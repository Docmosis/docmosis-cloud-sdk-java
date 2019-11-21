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
package com.docmosis.sdk.template;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.docmosis.sdk.request.RequestBuilder;
import com.docmosis.sdk.response.MutableDocmosisCloudResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * This class manages the interaction with the Docmosis Template services endpoint
 *
 */
public class Template {

	/**
	 * Create a List Template Request. Run .execute() to run the request and return a List Template Response.
	 * @return ListTemplatesRequest
	 */
	public static ListTemplatesRequest list()
	{
		final ListTemplatesRequest req = new ListTemplatesRequest();
		
		return req;
	}

	/**
	 * Create a Get Template Details Request. Run .execute() to run the request and return a Get Template Details Response.
	 * @return GetTemplateDetailsRequest
	 */
	public static GetTemplateDetailsRequest getDetails()
	{
		final GetTemplateDetailsRequest req = new GetTemplateDetailsRequest();
		
		return req;
	}

	/**
	 * Create a Get Template Structure Request. Run .execute() to run the request and return a Get Template Structure Response.
	 * @return GetTemplateStructureRequest
	 */
	public static GetTemplateStructureRequest getStructure()
	{
		final GetTemplateStructureRequest req = new GetTemplateStructureRequest();
		
		return req;
	}

	/**
	 * Create a Get Template Request. Run .execute() to run the request and return a Get Template Response.
	 * @return GetTemplateRequest
	 */
	public static GetTemplateRequest get()
	{
		final GetTemplateRequest req = new GetTemplateRequest();
		
		return req;
	}

	/**
	 * Create an Upload Template Request. Run .execute() to run the request and return an Upload Template Response.
	 * @return UploadTemplateRequest
	 */
	public static UploadTemplateRequest upload()
	{
		final UploadTemplateRequest req = new UploadTemplateRequest();
		
		return req;
	}

	/**
	 * Create a Delete Template Request. Run .execute() to run the request and return a Delete Template Response.
	 * @return DeleteTemplateRequest
	 */
	public static DeleteTemplateRequest delete()
	{
		final DeleteTemplateRequest req = new DeleteTemplateRequest();
		
		return req;
	}

	/**
	 * Create a Get Template Sample Data Request. Run .execute() to run the request and return a Get Template Sample Data Response.
	 * @return GetSampleDataRequest
	 */
	public static GetSampleDataRequest getSampleData()
	{
		final GetSampleDataRequest req = new GetSampleDataRequest();
		
		return req;
	}
	
	/**
	 * Execute a listTemplates request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	protected static ListTemplatesResponse executelist(ListTemplatesRequest request) throws TemplateException
	{
	    ListTemplatesResponse response;
	    MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();

	    try {
	    	//Build request
		    HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	response = new ListTemplatesResponse(mutableResponse.build());

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
	
			    	if (jsonObject.has("templateListStale")) {
			    		response.setTemplateListStale(Boolean.parseBoolean(jsonObject.get("templateListStale").getAsString()));
			    	}
			    	if (jsonObject.has("nextPageToken")) {
			    		response.setNextPageToken(jsonObject.get("nextPageToken").getAsString());
			    	}
			    	if (jsonObject.has("pageSize")) {
			    		response.setPageSize(jsonObject.get("pageSize").getAsInt());
			    	}
					Type listType = new TypeToken<List<TemplateDetails>>() {}.getType();
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
					List<TemplateDetails> templates = gson.fromJson(jsonObject.get("templateList"), listType);
					response.setTemplates(templates);
			    } else {
			    	throw new TemplateException("Cannot extract data from response.");
			    }
	    	}
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}

	/**
	 * Execute a getTemplateDetails request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	protected static GetTemplateDetailsResponse executeGetTemplateDetails(GetTemplateDetailsRequest request) throws TemplateException
	{
	    GetTemplateDetailsResponse response;
	    MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	response = new GetTemplateDetailsResponse(mutableResponse.build());

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
					TemplateDetails template = gson.fromJson(jsonObject.get("templateDetails"), TemplateDetails.class);
					response.setTemplateDetails(template);
			    } else {
			    	throw new TemplateException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}

	/**
	 * Execute a getTemplateStructure request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	protected static GetTemplateStructureResponse executeGetTemplateStructure(GetTemplateStructureRequest request) throws TemplateException
	{
	    GetTemplateStructureResponse response;
	    MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	response = new GetTemplateStructureResponse(mutableResponse.build());

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					JsonElement template = jsonObject.get("templateStructure");
					response.setTemplateStructure(template);
			    } else {
			    	throw new TemplateException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}
	
	/**
	 * Execute an uploadTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */	
	protected static UploadTemplateResponse executeUploadTemplate(UploadTemplateRequest request) throws TemplateException
	{
		UploadTemplateResponse response;
		MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

		    //Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	response = new UploadTemplateResponse(mutableResponse.build());
	    	
	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
					JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
					TemplateDetails template = gson.fromJson(jsonObject.get("templateDetails"), TemplateDetails.class);
					response.setTemplateDetails(template);
			    } else {
			    	throw new TemplateException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}

	/**
	 * Execute a deleteTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	protected static DeleteTemplateResponse executeDeleteTemplate(DeleteTemplateRequest request) throws TemplateException
	{
	    DeleteTemplateResponse response;
	    MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	
	    	//Extract data from Response String
	    	if (mutableResponse.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
					JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					JsonElement shortMsg = jsonObject.get("shortMsg");
					mutableResponse.setShortMsg(shortMsg.getAsString());
			    } else {
			    	throw new TemplateException("Cannot extract data from response.");
			    }
	    	}
	    	response = new DeleteTemplateResponse(mutableResponse.build());
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}

	/**
	 * Execute a getTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	protected static GetTemplateResponse executeGetTemplate(GetTemplateRequest request) throws TemplateException
	{
	    GetTemplateResponse response;
	    MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	response = new GetTemplateResponse(mutableResponse.build());
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}

	/**
	 * Execute a getSampleData request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	protected static GetSampleDataResponse executeGetSampleData(GetSampleDataRequest request) throws TemplateException
	{
	    GetSampleDataResponse response;
	    MutableDocmosisCloudResponse mutableResponse = new MutableDocmosisCloudResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());
	    	
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(mutableResponse, request, payload);
	    	response = new GetSampleDataResponse(mutableResponse.build());
	    	
	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
		    	if (responseString != null && responseString.length() > 0) {
		    		JsonElement jsonElement = new JsonParser().parse(responseString).getAsJsonObject().get("templateSampleData");
					
					//Remove escape characters
					String templateSampleDataStr = jsonElement.toString();
					templateSampleDataStr = templateSampleDataStr.replace("\\n", "").replace("\\r", "").replace("\\", "").replace(" ", "");
					templateSampleDataStr = templateSampleDataStr.substring(1, templateSampleDataStr.length()-1);
					
					if (request.isFormatJson()) { //Convert String to JsonObject
						JsonElement templateSampleData = new JsonParser().parse(templateSampleDataStr);
						response.setSampleDataJson(templateSampleData);
					}
					else { //Convert String to XMLObject
						try {
					        // Turn xml string into a document
					        Document document = DocumentBuilderFactory.newInstance()
					                .newDocumentBuilder()
					                .parse(new InputSource(new ByteArrayInputStream(templateSampleDataStr.getBytes("utf-8"))));
					        
					        // Remove whitespaces outside tags
					        document.normalize();
					        XPath xPath = XPathFactory.newInstance().newXPath();
					        NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
					                                                      document,
					                                                      XPathConstants.NODESET);
	
					        for (int i = 0; i < nodeList.getLength(); ++i) {
					            Node node = nodeList.item(i);
					            node.getParentNode().removeChild(node);
					        }
					        response.setSampleDataXml(document);
						} catch (Exception e) {
					        throw new RuntimeException(e);
					    }
					}
					response.setJson(request.isFormatJson()); //Set isJson boolean
		    	} else {
			    	throw new TemplateException("Cannot extract data from response.");
			    }
	    	}
	    }
	    catch (DocmosisException e) {
	    	throw new TemplateException(e);
	    }
		return response;
	}
}
