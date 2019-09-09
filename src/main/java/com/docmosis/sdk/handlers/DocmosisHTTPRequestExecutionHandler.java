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
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
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

import com.docmosis.sdk.convert.ConverterResponse;
import com.docmosis.sdk.environmentconfiguration.EnvironmentBuilder;
import com.docmosis.sdk.environmentconfiguration.InvalidEnvironmentException;
import com.docmosis.sdk.environmentconfiguration.Proxy;
import com.docmosis.sdk.render.RenderRequest;
import com.docmosis.sdk.render.RenderResponse;
import com.docmosis.sdk.request.DocmosisCloudFileRequest;
import com.docmosis.sdk.request.DocmosisCloudRequest;
import com.docmosis.sdk.response.DocmosisCloudResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DocmosisHTTPRequestExecutionHandler {

	private static final String FIELD_HEADER_X_DOCMOSIS_SVR = "X-Docmosis-Server";
	private static final String FIELD_HEADER_X_DOCMOSIS_REQUEST_ID     = "X-Docmosis-RequestId";
	private static final String FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED = "X-Docmosis-PagesRendered";
	private static final String FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT   = "X-Docmosis-BytesOutput";
	public static DocmosisHTTPRequestRetryHandler retryHandler;
	public static DocmosisServiceUnavailableRetryStrategy retryStrategy;

	/**
	 * Method executes the http POST via the apache http library.
	 * @param request the request object.
	 * @param payload the content to be sent with the POST.
	 */
	public static String executeHttpPost(DocmosisCloudResponse response, DocmosisCloudRequest<?> request, HttpEntity payLoad, Logger log, boolean requestIsJson) throws DocmosisException
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
	    	retryHandler = new DocmosisHTTPRequestRetryHandler(
	    			request.getMaxTries(), log);
	    	retryStrategy = new DocmosisServiceUnavailableRetryStrategy(
	    			request.getMaxTries(), request.getRetryDelay(), log, requestIsJson);

	    	//Create HTTP Client
	    	HttpClientBuilder clientBuilder = HttpClients
	    										.custom()
	    										.setRetryHandler(retryHandler)
	    										.setServiceUnavailableRetryStrategy(retryStrategy);
	    	
	    	RequestConfig.Builder configBuilder = RequestConfig.custom();

	    	//Create HTTP POST method and set data
    		httpPost = new HttpPost(request.getUrl());
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
    	            .setConnectTimeout((int)request.getConnectTimeout())
    	            .setSocketTimeout((int)request.getReadTimeout())
	    			.build();
	    			
	    	httpPost.setConfig(config);
 	    
    	    //Execute the request and populate the response object
    	    chResponse = client.execute(httpPost);
    	    setResponse(response, chResponse, request.getUrl(), log, requestIsJson);
    	    
    	    try {
	    	    if (request instanceof DocmosisCloudFileRequest) { //A file may have been returned
	    	    	if (request instanceof RenderRequest) {  //A Rendered file may have been returned
	    	    		RenderRequest rr = (RenderRequest) request;
	    	    		if (rr.getStoreTo() == null || rr.getStoreTo().contains("stream")) {
	    	    			if (chResponse.getStatusLine().getStatusCode() == 200 && chResponse.getEntity() != null) {
	    	    				final String contentType = chResponse.getFirstHeader("Content-Type").getValue();
	    	    				if (contentType.startsWith("application/json") || contentType.startsWith("application/xml")) {
	    	    	    			// text payload - ignore for successful results
	    	    					logEntry(log, "Response File stored to:" + rr.getStoreTo(), Level.FINE);
	    	    	    		} else {
	    	    	    			// binary payload
	    	    	    			rr.sendDocumentTo(chResponse.getEntity().getContent());
	    	    	    			logEntry(log, "Rendered File Output to:" + rr.getOutputName(), Level.FINE);
	    	    	    		}
			    	    	}
	    	    		}
	    	    	} else {
		    	    	//StoreDocument
		    	    	DocmosisCloudFileRequest<?> requestFile = (DocmosisCloudFileRequest<?>) request;
		    	    	if (chResponse.getStatusLine().getStatusCode() == 200 && chResponse.getEntity() != null) {
		    	    		requestFile.sendDocumentTo(chResponse.getEntity().getContent());
		    	    		logEntry(log, "Response File Output to:" + requestFile.getOutputName(), Level.FINE);
		    	    	}
	    	    	}
	    	    } else { // A json String has been returned
	    	    	//Get json response String
    	    		if (chResponse.getStatusLine().getStatusCode() == 200 && chResponse.getEntity() != null) {
    	    			responseString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
    	    			logEntry(log, "Response String extracted", Level.FINE);
    	    		}
	    	    }
    	    } catch (FileNotFoundException e) {
	    		log.log(Level.SEVERE, "FileNotFoundException. Cannot save response.", e);
	            throw new DocmosisException(e);
    	    } catch (IOException e) {
	    		log.log(Level.SEVERE, "IOException. Cannot extract data from response.", e);
	            throw new DocmosisException("Cannot extract data from response.", e);
	        }
            
        } catch (ConnectException e) {
            // can't make the connection
            log.log(Level.SEVERE, "Unable to connect to the Docmosis service.  Please check your URL and proxy settings:", e);
            throw new DocmosisException("Unable to connect to the Docmosis Cloud", e);
        } catch (ClientProtocolException e) {
        	log.log(Level.SEVERE, "Error in the HTTP Protocol and/or Headers:", e);
        	throw new DocmosisException("Error in the HTTP Protocol and/or Headers:", e);
        } catch (IOException e) {
        	log.log(Level.SEVERE, "Error processing request:", e);
        	throw new DocmosisException(e);
        }
	    finally {
	    	try {
	    		if (chResponse != null) {
	    				chResponse.close();
	    			}
				client.close();
			} catch (IOException e) {
				// quietly ignore 
			}
	    }
        return responseString;
	}

	public static String executeHttpPost(DocmosisCloudResponse response, DocmosisCloudRequest<?> request, HttpEntity payLoad, Logger log) throws DocmosisException
	{
		return executeHttpPost(response, request, payLoad, log, true);
	}

	/**
	 * 
	 * Generic method to set common Response details and the Failure Response message if status != 200 (failed). 
	 */
	public static void setResponse(DocmosisCloudResponse response, CloseableHttpResponse chResponse, String url, Logger log, boolean requestIsJson) throws IOException
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
    				setFailureResponseXml(response, chResponse, log);
    			}
    			if (response.getLongMsg() == null) {
    				response.setLongMsg("URL [" + url + "] is not valid.");
    			}
    			log.log(Level.WARNING, "Status = 404, " + response.getShortMsg() != "" ? response.getShortMsg() : response.getLongMsg());

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
    				setFailureResponseXml(response, chResponse, log);
    			}
    			if (response.getLongMsg() == null) {
    				response.setLongMsg("URL [" + url + "] is not available.");
    			}
    			log.log(Level.WARNING, "Status = " + status + ", " + response.getShortMsg() != "" ? response.getShortMsg() : response.getLongMsg());

    		} else {
				if (retryStrategy.getPrevFailure() != null) { //Building the previous failure object already consumed the response
    				response.setShortMsg(retryStrategy.getPrevFailure().getShortMsg());
    				response.setLongMsg(retryStrategy.getPrevFailure().getLongMsg());
    			}
				else if (requestIsJson) {
    				setFailureResponseJson(response, chResponse);
    			}
    			else {
    				setFailureResponseXml(response, chResponse, log);
    			}
				log.log(Level.WARNING, "Status = " + status + ", " + response.getShortMsg() != "" ? response.getShortMsg() : 
					response.getLongMsg() != "" ? response.getLongMsg() : "");
    		}
    	}
		
		//Set common values.
		response.setStatus(status);
		response.setPreviousFailureInformation(retryStrategy.getPrevFailure());
		response.setTries(retryStrategy.getTries());
		if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_SVR)) {
    		response.setServerId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_SVR).getValue());
    	}
		if (response instanceof RenderResponse) {
			RenderResponse RResponse = (RenderResponse) response;
			if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED)) {
	    		RResponse.setPagesRendered(toInt(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED).getValue()));
	    	}
			if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_REQUEST_ID)) {
				RResponse.setRequestId(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_REQUEST_ID).getValue());
	    	}
		}
		else if (response instanceof ConverterResponse) {
			ConverterResponse CResponse = (ConverterResponse) response;
	    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED)) {
	    		CResponse.setPagesConverted(toInt(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_PAGES_RENDERED).getValue()));
	    	}
	    	if (chResponse.containsHeader(FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT)) {
	    		CResponse.setLength(toLong(chResponse.getFirstHeader(FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT).getValue()));
	    	}
	    }
	}
	
    private static void setFailureResponseJson(DocmosisCloudResponse response, CloseableHttpResponse chResponse) throws IOException {
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
		response.setShortMsg(shortMsg == null ? "" : shortMsg.toString());
		response.setLongMsg(longMsg == null ? "" : longMsg.toString());
    }
    
    private static void setFailureResponseXml(DocmosisCloudResponse response, CloseableHttpResponse chResponse, Logger log) throws IOException {
    	try {
    		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse(chResponse.getEntity().getContent());
    		doc.getDocumentElement().normalize();
    		String shortMsg = null;
    		String longMsg = null;
    		
    		Node responseNode = doc.getChildNodes().item(0);
    		if (responseNode != null) {
	    		shortMsg = getXMLStringAttribute(responseNode.getAttributes().getNamedItem("shortMsg"));
	    		longMsg = getXMLStringAttribute(responseNode.getAttributes().getNamedItem("longMsg"));
    		}
	    		
    		response.setShortMsg(shortMsg == null ? "" : shortMsg);
    		response.setLongMsg(longMsg == null ? "" : longMsg);
    		
    	} catch (ParserConfigurationException e) {
    		log.log(Level.SEVERE, "Unable to extract json error response", e);
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

	/**
	 * Print message to the log at a default level of FINE.
	 * @param message
	 */
	public static void logEntry(Logger log, String message)
	{
		logEntry(log, message, Level.FINE);
	}

	/**
	 * Print message to the log at desired level.
	 * @param message
	 */
	public static void logEntry(Logger log, String message, Level lvl)
	{
		if (log.isLoggable(lvl)) {
	        log.log(lvl, message);
        }
	}
	
	public static void logInit(Logger log, DocmosisCloudRequest<?> request) throws IOException
	{
		log.setLevel(request.getEnvironment().getLogLevel());
		if (request.getEnvironment().isLoggingEnabled()) {
			Handler fileHandler = new FileHandler(request.getEnvironment().getLogLocation(), true);
			fileHandler.setLevel(request.getEnvironment().getLogLevel());
			fileHandler.setEncoding("UTF-8");
			fileHandler.setFormatter(new SimpleFormatter());
			log.addHandler(fileHandler);
    	}
	}

	public static DocmosisHTTPRequestRetryHandler getRetryHandler() {
		return retryHandler;
	}

	public static DocmosisServiceUnavailableRetryStrategy getRetryStrategy() {
		return retryStrategy;
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
