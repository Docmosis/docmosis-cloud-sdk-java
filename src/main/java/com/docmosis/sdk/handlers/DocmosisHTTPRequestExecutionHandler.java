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

import java.io.IOException;
import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.docmosis.sdk.response.DocmosisCloudResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DocmosisHTTPRequestExecutionHandler {

	private static final String FIELD_HEADER_X_DOCMOSIS_SVR = "X-Docmosis-Server";
	public static DocmosisHTTPRequestRetryHandler retryHandler;
	public static DocmosisServiceUnavailableRetryStrategy retryStrategy;

	/**
	 * Method executes the http POST via the apache http library.
	 * @param url the url of the service
	 * @param maxTries the maximum number of tries that should be attempted 
	 * to service this request when a server/network error occurs.
	 * @param retryDelay the retry delay (milliseconds) to apply when
	 * a retry is required.
	 * @param payload the content to be sent with the POST.
	 */
	public CloseableHttpResponse executeHttpPost(String url, int maxTries, int retryDelay, HttpEntity payLoad, CloseableHttpClient client, Logger log) throws DocmosisException
	{
	    CloseableHttpResponse responseHttp = null;
	    HttpPost httpPost = null;
	    
	    try {
	    	//Create retry handlers
	    	retryHandler = new DocmosisHTTPRequestRetryHandler(
	    			maxTries, log);
	    	retryStrategy = new DocmosisServiceUnavailableRetryStrategy(
	    			maxTries, retryDelay, log);

	    	//Create HTTP Client
	    	client = HttpClients.custom()
                    .setRetryHandler(retryHandler)
                    .setServiceUnavailableRetryStrategy(retryStrategy)
                    .build();
    		
	    	//Create HTTP POST method and set data
    		httpPost = new HttpPost(url);
    	    httpPost.setEntity(payLoad);
    	    
    	    //Execute the request and return a response
    	    responseHttp = client.execute(httpPost);
            
        } catch (ConnectException e) {
            // can't make the connection
            log.log(Level.FINE, "Unable to connect to the Docmosis service.  Please check your URL and proxy settings:", e);
            throw new DocmosisException("Unable to connect to the Docmosis Cloud", e);
        } catch (ClientProtocolException e) {
        	log.log(Level.FINE, "Error in the HTTP Protocol and/or Headers:", e);
        	throw new DocmosisException("Error in the HTTP Protocol and/or Headers:", e);
        } catch (IOException e) {
        	log.log(Level.FINE, "Error processing request:", e);
        	throw new DocmosisException(e);
        }
        return responseHttp;
	}

	/**
	 * 
	 * Generic method to set the Failure Response if status != 200 (failed) and to set the common Response details
	 */
	public void setFailureResponse(DocmosisCloudResponse response, CloseableHttpResponse chResponse, String url, int status) throws IOException
	{
		if (status != 200 && chResponse.getEntity() != null) { //Request Failed and a message was returned
	    	
    		if (status == 404) {
    			// deal with not found explicitly
    			if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
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

    		} else if (status >= 501 && status <= 599) {
    			// deal with a connectivity-based server-error
    			// 500 code could be the Docmosis service itself which will provide more diagnostics below.
    			if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
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

    		} else {
				if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
    				response.setLongMsg(retryStrategy.getPrevFailure().getLongMsg());
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
    		response.setLongMsg(chResponse.toString());
    	}
		response.setStatus(status);
		response.setPreviousFailureInformation(retryStrategy.getPrevFailure());
		response.setTries(retryStrategy.getTries());
		if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) {
    		response.setServerId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
    	}
	}

	/**
	 * Print message to the log.
	 * @param message
	 */
	public static void logEntry(Logger log, String message)
	{
		if (log.isLoggable(Level.FINE)) {
            final StringBuilder buffer = new StringBuilder();
	        buffer.append(message);
	        log.log(Level.FINE, buffer.toString());
        }
	}

	public static DocmosisHTTPRequestRetryHandler getRetryHandler() {
		return retryHandler;
	}

	public static DocmosisServiceUnavailableRetryStrategy getRetryStrategy() {
		return retryStrategy;
	}
}
