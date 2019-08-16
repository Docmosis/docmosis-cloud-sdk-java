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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DocmosisServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy
{
	
	private static final String FIELD_HEADER_X_DOCMOSIS_SVR = "X-Docmosis-Server";
	private int maxRetrys;
	private int retryDelay;
	private PreviousFailureInformation prevFailure;
	private final Logger log;
	private int tries;
	
	public DocmosisServiceUnavailableRetryStrategy(int maxRetrys, int retryDelay, Logger log)
	{
		this.maxRetrys = maxRetrys;
		this.retryDelay = retryDelay;
		prevFailure = null;
		this.log = log;
	}

	@Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context)
    {
		tries = executionCount;
		int status = response.getStatusLine().getStatusCode();
    	log.log(Level.FINE, "Service Unavailable. Received status=" + status + ". Try request: " + executionCount);
    	if (status != 200){
	    	try {
	    		//Build prevFailure Object
	    		String jsonString  = EntityUtils.toString(response.getEntity(), "UTF-8"); //Consumes HTTPEntity to create prevFailure
	    		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
	    		JsonElement shortMsg = null;
	    		JsonElement longMsg = null;
	    		if (jsonObject.has("shortMsg")) {
					shortMsg = jsonObject.get("shortMsg");
	    		}
	    		if (jsonObject.has("longMsg")) {
	    			longMsg = jsonObject.get("longMsg");
	    		}
		    	prevFailure = new PreviousFailureInformation(status,
		    				(shortMsg == null ? "" : shortMsg.toString()), 
		    				(longMsg == null ? response.toString() : longMsg.toString()), 
		    	   			(response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) == null ? "" : response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());

	    	}
	    	catch (IOException e)
	    	{
	    		log.log(Level.FINE, "Error processing response:", e);
	            prevFailure = null;
	    	}
    	}
        return (status >= 501 && status <= 599 && executionCount <= maxRetrys);
    }
	@Override
    public long getRetryInterval()
	{
        return retryDelay;
    }
	public PreviousFailureInformation getPrevFailure()
	{
		return prevFailure;
	}
	public int getTries()
	{
        return tries;
    }
}