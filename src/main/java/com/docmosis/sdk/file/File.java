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
package com.docmosis.sdk.file;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * This class manages the interaction with the Docmosis File services endpoint
 *
 */
public class File {
	private static final Logger log = Logger.getLogger(File.class.getName());
	
	/**
	 * Create a List Files Request. Run .execute() to run the request and return a List Files Response.
	 * @return ListFilesRequest
	 */
	public static ListFilesRequest list()
	{
		return new ListFilesRequest();
	}

	/**
	 * Create a Get File Request. Run .execute() to run the request and return a Get File Response.
	 * @return GetFileRequest
	 */
	public static GetFileRequest get()
	{
		return new GetFileRequest();
	}

	/**
	 * Create an Put File Request. Run .execute() to run the request and return an Put File Response.
	 * @return PutFileRequest
	 */
	public static PutFileRequest put()
	{
		return new PutFileRequest();
	}

	/**
	 * Create a Delete File Request. Run .execute() to run the request and return a Delete File Response.
	 * @return DeleteFileRequest
	 */
	public static DeleteFilesRequest delete()
	{
		return new DeleteFilesRequest();
	}
	
	/**
	 * Create a Rename File Request. Run .execute() to run the request and return a Rename File Response.
	 * @return RenameFileRequest
	 */
	public static RenameFilesRequest rename()
	{
		return new RenameFilesRequest();
	}
	
	/**
	 * Execute a listFiles request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static ListFilesResponse executelist(ListFilesRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "listFiles(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getFolder() != null) {
			builder.addTextBody("folder", request.getFolder());
		}
		builder.addTextBody("includeSubFolders", String.valueOf(request.getIncludeSubFolders()));
		builder.addTextBody("includeMetaData", String.valueOf(request.getIncludeMetaData()));
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    ListFilesResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractListFilesResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in ListFilesResponse object.
	 */
	private static ListFilesResponse extractListFilesResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		ListFilesResponse response = new ListFilesResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

			response.setStoredFileListStale(Boolean.parseBoolean(jsonObject.get("storedFileListStale").getAsString()));
			
			Type listType = new TypeToken<List<FileDetails>>() {}.getType();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			List<FileDetails> files = gson.fromJson(jsonObject.get("storedFileList"), listType);
			response.setFiles(files);
    	}
    	
		executionHandler.setFailureResponse(response, chResponse, url, status);
    	
		return response;
	}
	
	/**
	 * Execute a PutFile request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */	
	public static PutFileResponse executePutFile(PutFileRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "putFile(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		else {
			throw new DocmosisException("No Access Key specified.");
		}
		if (request.getFile() != null) {
			builder.addBinaryBody("file", request.getFile(), ContentType.APPLICATION_OCTET_STREAM, request.getFile().getName());
		}
		else {
			throw new DocmosisException("No File specified.");
		}
		if (request.getFileName() != null) {
			builder.addTextBody("fileName", request.getFileName());
		}
		if (request.getContentType() != null) {
			builder.addTextBody("contentType", request.getContentType());
		}
		if (request.getMetaData() != null) {
			builder.addTextBody("metaData", request.getMetaData());
		}
		//builder.addTextBody("length", String.valueOf(request.getFile()));
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    PutFileResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();

	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractPutFileResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in PutFileResponse object.
	 */
	private static PutFileResponse extractPutFileResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		PutFileResponse response = new PutFileResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded
			//Response details will be extracted below.
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
	
	/**
	 * Execute a DeleteFile request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */	
	public static DeleteFilesResponse executeDeleteFiles(DeleteFilesRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "putFile(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getPath() != null) {
			builder.addTextBody("path", request.getPath());
		}
		else {
			throw new DocmosisException("No Path specified.");
		}
		builder.addTextBody("includeSubFolders", String.valueOf(request.getIncludeSubFolders()));

	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    DeleteFilesResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractDeleteFilesResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in DeleteFileResponse object.
	 */
	private static DeleteFilesResponse extractDeleteFilesResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		DeleteFilesResponse response = new DeleteFilesResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded
			//Extract Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			JsonElement shortMsg = jsonObject.get("shortMsg");
			response.setShortMsg(shortMsg.getAsString());
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
	
	/**
	 * Execute a getFile request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetFileResponse executeGetFile(GetFileRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "getFile(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getFileName() != null) {
			builder.addTextBody("fileName", request.getFileName());
		}
		else {
			throw new DocmosisException("No File Name specified.");
		}

		HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetFileResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetFileResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in GetFileResponse object.
	 */
	private static GetFileResponse extractGetFileResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		GetFileResponse response = new GetFileResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			response.setDocument(chResponse.getEntity().getContent());
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
	
	/**
	 * Execute a RenameFiles request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */	
	public static RenameFilesResponse executeRenameFiles(RenameFilesRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "RenameFiles(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getFromPath() != null) {
			builder.addTextBody("fromPath", request.getFromPath());
		}
		else {
			throw new DocmosisException("No From Path specified.");
		}
		if (request.getToPath() != null) {
			builder.addTextBody("toPath", request.getToPath());
		}
		else {
			throw new DocmosisException("No To Path specified.");
		}

	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    RenameFilesResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();

	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractRenameFilesResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in RenameFilesResponse object.
	 */
	private static RenameFilesResponse extractRenameFilesResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		RenameFilesResponse response = new RenameFilesResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded
			//Extract Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			JsonElement shortMsg = jsonObject.get("shortMsg");
			response.setShortMsg(shortMsg.getAsString());
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
}
