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
import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
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
	public static ListTemplatesResponse executelist(ListTemplatesRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getEnvironment().getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getEnvironment().getAccessKey());
		}

		HttpEntity payload = builder.build();
	    ListTemplatesResponse response = new ListTemplatesResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    	
	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
	
			    	response.setTemplateListStale(Boolean.parseBoolean(jsonObject.get("templateListStale").getAsString()));
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
	public static GetTemplateDetailsResponse executeGetTemplateDetails(GetTemplateDetailsRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getEnvironment().getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getEnvironment().getAccessKey());
		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		else {
			throw new TemplateException("No Template Name specified.");
		}
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));

	    HttpEntity payload = builder.build();
	    GetTemplateDetailsResponse response = new GetTemplateDetailsResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);

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
	public static GetTemplateStructureResponse executeGetTemplateStructure(GetTemplateStructureRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getEnvironment().getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getEnvironment().getAccessKey());
		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		else {
			throw new TemplateException("No Template Name specified.");
		}

	    HttpEntity payload = builder.build();
	    GetTemplateStructureResponse response = new GetTemplateStructureResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					JsonObject template = jsonObject.getAsJsonObject("templateStructure");
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
	public static UploadTemplateResponse executeUploadTemplate(UploadTemplateRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		UploadTemplateRequestParams params = request.getParams();
//		if (request.getAccessKey() != null) {
//			builder.addTextBody("accessKey", request.getAccessKey());
//		}
//		if (params.getTemplateFile() != null) {
//			if (params.getTemplateFile().canRead()) {
//				builder.addBinaryBody("templateFile", params.getTemplateFile(), ContentType.APPLICATION_OCTET_STREAM, params.getTemplateFile().getName());
//			} else {
//				throw new TemplateException("cannot read template: ["
//	                    + params.getTemplateFile() + "]");
//			}
//		} else {
//			throw new TemplateException("No template selected");
//		}
//		if (params.getTemplateName() != null) {
//			builder.addTextBody("templateName", params.getTemplateName());
//		}
//		builder.addTextBody("templateDescription", params.getTemplateDescription());
//		builder.addTextBody("isSystemTemplate", String.valueOf(params.getIsSystemTemplate()));
//		builder.addTextBody("devMode", String.valueOf(params.getDevMode()));
//		builder.addTextBody("keepPrevOnFail", String.valueOf(params.getKeepPrevOnFail()));
//		builder.addTextBody("fieldDelimPrefix", params.getFieldDelimPrefix());
//		builder.addTextBody("fieldDelimSuffix", params.getFieldDelimSuffix());
//		builder.addTextBody("normalizeTemplateName", String.valueOf(params.getNormalizeTemplateName()));

		addField(builder, "accessKey", request.getEnvironment().getAccessKey());
		addField(builder, "templateFile", params.getTemplateFile());
		addField(builder, "templateName", params.getTemplateName());
		addField(builder, "templateDescription", params.getTemplateDescription());
		addField(builder, "isSystemTemplate", params.getIsSystemTemplate());
		addField(builder, "devMode", params.getDevMode());
		addField(builder, "keepPrevOnFail", params.getKeepPrevOnFail());
		addField(builder, "fieldDelimPrefix", params.getFieldDelimPrefix());
		addField(builder, "fieldDelimSuffix", params.getFieldDelimSuffix());
		addField(builder, "normalizeTemplateName", params.getNormalizeTemplateName());

		HttpEntity payload = builder.build();
	    UploadTemplateResponse response = new UploadTemplateResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    	
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
	public static DeleteTemplateResponse executeDeleteTemplate(DeleteTemplateRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getEnvironment().getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getEnvironment().getAccessKey());
		}
		if (request.getTemplateNames() != null) {
			List<String> templateNames = request.getTemplateNames();
			for(String templateName : templateNames) {
				builder.addTextBody("templateName", templateName);
			}
		}
		else {
			throw new TemplateException("No Template Name specified.");
		}
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));
	    
		HttpEntity payload = builder.build();
	    DeleteTemplateResponse response = new DeleteTemplateResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    	
	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
					JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					JsonElement shortMsg = jsonObject.get("shortMsg");
					response.setShortMsg(shortMsg.getAsString());
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
	 * Execute a getTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws TemplateException if execution fails or cannot extract data from response
	 */
	public static GetTemplateResponse executeGetTemplate(GetTemplateRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getEnvironment().getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getEnvironment().getAccessKey());
		}
		if (request.getTemplateNames() != null) {
			List<String> templateNames = request.getTemplateNames();
			for(String templateName : templateNames) {
				builder.addTextBody("templateName", templateName);
			}
		}
		else {
			throw new TemplateException("No Template Name specified.");
		}
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));
	    
		HttpEntity payload = builder.build();
	    GetTemplateResponse response = new GetTemplateResponse();
	    
	    try {
	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
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
	public static GetSampleDataResponse executeGetSampleData(GetSampleDataRequest request) throws TemplateException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getEnvironment().getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getEnvironment().getAccessKey());
		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		else {
			throw new TemplateException("No Template Name specified.");
		}
		if (request.getFormat() != null) {
			builder.addTextBody("format", request.getFormat());
		}
		else {
			builder.addTextBody("format", "");
		}
	    HttpEntity payload = builder.build();

	    GetSampleDataResponse response = new GetSampleDataResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    	
	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
		    	if (responseString != null && responseString.length() > 0) {
		    		JsonElement jsonElement = new JsonParser().parse(responseString).getAsJsonObject().get("templateSampleData");
					
					//Remove escape characters
					String templateSampleDataStr = jsonElement.toString();
					templateSampleDataStr = templateSampleDataStr.replace("\\n", "").replace("\\r", "").replace("\\", "").replace(" ", "");
					templateSampleDataStr = templateSampleDataStr.substring(1, templateSampleDataStr.length()-1);
					
					if (request.isFormatJson()) { //Convert String to JsonObject
						JsonObject templateSampleData = new JsonParser().parse(templateSampleDataStr).getAsJsonObject();
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
	
	private static boolean addField(MultipartEntityBuilder builder, String key, String param) {
		boolean rtn = false;
		if (param != null) {
			builder.addTextBody(key, param);
			rtn = true;
		}
		return rtn;
	}
	private static boolean addField(MultipartEntityBuilder builder, String key, Boolean param) {
		boolean rtn = false;
		if (param != null) {
			builder.addTextBody(key, String.valueOf(param));
			rtn = true;
		}
		return rtn;
	}
	private static boolean addField(MultipartEntityBuilder builder, String key, File param) throws TemplateException {
		boolean rtn = false;
		if (param != null) {
			if (param.canRead()) {
				builder.addTextBody(key, String.valueOf(param));
				builder.addBinaryBody(key, param, ContentType.APPLICATION_OCTET_STREAM, param.getName());
				rtn = true;
			} else {
				throw new TemplateException("cannot read " + key + ": [" + param + "]");
			}
		} else {
			throw new TemplateException("No " + key + " selected");
		}
		return rtn;
	}
}
