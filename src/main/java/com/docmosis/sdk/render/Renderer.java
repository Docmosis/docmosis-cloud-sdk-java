/*
 *   Copyright 2012 Docmosis.com or its affiliates.  All Rights Reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *       http://www.apache.org/licenses/LICENSE-2.0
 *   or in the LICENSE file accompanying this file.
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.docmosis.sdk.render;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestRetryHandler;
import com.docmosis.sdk.handlers.DocmosisServiceUnavailableRetryStrategy;
import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * This class manages the interaction with the Docmosis render service endpoint - producing
 * documents.
 * 
 * Direct use of this class is not necessary since {@link RenderRequest.execute()} will invoke
 * this class as required.
 *
 */
public class Renderer {

    private static final Logger log = Logger.getLogger(Renderer.class.getName());

	private static final String FIELD_HEADER_X_DOCMOSIS_REQUEST_ID     = "X-Docmosis-RequestId";
	private static final String FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED = "X-Docmosis-PagesRendered";
	private static final String FIELD_HEADER_X_DOCMOSIS_SVR            = "X-Docmosis-Server";
	private static CloseableHttpClient client;
    private static CloseableHttpResponse responseHttp;

    /**
     * Render a Docmosis document using either Tornado or Docmosis Cloud.
     * <p>
     * NOTE: call {@link RenderResponse#cleanup()} on the response returned
     *       to cleanup resources  
     * 
     * @param request the render data.
     * @return a response object encapsulating the result of the render
     * 
     * @throws RendererException if something goes wrong constructing the request
     */
    public Renderer()
    {
    	client = null;
    	responseHttp = null;
    }
 
    /**
	 * Create a Render Request. Run .execute() to run the request and return the Rendered File.
	 * @return RenderRequest
	 */
    public static RenderRequest render()
    {
    	return RenderRequestFactory.getRequest();
    }
    
    /**
	 * Create a Render Tags Request. Run .execute() to run the request and return the Render Tags Response.
	 * @return GetRenderTagsRequest
	 */
    public static GetRenderTagsRequest getRenderTags()
    {
    	return new GetRenderTagsRequest();
    }

    /**
     * The ping service provides a direct check that the Docmosis REST web services are online and there is at least one Docmosis server listening.
     * This is useful for diagnostics and for monitoring purposes.
     * @return true if at least one Docmosis server is listening.
     */
    public static boolean ping()
    {
    	return ping("https://dws2.docmosis.com/services/rs/ping");	  
    }

    /**
     * The ping service provides a direct check that the Docmosis REST web services are online and there is at least one Docmosis server listening.
     * This is useful for diagnostics and for monitoring purposes.
     * @return true if at least one Docmosis server is listening.
     */   
    public static boolean ping(String url)
    {
    	try {
    		CloseableHttpClient client = HttpClients.custom()
    										.build();
    		CloseableHttpResponse responseHttp = client.execute(new HttpPost(url));
    		return responseHttp.getStatusLine().getStatusCode() == 200;
    	} catch (Exception e) {
            return false;
        }	  
    }
    
    public static RenderResponse executeRender(final RenderRequest request) throws RendererException 
    {        
        if (log.isLoggable(Level.FINE)) {
            final StringBuilder buffer = new StringBuilder();
	        buffer.append("render(");
	        if (log.isLoggable(Level.FINEST)) {
	        	// log the entire request
	        	buffer.append(request.toString(true));
	        } else {
	        	// log just the more likely fields
	        	buffer.append(request.toString(false));
	        }
	        buffer.append(")");
	        log.log(Level.FINEST, buffer.toString());
        }

        final int maxTriesOnServerError = request.getMaxTries();
        RenderResponse response = null;
        
        client = null;
    	responseHttp = null;
    	HttpPost httpPost = null;
        	
		try {
        	
        	final boolean requestIsJson = requestIsJson(request);
        	DocmosisHTTPRequestRetryHandler retryHandler = new DocmosisHTTPRequestRetryHandler(maxTriesOnServerError, log);
        	RendererServiceUnavailableRetryStrategy retryStrategy = new RendererServiceUnavailableRetryStrategy(maxTriesOnServerError, request.getRetryDelay(), log, requestIsJson);
        	
	    	client = HttpClients.custom()
                    .setRetryHandler(retryHandler)
                    .setServiceUnavailableRetryStrategy(retryStrategy)
                    .build();

	    	httpPost = new HttpPost(request.getUrl());

    		//Build request
            final String renderRequest = buildRequest(request, requestIsJson);
            log.fine("Sending request:" + renderRequest);

            StringEntity se = null;
            if (requestIsJson) {
            	se = new StringEntity(renderRequest, ContentType.create("application/json"));
            	httpPost.setEntity(se);
            }
            else {
            	se = new StringEntity(renderRequest, ContentType.create("application/xml"));
            }
            httpPost.setEntity(se);
            
            //Build the response
            responseHttp = client.execute(httpPost);
            response = extractResponse(responseHttp, request.getUrl(), requestIsJson, retryStrategy.getPrevFailure());
            
            if (retryStrategy.getPrevFailure() != null) {
            	response.setPreviousFailureInformation(retryStrategy.getPrevFailure());
            	response.setTries(retryStrategy.getTries());
            }
            
        } catch (ConnectException e) {
            // can't make the connection
            log.log(Level.FINE, "Unable to connect to the Docmosis service.  Please check your URL and proxy settings:", e);
            throw new RendererException("Unable to connect to the Docmosis Cloud", e);
        } catch (ClientProtocolException e) {
        	log.log(Level.FINE, "Error in the HTTP Protocol and/or Headers:", e);
            throw new RendererException("Error in the HTTP Protocol and/or Headers:", e);
        } catch (IOException e) {
        	log.log(Level.FINE, "Error processing render request:", e);
            throw new RendererException(e);
        }	    	
        //catch(InterruptedException e) {
        //	log.log(Level.WARNING, "Interrupted", e);
        //}
        return response;
    }

    
    /**
     * Determine if we are dealing with JSON or XML in general by inspecting 
     * the data in the request.  If in doubt, JSON.
     * 
     * @param req
     * @return
     */
    private static boolean requestIsJson(RenderRequest req)
    {
    	return req == null || isJson(req.getData());
    }
    
    /**
     * Determine if the given string looks like JSON
     * 
     * @param data
     * @return true if it appears to be JSON or is null/blank
     */
    public static boolean isJson(String data)
    {
    	boolean isJson = false;
    	
    	if (data == null || "".equals(data.trim())) {
    		// default to true
    		isJson = true;
    	} else {
    		// hunt for the first non-whitespace char - is it a '{'?
    		
    		for(int i=0; i < data.length(); i++) {
    			final char ch = data.charAt(i);
    			if (!Character.isWhitespace(ch)) {
    				if (ch == '{') {
    					isJson = true;
    				}
					break;
    			}
    		}
    		
    	}
    	
    	return isJson;
    }
    
    private static String buildRequest(final RenderRequest request, final boolean jsonFormat) {
        StringBuilder buffer = new StringBuilder();

        // Start building the instruction.
        addField("accessKey", request.getAccessKey(), buffer, true, jsonFormat);
        addField("templateName", request.getTemplateName(), buffer, true, jsonFormat);
        addField("isSystemTemplate", request.getIsSystemTemplate(), buffer, true, jsonFormat);
        addField("outputName", request.getOutputName(), buffer, true, jsonFormat);
        addField("outputFormat", request.getOutputFormat(), buffer, true, jsonFormat);
        addField("compressSingleFormat", request.getCompressSingleFormat(), buffer, true, jsonFormat);
        addField("storeTo", request.getStoreTo(), buffer, true, jsonFormat);
        addField("billingKey", request.getBillingKey(), buffer, true, jsonFormat); //Still used??
        addField("devMode", request.getDevMode(), buffer, true, jsonFormat);
        addField("requestId", request.getRequestId(), buffer, true, jsonFormat);
        addField("mailSubject", request.getMailSubject(), buffer, true, jsonFormat);
        addField("mailBodyHtml", request.getMailBodyHtml(), buffer, true, jsonFormat);
        addField("mailBodyText", request.getMailBodyText(), buffer, true, jsonFormat);
        addField("mailNoZipAttachments", request.getMailNoZipAttachments(), buffer, true, jsonFormat);
        addField("sourceId", request.getSourceId(), buffer, true, jsonFormat);
        addField("stylesInText", request.getStylesInText(), buffer, true, jsonFormat);
        addField("passwordProtect", request.getPasswordProtect(), buffer, true, jsonFormat);
        addField("pdfArchiveMode", request.getPdfArchiveMode(), buffer, true, jsonFormat);
        addField("pdfWatermark", request.getPdfWatermark(), buffer, true, jsonFormat);
        addField("pdfTagged", request.getPdfTagged(), buffer, true, jsonFormat);
        //Missing ignoreUnknownParams
        //Missing tags
        //Missing streamResultInResponse

        
        if (jsonFormat) {
        	String json = request.getData();
        	json = (json == null || "".equals(json.trim())) ? null : json;
        	
            addField("data", json, buffer, false, jsonFormat);
        	buffer = new StringBuilder(buffer.length() + 2).append("{").append(buffer).append("}");
        } else {
        	buffer.insert(0, "<?xml version=\"1.0\" encoding=\"utf-8\"?><render ");
        	buffer.append(">");
			if (request.getData().trim().startsWith("<data")) {
				buffer.append(request.getData());
			} else {
				buffer.append("<data>");
				buffer.append(request.getData());
				buffer.append("</data>");
			}

        	buffer.append("</render>");
        }

        return buffer.toString();
    }

//    private static void addField(final String key, final String value, final StringBuilder buffer) 
//    {
//    	addFieldJson(key, value, buffer, true);
//    }

    private static void addField(final String key, final String value, final StringBuilder buffer,
                final boolean quoteValue, final boolean jsonFormat) 
    {
    	if (jsonFormat) {
    		addFieldJson(key, value, buffer, quoteValue);
    	} else {
    		addFieldXml(key, value, buffer, quoteValue);
    	}
    }
    
    private static void addFieldJson(final String key, final String value, final StringBuilder buffer,
            final boolean quoteValue)
    {
    	
        if (value != null) {
            if (buffer.length() > 0) {
                buffer.append(",");
            }
            buffer.append("\"");
            buffer.append(key);
        	buffer.append("\":");
            if (quoteValue) {
            	buffer.append("\"");
            }
            buffer.append(value);
            if (quoteValue) {
            	buffer.append("\"");
            }
        }
    }

    
    private static void addFieldXml(final String key, final String value, final StringBuilder buffer,
            final boolean quoteValue)
    {
    	if (value != null) {
    		buffer.append(" ").append(key).append("=\"").append(value).append('"');
    	}
    }

    /*
     * Extract the payload which could be a success or failure and may even contain a binary document stream
     */
    private static RenderResponse extractResponse(CloseableHttpResponse chResponse, String url, final boolean jsonFormat, PreviousFailureInformation prevFailure) throws IOException 
    {
    	RenderResponse response = new RenderResponse();
    	int status = chResponse.getStatusLine().getStatusCode();
    	
		final String contentType = chResponse.getFirstHeader("Content-Type").getValue();
    	if (status == 200) {
    		// succeeded
    		if (contentType.startsWith("application/json") || contentType.startsWith("application/xml")) {
    			// text payload - ignore for successful results
    		} else {
    			// binary payload
    			response.setDocument(chResponse.getEntity().getContent());
    		}

    	} else if (chResponse.getEntity() != null) {
//    		log.log(Level.SEVERE, "Render call failed: status = " + conn.getResponseCode());
//    		log.log(Level.SEVERE, "message:" + conn.getResponseMessage());
    	
    		if (status == 404) {
    			// deal with not found explicitly
    			if (prevFailure != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(prevFailure.getShortMsg());
    			}
    			else if (jsonFormat) {
    				response = setFailureResponseJson(chResponse);
    			}
    			else {
    				response = setFailureResponseXml(chResponse);
    			}
    			response.setLongMsg("URL [" + url + "] is not valid.");
    			response.setStatus(status);
    			
    			return response;

    		} else if (status >= 501 && status <= 599) {
    			// deal with a connectivity-based server-error
    			// 500 code could be the Docmosis service itself which will provide more diagnostics below.
    			if (prevFailure != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(prevFailure.getShortMsg());
    			}
    			else if (jsonFormat) {
    				response = setFailureResponseJson(chResponse);
    			}
    			else {
    				response = setFailureResponseXml(chResponse);
    			}
    			response.setLongMsg("URL [" + url + "] is not available.");
    			response.setStatus(status);
    			
    			return response;
    		}
    		else {
    			if (prevFailure != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(prevFailure.getShortMsg());
    				response.setLongMsg(prevFailure.getLongMsg());
    			
    			} else if (jsonFormat) {
			        // Read the error response from JSON
    				response = setFailureResponseJson(chResponse);

		        } else {
	        		// Read the error response from XML
		        	response = setFailureResponseXml(chResponse);
		        }
    		}
    	} else {
    		// there is no stream of information to read - server didn't respond?
			// main error codes will be captured below.
    		response.setLongMsg(chResponse.toString());
    	}
    	
    	response.setStatus(status);
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_REQUEST_ID)) {
    		response.setRequestId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_REQUEST_ID).getValue());
    	}
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED)) {
    		response.setPagesRendered(toInt(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED).getValue()));
    	}
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) {
    		response.setServerId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
    	}
    	response.setClientHTTP(client);
    	response.setResponseHttp(responseHttp);

    	return response;
    }

    private static RenderResponse setFailureResponseJson(CloseableHttpResponse chResponse) throws IOException {
    	RenderResponse response = new RenderResponse();
    	String jsonString  = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
		JsonElement shortMsg = null;
		JsonElement longMsg = null;
		if (jsonObject.has("shortMsg")) {
			shortMsg = jsonObject.get("shortMsg");
		}
		if (jsonObject.has("longMsg")) {
			longMsg = jsonObject.get("longMsg");
		}
		response.setShortMsg(shortMsg.toString());
		response.setLongMsg(longMsg == null ? chResponse.toString() : longMsg.toString());
    	return response;
    }
    
    private static RenderResponse setFailureResponseXml(CloseableHttpResponse chResponse) throws IOException {
    	RenderResponse response = new RenderResponse();
    	try {
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(chResponse.getEntity().getContent());
    		doc.getDocumentElement().normalize();
    		
    		Node responseNode = doc.getChildNodes().item(0);
    		if (responseNode != null) {
	    		final String shortMsg = getXMLStringAttribute(responseNode.getAttributes().getNamedItem("shortMsg"));
	    		final String longMsg = getXMLStringAttribute(responseNode.getAttributes().getNamedItem("longMsg"));
	    		
	    		response.setLongMsg(longMsg == null ? chResponse.toString() : longMsg);
	    		response.setShortMsg(shortMsg);
    		}
    		
    	} catch (ParserConfigurationException e) {
    		log.log(Level.FINE, "Unable to extract XML error response", e);
			throw new IOException(e);
		} catch (SAXException e) {
			throw new IOException(e);
		}
    	return response;
    }

    private static int toInt(String val)
    {
    	try {
    		return Integer.parseInt(val);
    	} catch(NumberFormatException e) {
    		return 0;
    	}
    }
    
    private static final String getXMLStringAttribute(Node node)
    {
    	String result = null;
    	if (node != null) {
    		result = node.getNodeValue();
    	}
    	return result;
    }
    
	/**
	 * Execute a GetRenderTags request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetRenderTagsResponse executeGetRenderTags(GetRenderTagsRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "GetRenderTags(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getAccessKey() != null) {
			builder.addTextBody("tags", request.getTags());
		}
		else {
			throw new DocmosisException("No Tags Specified.");
		}
		if (request.getYear() != 0) {
			builder.addTextBody("year", String.valueOf(request.getYear()));
		}
		if (request.getMonth() != 0) {
			builder.addTextBody("month", String.valueOf(request.getMonth()));
		}
		if (request.getnMonths() != 0) {
			builder.addTextBody("nMonths", String.valueOf(request.getnMonths()));
		}
		builder.addTextBody("padBlanks", String.valueOf(request.getPadBlanks()));
		
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetRenderTagsResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetRenderTagsResponse(chResponse, request.getUrl(), executionHandler);
	    }
	    catch (IOException e) {
	    	log.log(Level.FINE, "IOException. Cannot extract data from response.", e);
            throw new DocmosisException("Cannot extract data from response.", e);
	    }
	    catch (DocmosisException e) {
	    	throw e;
	    }
		return response;
	}

	/**
	 * Extract data from response object and store in ListTemplatesResponse object.
	 */
	private static GetRenderTagsResponse extractGetRenderTagsResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		GetRenderTagsResponse response = new GetRenderTagsResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

			//response.setTemplateListStale(Boolean.parseBoolean(jsonObject.get("templateListStale").getAsString()));
			
			Type renderTagsListType = new TypeToken<List<RenderTags>>() {}.getType();
			//Type listTagType = new TypeToken<List<Tag>>() {}.getType();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			List<RenderTags> renderTags = gson.fromJson(jsonObject.get("renderTags"), renderTagsListType);
			response.setRenderTags(renderTags);
    	}
    	
		executionHandler.setFailureResponse(response, chResponse, url, status);
    	
		return response;
	}
    
    /**
     * A helper for dealing with Base64 encoding. This is only needed if you are
     * using a proxy to reach the internet and must authenticate with the proxy.
     */
    private static class Base64Helper {
        private static final char[] B64_DIGIT = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
                'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
                '2', '3', '4', '5', '6', '7', '8', '9', '+', '-' };

        public static String toBase64(byte[] data) {
            int i = 0;
            int chunks = data.length / 3;
            StringBuilder sb = new StringBuilder(chunks * 4 + 2);
            for (int count = 0; count < chunks; count++, i += 3) {
                int buffer = ((data[i] & 0xFF) << 16) | ((data[i + 1] & 0xFF) << 8) | (data[i + 2] & 0xFF);
                sb.append(B64_DIGIT[(buffer & 0xFC0000) >> 18]);
                sb.append(B64_DIGIT[(buffer & 0x03F000) >> 12]);
                sb.append(B64_DIGIT[(buffer & 0x000FC0) >> 6]);
                sb.append(B64_DIGIT[(buffer & 0x00003F)]);
            }
            // padding
            int buffer;
            switch (data.length - i) {
            case 2:
                buffer = ((data[i] & 0xFF) << 16) | ((data[i + 1] & 0xFF) << 8);
                sb.append(B64_DIGIT[(buffer & 0xFC0000) >> 18]);
                sb.append(B64_DIGIT[(buffer & 0x03F000) >> 12]);
                sb.append(B64_DIGIT[(buffer & 0x000FC0) >> 6]);
                sb.append('=');
                break;
            case 1:
                buffer = ((data[i] & 0xFF) << 16);
                sb.append(B64_DIGIT[(buffer & 0xFC0000) >> 18]);
                sb.append(B64_DIGIT[(buffer & 0x03F000) >> 12]);
                sb.append("==");
                break;
            }
            return sb.toString();
        }
    }
    public void cleanup()
	{
    	if (responseHttp != null) {
	    	try {
	    		responseHttp.close();
			} catch (IOException e) {
				// expected that close might have trouble when request failed
				log.log(Level.FINE, "Problem closing response:", e);
			}
    	}
    	if (client != null) {
	    	try {
        		client.close();
			} catch (IOException e) {
				// expected that close might have trouble when request failed
				log.log(Level.FINE, "Problem closing connection:", e);
			}
    	}
	}
}
