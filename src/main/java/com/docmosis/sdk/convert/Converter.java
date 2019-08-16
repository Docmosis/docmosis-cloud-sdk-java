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
package com.docmosis.sdk.convert;

import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.docmosis.sdk.handlers.DocmosisHTTPRequestRetryHandler;
import com.docmosis.sdk.handlers.DocmosisServiceUnavailableRetryStrategy;
import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class manages the interaction with the Docmosis convert service endpoint - converting
 * documents between formats
 *
 */
public class Converter {

    private static final Logger log = Logger.getLogger(Converter.class.getName());
    
	private static final String FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED = "X-Docmosis-PagesRendered";
	private static final String FIELD_HEADER_X_DOCMOSIS_SVR            = "X-Docmosis-Server";
	private static final String FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT   = "X-Docmosis-BytesOutput";
    private static CloseableHttpClient client;
    private static CloseableHttpResponse responseHttp;
    
    /**
     * Convert a document using Docmosis Web Services 
     * <p>
     * NOTE: call {@link ConverterResponse#cleanup()} on the response returned
     *       to cleanup resources
     *       call {@link ConverterRequest#cleanup()} to cleanup Converter resources
     * 
     * @param request the convert data.
     * @return a response object encapsulating the result of the operation
     * 
     * @throws ConverterException if something goes wrong constructing the request
     */
    public Converter()
    {
    	client = null;
    	responseHttp = null;
    }

    public static ConverterRequest convert()
    {
    	return new ConverterRequest();
    }

    public static ConverterResponse executeConvert(final ConverterRequest request) throws ConverterException 
    {       
        if (log.isLoggable(Level.FINE)) {
            final StringBuilder buffer = new StringBuilder();
	        buffer.append("convert(");
        	buffer.append(request.toString());
	        buffer.append(")");
	        log.log(Level.FINE, buffer.toString());
        }

        if (!request.getFileToConvert().canRead()) {
            throw new ConverterException("cannot read file to convert: ["
                            + request.getFileToConvert() + "]");
        }
        
        final int maxTriesOnServerError = request.getMaxTries();
        ConverterResponse response = null;
        	
    	client = null;
    	responseHttp = null;
    	HttpPost httpPost = null;
    	
	    try {
	        
	    	DocmosisHTTPRequestRetryHandler retryHandler = new DocmosisHTTPRequestRetryHandler(maxTriesOnServerError, log);
	    	DocmosisServiceUnavailableRetryStrategy retryStrategy = new DocmosisServiceUnavailableRetryStrategy(maxTriesOnServerError, request.getRetryDelay(), log);
	    	
	    	client = HttpClients.custom()
                    .setRetryHandler(retryHandler)
                    .setServiceUnavailableRetryStrategy(retryStrategy)
                    .build();
    		
    		httpPost = new HttpPost(request.getUrl());

    		//Build request
    		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
    		if (request.getAccessKey() != null) {
    			builder.addTextBody("accessKey", request.getAccessKey());
    		}
    	    builder.addBinaryBody("file", request.getFileToConvert(), ContentType.APPLICATION_OCTET_STREAM, request.getFileToConvert().getName());
    	    builder.addTextBody("outputName", request.getOutputName());
    	    HttpEntity multipart = builder.build();

    	    httpPost.setEntity(multipart);

            // build the response
    	    responseHttp = client.execute(httpPost);
 	    
            response = extractResponse(responseHttp, request.getUrl(), retryStrategy.getPrevFailure());
            
            if (retryStrategy.getPrevFailure() != null) {
            	response.setPreviousFailureInformation(retryStrategy.getPrevFailure());
            	response.setTries(retryStrategy.getTries());
            }
            
            
        } catch (ConnectException e) {
            // can't make the connection
            log.log(Level.FINE, "Unable to connect to the Docmosis service.  Please check your URL and proxy settings:", e);
            throw new ConverterException("Unable to connect to the Docmosis Cloud", e);
        } catch (ClientProtocolException e) {
        	log.log(Level.FINE, "Error in the HTTP Protocol and/or Headers:", e);
            throw new ConverterException("Error in the HTTP Protocol and/or Headers:", e);
        } catch (IOException e) {
        	e.printStackTrace();
        	log.log(Level.FINE, "Error processing convert request:", e);
            throw new ConverterException(e);
        }
        return response;
    }
    
    /*
     * Extract the payload which could be a success or failure and may even contain a binary document stream
     */
	private static ConverterResponse extractResponse(CloseableHttpResponse chResponse, String url, PreviousFailureInformation prevFailure) throws IOException 
    {
    	ConverterResponse response = null;
    	int status = chResponse.getStatusLine().getStatusCode();

    	response = new ConverterResponse();
    	
    	if (status == 200) { // succeeded

			response.setDocument(chResponse.getEntity().getContent());
			
    	} else if (chResponse.getEntity() != null) {
    	
    		if (status == 404) {
    			// deal with not found explicitly
    			response.setStatus(status);
    			if (prevFailure != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(prevFailure.getShortMsg());
    			}
    			else {
	    			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
	    			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
	    			if (jsonObject.has("shortMsg")) {
	    				JsonElement shortMsg = jsonObject.get("shortMsg");
	    				response.setShortMsg(shortMsg.getAsString());
	    			}
    			}
    			response.setLongMsg("URL [" + url + "] is not valid.");
    			
    			return response;
    			
    		} else if (status >= 501 && status <= 599) {
    			// deal with a connectivity-based server-error
    			// 500 code could be the Docmosis service itself which will provide more diagnostics below.
    			response.setStatus(status);
    			if (prevFailure != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(prevFailure.getShortMsg());
    			}
    			else {
	    			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
	    			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
	    			if (jsonObject.has("shortMsg")) {
	    				JsonElement shortMsg = jsonObject.get("shortMsg");
	    				response.setShortMsg(shortMsg.getAsString());
	    			}
    			}
    			response.setLongMsg("URL [" + url + "] is not available.");

    			return response;
    		}
    		else {
    			if (prevFailure != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(prevFailure.getShortMsg());
    				response.setLongMsg(prevFailure.getLongMsg());
    			}
    			else {
    				String jsonString  = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
    	    		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
    	    		if (jsonObject.has("shortMsg")) {
    	    			JsonElement shortMsg = jsonObject.get("shortMsg");
    	    			response.setShortMsg(shortMsg.toString());
    	    		}
    	    		if (jsonObject.has("longMsg")) {
    	    			JsonElement longMsg = jsonObject.get("longMsg");
    	    			response.setLongMsg(longMsg.toString());
    	    		}
    	    		else {
    	    			response.setLongMsg(chResponse.toString());
    	    		}
    			}
    		}
    	} else {
    		// there is no stream of information to read - server didn't respond?
			// main error codes will be captured below.
    	}

    	response.setStatus(status);
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) {
    		response.setServerId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
    	}
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED)) {
    		response.setPagesConverted(toInt(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED).getValue()));
    	}
    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT)) {
    		response.setLength(toLong(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT).getValue()));
    	}
    	response.setClientHTTP(client);
    	response.setResponseHttp(responseHttp);

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

    private static long toLong(String val)
    {
    	try {
    		return Long.parseLong(val);
    	} catch(NumberFormatException e) {
    		return 0;
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
