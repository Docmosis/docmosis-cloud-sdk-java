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

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * This class manages the interaction with the Docmosis FileStorage services endpoint
 *
 */
public class FileStorage {
	
	/**
	 * Create a List Files Request. Run .execute() to run the request and return a List Files Response.
	 * @return ListFilesRequest
	 */
	public static ListFilesRequest list()
	{
		final ListFilesRequest req = new ListFilesRequest();
		
		return req;
	}

	/**
	 * Create a Get File Request. Run .execute() to run the request and return a Get File Response.
	 * @return GetFileRequest
	 */
	public static GetFileRequest get()
	{
		final GetFileRequest req = new GetFileRequest();
		
		return req;
	}

	/**
	 * Create an Put File Request. Run .execute() to run the request and return an Put File Response.
	 * @return PutFileRequest
	 */
	public static PutFileRequest put()
	{
		final PutFileRequest req = new PutFileRequest();
		
		return req;
	}

	/**
	 * Create a Delete File Request. Run .execute() to run the request and return a Delete File Response.
	 * @return DeleteFileRequest
	 */
	public static DeleteFilesRequest delete()
	{
		final DeleteFilesRequest req = new DeleteFilesRequest();
		
		return req;
	}
	
	/**
	 * Create a Rename File Request. Run .execute() to run the request and return a Rename File Response.
	 * @return RenameFileRequest
	 */
	public static RenameFilesRequest rename()
	{
		final RenameFilesRequest req = new RenameFilesRequest();
		
		return req;
	}
	
	/**
	 * Execute a listFiles request.
	 * @param request Object
	 * @return Response Object
	 * @throws FileException if execution fails or cannot extract data from response
	 */
	public static ListFilesResponse executelist(ListFilesRequest request) throws FileException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getFolder() != null) {
			builder.addTextBody("folder", request.getFolder());
		}
		builder.addTextBody("includeSubFolders", String.valueOf(request.getIncludeSubFolders()));
		builder.addTextBody("includeMetaData", String.valueOf(request.getIncludeMetaData()));

		HttpEntity payload = builder.build();
	    ListFilesResponse response = new ListFilesResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
	
					response.setStoredFileListStale(Boolean.parseBoolean(jsonObject.get("storedFileListStale").getAsString()));
					Type listType = new TypeToken<List<FileDetails>>() {}.getType();
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
					List<FileDetails> files = gson.fromJson(jsonObject.get("storedFileList"), listType);
					response.setFiles(files);
			    } else {
			    	throw new FileException("Cannot extract data from response.");
			    }
	    	}
	    }
	    catch (DocmosisException e) {
	    	throw new FileException(e);
	    }
		return response;
	}
	
	/**
	 * Execute a PutFile request.
	 * @param request Object
	 * @return Response Object
	 * @throws FileException if execution fails or cannot extract data from response
	 */	
	public static PutFileResponse executePutFile(PutFileRequest request) throws FileException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getFile() != null) {
			if (request.getFile().canRead()) {
				builder.addBinaryBody("file", request.getFile(), ContentType.APPLICATION_OCTET_STREAM, request.getFile().getName());
			} else {
				throw new FileException("cannot read file: ["
	                    + request.getFile() + "]");
			}
		} else {
			throw new FileException("No file specified");
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
	    PutFileResponse response = new PutFileResponse();

	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);

	    	if (response.hasSucceeded()) {
		    	if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					JsonElement shortMsg = jsonObject.get("shortMsg");
					response.setShortMsg(shortMsg.getAsString());
			    } else {
			    	throw new FileException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	e.printStackTrace();
	    	throw new FileException(e);
	    }
		return response;
	}
	
	/**
	 * Execute a DeleteFile request.
	 * @param request Object
	 * @return Response Object
	 * @throws FileException if execution fails or cannot extract data from response
	 */	
	public static DeleteFilesResponse executeDeleteFiles(DeleteFilesRequest request) throws FileException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getPath() != null) {
			builder.addTextBody("path", request.getPath());
		}
		else {
			throw new FileException("No Path specified.");
		}
		builder.addTextBody("includeSubFolders", String.valueOf(request.getIncludeSubFolders()));

	    HttpEntity payload = builder.build();
	    DeleteFilesResponse response = new DeleteFilesResponse();
	    
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
			    	throw new FileException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	throw new FileException(e);
	    }
		return response;
	}
	
	/**
	 * Execute a getFile request.
	 * @param request Object
	 * @return Response Object
	 * @throws FileException if execution fails or cannot extract data from response
	 */
	public static GetFileResponse executeGetFile(GetFileRequest request) throws FileException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getFileName() != null) {
			builder.addTextBody("fileName", request.getFileName());
		}
		else {
			throw new FileException("No File Name specified.");
		}

		HttpEntity payload = builder.build();
	    GetFileResponse response = new GetFileResponse();
	    
	    try {
	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    }
	    catch (DocmosisException e) {
	    	throw new FileException(e);
	    }
		return response;
	}
	
	/**
	 * Execute a RenameFiles request.
	 * @param request Object
	 * @return Response Object
	 * @throws FileException if execution fails or cannot extract data from response
	 */	
	public static RenameFilesResponse executeRenameFiles(RenameFilesRequest request) throws FileException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getFromPath() != null) {
			builder.addTextBody("fromPath", request.getFromPath());
		}
		else {
			throw new FileException("No From Path specified.");
		}
		if (request.getToPath() != null) {
			builder.addTextBody("toPath", request.getToPath());
		}
		else {
			throw new FileException("No To Path specified.");
		}

	    HttpEntity payload = builder.build();
	    RenameFilesResponse response = new RenameFilesResponse();

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
			    	throw new FileException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	throw new FileException(e);
	    }
		return response;
	}
	
//	private static void setEnvironment(DocmosisCloudRequest<?> request) throws FileException {
//		try {
//    		EnvironmentBuilder.validate(request.getEnvironment());
//		} catch (InvalidEnvironmentException e1) {
//			throw new FileException(e1);
//		}
//	}
}
