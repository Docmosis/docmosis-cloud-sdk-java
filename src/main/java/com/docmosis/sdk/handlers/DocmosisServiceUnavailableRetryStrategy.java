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

import org.apache.commons.codec.Charsets;
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
	private static final String FIELD_HEADER_SERVER = "Server";
	private static final String FIELD_HEADER_CONTENT_TYPE = "Content-Type";
	
	private static final String FIELD_VALUE_DOCMOSIS = "Docmosis";
	private static final String FIELD_VALUE_TORNADO = "Tornado";
	
	private static final String FIELD_CONTENT_TYPE_JSON = "application/json";
	private static final String FIELD_CONTENT_TYPE_XML = "application/xml";
	
	private static final String FIELD_NAME_SHORT_MSG = "shortMsg";
	private static final String FIELD_NAME_LONG_MSG = "longMsg";
	
	
	private int maxRetrys;
	private long retryDelay;
	private PreviousFailureInformation prevFailure;
	private int tries;
	
	public DocmosisServiceUnavailableRetryStrategy(int maxRetrys, long retryDelay)
	{
		this.maxRetrys = maxRetrys;
		this.retryDelay = retryDelay;
		prevFailure = null;
	}

	@Override
    public boolean retryRequest(HttpResponse response, int executionCount, HttpContext context)
    {
		tries = executionCount;
		int status = response.getStatusLine().getStatusCode();
    	if (status != 200){
    		String server = (response.containsHeader(FIELD_HEADER_SERVER)) ? response.getFirstHeader(FIELD_HEADER_SERVER).getValue() : "";
    		String xServer = (response.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) ? response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue() : "";
    		if (server.contains(FIELD_VALUE_DOCMOSIS) || xServer.contains(FIELD_VALUE_TORNADO)) { //Response is from Docmosis.
		    	try {
		    		//Build prevFailure Object
		    		String contentType = (response.containsHeader(FIELD_HEADER_CONTENT_TYPE)) ? response.getFirstHeader(FIELD_HEADER_CONTENT_TYPE).getValue() : "";
		    		if (contentType.contains(FIELD_CONTENT_TYPE_JSON)) {
			    		JsonElement shortMsg = null;
			    		JsonElement longMsg = null;
		    			if (response.getEntity() != null) {
				    		String responseString = EntityUtils.toString(response.getEntity(), "UTF-8"); //Consumes HTTPEntity to create prevFailure
				    		JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
				    		if (jsonObject.has(FIELD_NAME_SHORT_MSG)) {
								shortMsg = jsonObject.get(FIELD_NAME_SHORT_MSG);
				    		}
				    		if (jsonObject.has(FIELD_NAME_LONG_MSG)) {
				    			longMsg = jsonObject.get(FIELD_NAME_LONG_MSG);
				    		}
					    	prevFailure = new PreviousFailureInformation(status,
					    				(shortMsg == null ? "" : shortMsg.toString()), 
					    				(longMsg == null ? "" : longMsg.toString()), 
					    	   			(response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) == null ? "" : response.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
		    			}
		    		}
			    	else if (contentType.contains(FIELD_CONTENT_TYPE_XML)) {
		    			try {
			        		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
				    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
				    		Document doc = docBuilder.parse(response.getEntity().getContent());
				    		doc.getDocumentElement().normalize();
				    		
				    		Node responseNode = doc.getChildNodes().item(0);
				    		if (responseNode != null) {
				    			
				    			String shortMsg = null;
				    			String longMsg = null;
				    			if (responseNode.getAttributes().getNamedItem(FIELD_NAME_SHORT_MSG) != null) {
				    				shortMsg = responseNode.getAttributes().getNamedItem(FIELD_NAME_SHORT_MSG).getNodeValue();
				    			}
				    			if (responseNode.getAttributes().getNamedItem(FIELD_NAME_LONG_MSG) != null) {
				    				longMsg = responseNode.getAttributes().getNamedItem(FIELD_NAME_LONG_MSG).getNodeValue();
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
			    	else { //Unexpected format
			    		try {
		    				prevFailure = new PreviousFailureInformation(status, "", EntityUtils.toString(response.getEntity(), Charsets.UTF_8), server);
		    			}
		    			catch (IOException e)
				    	{
				    		prevFailure = new PreviousFailureInformation(status, "Could not extract response from Server: " + server, "", server);
				    	}
			    	}
		    	}
		    	catch (IOException e)
		    	{
		            prevFailure = null;
		    	}
    		}
    		else { //Unexpected response not from Docmosis cloud or Tornado
    			try {
    				prevFailure = new PreviousFailureInformation(status, "Unexpected Response from Server: " + server, EntityUtils.toString(response.getEntity(), Charsets.UTF_8), server);
    			}
    			catch (IOException e)
		    	{
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