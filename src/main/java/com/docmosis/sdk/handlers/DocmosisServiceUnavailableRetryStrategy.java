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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DocmosisServiceUnavailableRetryStrategy implements ServiceUnavailableRetryStrategy
{
	
	private static final String FIELD_HEADER_X_DOCMOSIS_SVR = "X-Docmosis-Server";
	private int maxRetrys;
	private long retryDelay;
	private PreviousFailureInformation prevFailure;
	private int tries;
	private boolean isJson;
	
	public DocmosisServiceUnavailableRetryStrategy(int maxRetrys, long retryDelay, boolean isJson)
	{
		this.maxRetrys = maxRetrys;
		this.retryDelay = retryDelay;
		prevFailure = null;
		this.isJson = isJson;
	}

	@Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context)
    {
		tries = executionCount;
		int status = response.getStatusLine().getStatusCode();
    	if (status != 200){
    		if (response.getFirstHeader("Server") != null && 
    				(response.getFirstHeader("Server").toString().contains("Docmosis") || response.getFirstHeader("Server").toString().contains("Tornado"))) { //Response is from Docmosis and not a proxy.
		    	try {
		    		//Build prevFailure Object
		    		if (isJson) {
			    		JsonElement shortMsg = null;
			    		JsonElement longMsg = null;
		    			if (response.getEntity() != null) {
				    		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8"); //Consumes HTTPEntity to create prevFailure
				    		JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
				    		if (jsonObject.has("shortMsg")) {
								shortMsg = jsonObject.get("shortMsg");
				    		}
				    		if (jsonObject.has("longMsg")) {
				    			longMsg = jsonObject.get("longMsg");
				    		}
					    	prevFailure = new PreviousFailureInformation(status,
					    				(shortMsg == null ? "" : shortMsg.toString()), 
					    				(longMsg == null ? "" : longMsg.toString()), 
					    	   			(response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) == null ? "" : response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
		    			}
		    		}
			    	else {
		    			try {
			        		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				    		Document doc = docBuilder.parse(response.getEntity().getContent());
				    		doc.getDocumentElement().normalize();
				    		
				    		Node responseNode = doc.getChildNodes().item(0);
				    		if (responseNode != null) {
				    			
				    			String shortMsg = null;
				    			String longMsg = null;
				    			if (responseNode.getAttributes().getNamedItem("shortMsg") != null) {
				    				shortMsg = responseNode.getAttributes().getNamedItem("shortMsg").getNodeValue();
				    			}
				    			if (responseNode.getAttributes().getNamedItem("longMsg") != null) {
				    				longMsg = responseNode.getAttributes().getNamedItem("longMsg").getNodeValue();
				    			}
					    		prevFailure = new PreviousFailureInformation(status,
					    				(shortMsg == null ? "" : shortMsg), 
					    				(longMsg == null ? "" : longMsg), 
					    	   			(response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) == null ? "" : response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
				    		}
				    		
				    		
			        	} catch (ParserConfigurationException e) {
							throw new IOException(e);
						} catch (SAXException e) {
							throw new IOException(e);
						}
		    		}
		    	}
		    	catch (IOException e)
		    	{
		            prevFailure = null;
		    	}
    		}
    		else { //Unexpected response from a proxy
    			try {
    				String server = response.getFirstHeader("Server") == null ? "" : response.getFirstHeader("Server").getValue();
    				prevFailure = new PreviousFailureInformation(status, "", EntityUtils.toString(response.getEntity(), "UTF-8"), server);
    			}
    			catch (IOException e)
		    	{
		    		String server = response.getFirstHeader("Server") == null ? "" : response.getFirstHeader("Server").getValue();
		    		prevFailure = new PreviousFailureInformation(status, "Unexpected Response from Server: " + server, "", server);
		    	}
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