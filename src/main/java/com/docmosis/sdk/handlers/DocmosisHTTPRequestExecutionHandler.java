/*
 *   Copyright 2019 Docmosis.com or its affiliates.  All Rights Reserved.
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
package com.docmosis.sdk.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.ProxyAuthenticationStrategy;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.docmosis.sdk.environment.EnvironmentBuilder;
import com.docmosis.sdk.environment.InvalidEnvironmentException;
import com.docmosis.sdk.environment.Proxy;
import com.docmosis.sdk.render.RenderRequest;
import com.docmosis.sdk.request.DocmosisCloudFileRequest;
import com.docmosis.sdk.request.DocmosisCloudRequest;
import com.docmosis.sdk.response.MutableResponseInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DocmosisHTTPRequestExecutionHandler {

	private static final String FIELD_HEADER_X_DOCMOSIS_SVR			   = "X-Docmosis-Server";
	private static final String FIELD_HEADER_X_DOCMOSIS_REQUEST_ID     = "X-Docmosis-RequestId";
	private static final String FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED = "X-Docmosis-PagesRendered";
	private static final String FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT   = "X-Docmosis-BytesOutput";
	private static final String FIELD_HEADER_SERVER = "Server";
	private static final String FIELD_HEADER_CONTENT_TYPE = "Content-Type";
	
	private static final String FIELD_VALUE_DOCMOSIS = "Docmosis";
	private static final String FIELD_VALUE_TORNADO = "Tornado";
	
	private static final String FIELD_CONTENT_TYPE_JSON = "application/json";
	private static final String FIELD_CONTENT_TYPE_XML = "application/xml";
	
	private static final String FIELD_NAME_SHORT_MSG = "shortMsg";
	private static final String FIELD_NAME_LONG_MSG = "longMsg";

	/**
	 * Method executes the http POST via the apache http library.
	 * @param response the response object
	 * @param request the request object
	 * @param payLoad the content to be sent with the POST
	 * @param requestIsJson true if json, otherwise xml
	 * @return json response as a string object. If a document response then a blank string is returned
	 * @throws DocmosisException if something goes wrong whilst fulfilling the request
	 */
	public static String executeHttpPost(MutableResponseInterface response, DocmosisCloudRequest<?> request, HttpEntity payLoad, boolean requestIsJson) throws DocmosisException
	{
		CloseableHttpClient client = null;
		CloseableHttpResponse chResponse = null;
	    HttpPost httpPost = null;
	    String responseString = ""; //Json response
	    
	    //Set environment
    	try {
			EnvironmentBuilder.validate(request.getEnvironment());
		} catch (InvalidEnvironmentException e1) {
			throw new DocmosisException(e1);
		}
	    
	    try {
	    	//Create retry handlers
	    	final DocmosisHTTPRequestRetryHandler retryHandler = new DocmosisHTTPRequestRetryHandler(
	    			request.getEnvironment().getMaxTries());
	    	final DocmosisServiceUnavailableRetryStrategy retryStrategy = new DocmosisServiceUnavailableRetryStrategy(
	    			request.getEnvironment().getMaxTries(), request.getEnvironment().getRetryDelayMS());

	    	//Create HTTP Client
	    	HttpClientBuilder clientBuilder = HttpClients
	    										.custom()
	    										.setRetryHandler(retryHandler)
	    										.setServiceUnavailableRetryStrategy(retryStrategy);
	    	
	    	RequestConfig.Builder configBuilder = RequestConfig.custom();

	    	//Create HTTP POST method and set data
    		httpPost = new HttpPost(request.getUrl());
    		//httpPost.setHeader("Accept-Encoding", "UTF-8");
    	    httpPost.setEntity(payLoad);

	    	//Add proxy if one is set
	    	Proxy prx = request.getEnvironment().getProxy();
	    	if (prx != null){
	    		HttpHost proxyhost = new HttpHost(prx.getHost(), prx.getPort());
	    		
	    		if (prx.getUser() != null && prx.getPasswd() != null) {
	    			//Credentials credentials = new UsernamePasswordCredentials(prx.getUser(), prx.getPasswd());
	    			NTCredentials credentials = new NTCredentials(prx.getUser(), prx.getPasswd(), null, null);
	    			AuthScope authScope = new AuthScope(prx.getHost(), prx.getPort());
	    			CredentialsProvider credsProvider = new BasicCredentialsProvider();
	    			credsProvider.setCredentials(authScope, credentials);
	    			clientBuilder.setDefaultCredentialsProvider(credsProvider);
	    			clientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
	    		}
	    		
	    		clientBuilder.setProxy(proxyhost);
	    		client = clientBuilder.build();
	    		configBuilder.setProxy(proxyhost);
	    	} else {
	    		client = clientBuilder.build();
	    	}

	    	//Configure timeouts
	    	RequestConfig config = configBuilder
    	            .setConnectTimeout((int)request.getEnvironment().getConnectTimeoutMS())
    	            .setSocketTimeout((int)request.getEnvironment().getReadTimeoutMS())
	    			.build();
	    			
	    	httpPost.setConfig(config);
	    	
	    	//Set user agent header
	    	httpPost.addHeader(HttpHeaders.USER_AGENT, request.getEnvironment().getUserAgent());
	    	
    	    //Execute the request and populate the response object with common settings
    	    chResponse = client.execute(httpPost);

    	    //Process the response body
    	    String server = (chResponse.containsHeader(FIELD_HEADER_SERVER)) ? chResponse.getFirstHeader(FIELD_HEADER_SERVER).getValue() : "";
    	    String xServer = (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) ? chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue() : "";
    		if (server.contains(FIELD_VALUE_DOCMOSIS) || xServer.contains(FIELD_VALUE_TORNADO)) { //Response is from Docmosis.
    			setResponse(response, chResponse, request.getUrl(), requestIsJson, retryStrategy); //Set common response values
	    	    if (chResponse.getStatusLine().getStatusCode() == 200) {
	    	    	if (chResponse.getEntity() != null) {
			    	    try {
				    	    if (request instanceof DocmosisCloudFileRequest) { //A file has been returned
				    	    	if (request instanceof RenderRequest) {  //A Rendered file may have been returned
				    	    		RenderRequest rr = (RenderRequest) request;
		    	    				final String contentType = chResponse.getFirstHeader(FIELD_HEADER_CONTENT_TYPE).getValue();
		    	    				if (contentType.startsWith(FIELD_CONTENT_TYPE_JSON) || contentType.startsWith(FIELD_CONTENT_TYPE_XML)) {
		    	    	    			// text payload - ignore for successful results
		    	    	    		} else {
		    	    	    			// binary payload
		    	    	    			rr.sendDocumentTo(chResponse.getEntity().getContent());
		    	    	    		}
				    	    	} else {
					    	    	//StoreDocument
					    	    	DocmosisCloudFileRequest<?> requestFile = (DocmosisCloudFileRequest<?>) request;
					    	    	requestFile.sendDocumentTo(chResponse.getEntity().getContent());
				    	    	}
				    	    } else { // A json String has been returned
				    	    	//Get json response String
		    	    			responseString = EntityUtils.toString(chResponse.getEntity(), Charsets.UTF_8);
				    	    }
			    	    } catch (FileNotFoundException e) {
				            throw new DocmosisException(e);
			    	    } catch (IOException e) {
				            throw new DocmosisException(e);
				        }
		    	    }
		    	    else {
		    	    	throw new DocmosisException("No response returned from Docmosis Cloud");
		    	    }
	    	    }
    		}
    		else { //Unexpected response not from Docmosis cloud or Tornado
    			String errorMsg = null;
				try { //Try to extract returned message
					if (chResponse.getEntity() != null) {
						errorMsg = EntityUtils.toString(chResponse.getEntity(), Charsets.UTF_8);
					}
				}
				catch (IOException e) {
		    		//Quietly ignore.
		    	}
    			throw new DocmosisException("Unexpected Response from Server: " + server + ((errorMsg != null) ? ", Response:" + System.getProperty("line.separator") + errorMsg : ""));
    		}

        } catch (ConnectException e) {
            // can't make the connection
            throw new DocmosisException("Unable to connect to the Docmosis Cloud", e);
        } catch (ClientProtocolException e) {
        	throw new DocmosisException("Error in the HTTP Protocol and/or Headers:", e);
        } catch (IOException e) {
        	throw new DocmosisException(e);
        }
	    finally {
	    	try {
	    		if (chResponse != null) {
	    			chResponse.close();
	    		}
	    		if (client != null) {
	    			client.close();
				}
			} catch (IOException e) {
				// quietly ignore 
			}
	    }
        return responseString;
	}

	public static String executeHttpPost(MutableResponseInterface response, DocmosisCloudRequest<?> request, HttpEntity payLoad) throws DocmosisException
	{
		return executeHttpPost(response, request, payLoad, true);
	}

	/**
	 * Generic method to set common Response details and the Failure Response message if status != 200 (failed).
	 * @param response the response object
	 * @param chResponse the apache response object
	 * @param url of the endpoint
	 * @param requestIsJson requestIsJson true if json, otherwise xml
	 * @throws IOException if response cannot be extracted
	 */
	private static void setResponse(MutableResponseInterface response, CloseableHttpResponse chResponse, String url, 
			boolean requestIsJson, DocmosisServiceUnavailableRetryStrategy retryStrategy) throws IOException
	{
		int status = chResponse.getStatusLine().getStatusCode();
		if (status != 200 && chResponse.getEntity() != null) { //Request Failed and a message was returned
	    	
    		if (status == 404) {
    			// deal with not found explicitly
    			if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
    			}
    			else if (requestIsJson) {
    				setFailureResponseJson(response, chResponse);
    			}
    			else {
    				setFailureResponseXml(response, chResponse);
    			}
    			if (response.getLongMsg() == null) {
    				response.setLongMsg("URL [" + url + "] is not valid.");
    			}

    		} else if (status >= 501 && status <= 599) {
    			// deal with a connectivity-based server-error
    			// 500 code could be the Docmosis service itself which will provide more diagnostics below.
    			if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
    			}
    			else if (requestIsJson) {
    				setFailureResponseJson(response, chResponse);
    			}
    			else {
    				setFailureResponseXml(response, chResponse);
    			}
    			if (response.getLongMsg() == null) {
    				response.setLongMsg("URL [" + url + "] is not available.");
    			}

    		} else {
				if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
    				response.setLongMsg(retryStrategy.getPrevFailure().getLongMsg());
    			}
				else if (requestIsJson) {
    				setFailureResponseJson(response, chResponse);
    			}
    			else {
    				setFailureResponseXml(response, chResponse);
    			}
    		}
    	}
		
		//Set common values.
		response.setStatus(status);
		response.setPreviousFailureInformation(retryStrategy.getPrevFailure());
		response.setTries(retryStrategy.getTries());
		if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) {
    		response.setServerId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
    	}
		if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED)) {
			response.setPages(toInt(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED).getValue()));
    	}
		if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_REQUEST_ID)) {
			response.setRequestId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_REQUEST_ID).getValue());
    	}
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED)) {
    		response.setPages(toInt(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED).getValue()));
    	}
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT)) {
    		response.setLength(toLong(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT).getValue()));
    	}
	}
	
    private static void setFailureResponseJson(MutableResponseInterface response, CloseableHttpResponse chResponse) throws IOException {
    	String jsonString  = EntityUtils.toString(chResponse.getEntity(), Charsets.UTF_8);
		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
		JsonElement shortMsg = null;
		JsonElement longMsg = null;
		if (jsonObject.has(FIELD_NAME_SHORT_MSG)) {
			shortMsg = jsonObject.get(FIELD_NAME_SHORT_MSG);
		}
		if (jsonObject.has(FIELD_NAME_LONG_MSG)) {
			longMsg = jsonObject.get(FIELD_NAME_LONG_MSG);
		}
		response.setShortMsg(shortMsg == null ? "" : shortMsg.toString());
		response.setLongMsg(longMsg == null ? "" : longMsg.toString());
    }
    
    private static void setFailureResponseXml(MutableResponseInterface response, CloseableHttpResponse chResponse) throws IOException {
    	try {
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(chResponse.getEntity().getContent());
    		doc.getDocumentElement().normalize();
    		String shortMsg = null;
    		String longMsg = null;
    		
    		Node responseNode = doc.getChildNodes().item(0);
    		if (responseNode != null) {
	    		shortMsg = getXMLStringAttribute(responseNode.getAttributes().getNamedItem(FIELD_NAME_SHORT_MSG));
	    		longMsg = getXMLStringAttribute(responseNode.getAttributes().getNamedItem(FIELD_NAME_LONG_MSG));
    		}
	    		
    		response.setShortMsg(shortMsg == null ? "" : shortMsg);
    		response.setLongMsg(longMsg == null ? "" : longMsg);
    		
    	} catch (ParserConfigurationException e) {
			throw new IOException(e);
		} catch (SAXException e) {
			throw new IOException(e);
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

	
    private static int toInt(String val)
    {
    	try {
    		return Integer.parseInt(val);
    	} catch(NumberFormatException e) {
    		return 0;
    	}
    }

    private static long toLong(String val)
    {
    	try {
    		return Long.parseLong(val);
    	} catch(NumberFormatException e) {
    		return 0;
    	}
    }
}
