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
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
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
	private static final Logger log = Logger.getLogger(Template.class.getName());
	
	/**
	 * Create a List Template Request. Run .execute() to run the request and return a List Template Response.
	 * @return ListTemplatesRequest
	 */
	public static ListTemplatesRequest list()
	{
		return new ListTemplatesRequest();
	}

	/**
	 * Create a Get Template Details Request. Run .execute() to run the request and return a Get Template Details Response.
	 * @return GetTemplateDetailsRequest
	 */
	public static GetTemplateDetailsRequest getDetails()
	{
		return new GetTemplateDetailsRequest();
	}

	/**
	 * Create a Get Template Structure Request. Run .execute() to run the request and return a Get Template Structure Response.
	 * @return GetTemplateStructureRequest
	 */
	public static GetTemplateStructureRequest getStructure()
	{
		return new GetTemplateStructureRequest();
	}

	/**
	 * Create a Get Template Request. Run .execute() to run the request and return a Get Template Response.
	 * @return GetTemplateRequest
	 */
	public static GetTemplateRequest get()
	{
		return new GetTemplateRequest();
	}

	/**
	 * Create an Upload Template Request. Run .execute() to run the request and return an Upload Template Response.
	 * @return UploadTemplateRequest
	 */
	public static UploadTemplateRequest upload()
	{
		return new UploadTemplateRequest();
	}

	/**
	 * Create a Delete Template Request. Run .execute() to run the request and return a Delete Template Response.
	 * @return DeleteTemplateRequest
	 */
	public static DeleteTemplateRequest delete()
	{
		return new DeleteTemplateRequest();
	}

	/**
	 * Create a Get Template Sample Data Request. Run .execute() to run the request and return a Get Template Sample Data Response.
	 * @return GetSampleDataRequest
	 */
	public static GetSampleDataRequest getSampleData()
	{
		return new GetSampleDataRequest();
	}

	/**
	 * Execute a listTemplates request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static ListTemplatesResponse executelist(ListTemplatesRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "listTemplates(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    ListTemplatesResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractListTemplatesResponse(chResponse, request.getUrl(), executionHandler);
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
	private static ListTemplatesResponse extractListTemplatesResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		ListTemplatesResponse response = new ListTemplatesResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

			response.setTemplateListStale(Boolean.parseBoolean(jsonObject.get("templateListStale").getAsString()));
			
			Type listType = new TypeToken<List<TemplateDetails>>() {}.getType();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			List<TemplateDetails> templates = gson.fromJson(jsonObject.get("templateList"), listType);
			response.setTemplates(templates);
    	}
    	
		executionHandler.setFailureResponse(response, chResponse, url, status);
    	
		return response;
	}

	/**
	 * Execute a getTemplateDetails request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetTemplateDetailsResponse executeGetTemplateDetails(GetTemplateDetailsRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "getTemplateDetails(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		else {
			throw new DocmosisException("No Template Name specified.");
		}
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetTemplateDetailsResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetTemplateDetailsResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in GetTemplateDetailsResponse object.
	 */
	private static GetTemplateDetailsResponse extractGetTemplateDetailsResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		GetTemplateDetailsResponse response = new GetTemplateDetailsResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			TemplateDetails template = gson.fromJson(jsonObject.get("templateDetails"), TemplateDetails.class);
			response.setTemplateDetails(template);
			
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}

	/**
	 * Execute a getTemplateStructure request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetTemplateStructureResponse executeGetTemplateStructure(GetTemplateStructureRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "getTemplateStructure(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		else {
			throw new DocmosisException("No Template Name specified.");
		}
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetTemplateStructureResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetTemplateStructureResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in GetTemplateStructureResponse object.
	 */
	private static GetTemplateStructureResponse extractGetTemplateStructureResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		GetTemplateStructureResponse response = new GetTemplateStructureResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Structure from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			JsonObject template = jsonObject.getAsJsonObject("templateStructure");
			response.setTemplateStructure(template);
			
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
	
	/**
	 * Execute an uploadTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */	
	public static UploadTemplateResponse executeUploadTemplate(UploadTemplateRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "uploadTemplate(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}

		if (request.getTemplateFile() != null) {
			builder.addBinaryBody("templateFile", request.getTemplateFile(), ContentType.APPLICATION_OCTET_STREAM, request.getTemplateFile().getName());
		}
		else {
			throw new DocmosisException("No Template File specified.");
		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		builder.addTextBody("templateDescription", request.getTemplateDescription());
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));
		builder.addTextBody("devMode", String.valueOf(request.getDevMode()));
		builder.addTextBody("keepPrevOnFail", String.valueOf(request.getKeepPrevOnFail()));
		builder.addTextBody("fieldDelimPrefix", request.getFieldDelimPrefix());
		builder.addTextBody("fieldDelimSuffix", request.getFieldDelimSuffix());
		builder.addTextBody("normalizeTemplateName", String.valueOf(request.getNormalizeTemplateName()));

		HttpEntity payload = builder.build();

	    CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    UploadTemplateResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractUploadTemplateResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in UploadTemplateResponse object.
	 */
	private static UploadTemplateResponse extractUploadTemplateResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		UploadTemplateResponse response = new UploadTemplateResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			TemplateDetails template = gson.fromJson(jsonObject.get("templateDetails"), TemplateDetails.class);
			response.setTemplateDetails(template);
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}

	/**
	 * Execute a deleteTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static DeleteTemplateResponse executeDeleteTemplate(DeleteTemplateRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "deleteTemplate(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getTemplateNames() != null) {
			List<String> templateNames = request.getTemplateNames();
			for(String templateName : templateNames) {
				builder.addTextBody("templateName", templateName);
			}
		}
		else {
			throw new DocmosisException("No Template Name specified.");
		}
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));
	    
		HttpEntity payload = builder.build();
		
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    DeleteTemplateResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractDeleteTemplateResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in DeleteTemplateResponse object.
	 */
	private static DeleteTemplateResponse extractDeleteTemplateResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		DeleteTemplateResponse response = new DeleteTemplateResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			JsonElement shortMsg = jsonObject.get("shortMsg");
			response.setShortMsg(shortMsg.getAsString());
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}

	/**
	 * Execute a getTemplate request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetTemplateResponse executeGetTemplate(GetTemplateRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "getTemplate(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getTemplateNames() != null) {
			List<String> templateNames = request.getTemplateNames();
			for(String templateName : templateNames) {
				builder.addTextBody("templateName", templateName);
			}
		}
		else {
			throw new DocmosisException("No Template Name specified.");
		}
		builder.addTextBody("isSystemTemplate", String.valueOf(request.getIsSystemTemplate()));
	    
		HttpEntity payload = builder.build();
		
	    CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetTemplateResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetTemplateResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in GetTemplateResponse object.
	 */
	private static GetTemplateResponse extractGetTemplateResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		GetTemplateResponse response = new GetTemplateResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			response.setDocument(chResponse.getEntity().getContent());
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}

	/**
	 * Execute a getSampleData request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetSampleDataResponse executeGetSampleData(GetSampleDataRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "getSampleData(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getTemplateName() != null) {
			builder.addTextBody("templateName", request.getTemplateName());
		}
		else {
			throw new DocmosisException("No Template Name specified.");
		}
		if (request.getFormat() != null) {
			builder.addTextBody("format", request.getFormat());
		}
		else {
			builder.addTextBody("format", "");
		}
	    HttpEntity payload = builder.build();

	    CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetSampleDataResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetSampleDataResponse(chResponse, request.getUrl(), request.isFormatJson(), executionHandler);
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
	 * Extract data from response object and store in GetSampleDataResponse object.
	 */
	private static GetSampleDataResponse extractGetSampleDataResponse(CloseableHttpResponse chResponse, String url, boolean isJson, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException, RuntimeException
	{
		GetSampleDataResponse response = new GetSampleDataResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Template Data from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonElement jsonElement = new JsonParser().parse(jsonString).getAsJsonObject().get("templateSampleData");
			
			//Remove escape characters
			String templateSampleDataStr = jsonElement.toString();
			templateSampleDataStr = templateSampleDataStr.replace("\\n", "").replace("\\", "").replace(" ", "");
			templateSampleDataStr = templateSampleDataStr.substring(1, templateSampleDataStr.length()-1);
			
			if (isJson) { //Convert String to JsonObject
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
			response.setJson(isJson); //Set isJson boolean
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
}
