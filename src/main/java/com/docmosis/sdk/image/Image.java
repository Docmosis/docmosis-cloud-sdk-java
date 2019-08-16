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
package com.docmosis.sdk.image;

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
 * This class manages the interaction with the Docmosis Image services endpoint
 *
 */
public class Image {
	private static final Logger log = Logger.getLogger(Image.class.getName());
	
	/**
	 * Create a List Images Request. Run .execute() to run the request and return a List Images Response.
	 * @return ListImagesRequest
	 */
	public static ListImagesRequest list()
	{
		return new ListImagesRequest();
	}

	/**
	 * Create a Get Image Request. Run .execute() to run the request and return a Get Image Response.
	 * @return GetImageeRequest
	 */
	public static GetImageRequest get()
	{
		return new GetImageRequest();
	}

	/**
	 * Create an Upload Image Request. Run .execute() to run the request and return an Upload Image Response.
	 * @return UploadImageRequest
	 */
	public static UploadImageRequest upload()
	{
		return new UploadImageRequest();
	}

	/**
	 * Create a Delete Image Request. Run .execute() to run the request and return a Delete Image Response.
	 * @return DeleteImageRequest
	 */
	public static DeleteImageRequest delete()
	{
		return new DeleteImageRequest();
	}

	/**
	 * Execute a listImages request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static ListImagesResponse executelist(ListImagesRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "listImages(" + request.toString() + ")");
		
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
	    ListImagesResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractListImagesResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in ListImagesResponse object.
	 */
	private static ListImagesResponse extractListImagesResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		ListImagesResponse response = new ListImagesResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();

			response.setImageListStale(Boolean.parseBoolean(jsonObject.get("imageListStale").getAsString()));
			
			Type listType = new TypeToken<List<ImageDetails>>() {}.getType();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			List<ImageDetails> images = gson.fromJson(jsonObject.get("imageList"), listType);
			response.setImages(images);
    	}
    	
		executionHandler.setFailureResponse(response, chResponse, url, status);
    	
		return response;
	}
	
	/**
	 * Execute an uploadImage request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */	
	public static UploadImageResponse executeUploadImage(UploadImageRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "uploadImage(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}

		if (request.getImageFile() != null) {
			builder.addBinaryBody("imageFile", request.getImageFile(), ContentType.APPLICATION_OCTET_STREAM, request.getImageFile().getName());
		}
		else {
			throw new DocmosisException("No Image File specified.");
		}
		if (request.getImageName() != null) {
			builder.addTextBody("imageName", request.getImageName());
		}
		builder.addTextBody("imageDescription", request.getImageDescription());
		builder.addTextBody("isSystemImage", String.valueOf(request.getIsSystemImage()));
		builder.addTextBody("normalizeImageName", String.valueOf(request.getNormalizeImageName()));

	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    UploadImageResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractUploadImageResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in UploadImageResponse object.
	 */
	private static UploadImageResponse extractUploadImageResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		UploadImageResponse response = new UploadImageResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			//Extract Details from Json
			String jsonString = EntityUtils.toString(chResponse.getEntity(), "UTF-8");
			JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
			ImageDetails image = gson.fromJson(jsonObject.get("imageDetails"), ImageDetails.class);
			response.setImageDetails(image);
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
	
	/**
	 * Execute a deleteImage request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static DeleteImageResponse executeDeleteImage(DeleteImageRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "deleteImage(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getImageNames() != null) {
			List<String> imageNames = request.getImageNames();
			for(String imageName : imageNames) {
				builder.addTextBody("imageName", imageName);
			}
		}
		else {
			throw new DocmosisException("No Image Name specified.");
		}
		builder.addTextBody("isSystemImage", String.valueOf(request.getIsSystemImage()));
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    DeleteImageResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractDeleteImageResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in DeleteImageResponse object.
	 */
	private static DeleteImageResponse extractDeleteImageResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		DeleteImageResponse response = new DeleteImageResponse();
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
	 * Execute a getImage request.
	 * @param request Object
	 * @return Response Object
	 * @throws DocmosisException if execution fails or cannot extract data from response
	 */
	public static GetImageResponse executeGetImage(GetImageRequest request) throws DocmosisException
	{
		DocmosisHTTPRequestExecutionHandler.logEntry(log, "getImage(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
//		else {
//			throw new DocmosisException("No Access Key specified.");
//		}
		if (request.getImageNames() != null) {
			List<String> imageNames = request.getImageNames();
			for(String imageName : imageNames) {
				builder.addTextBody("imageName", imageName);
			}
		}
		else {
			throw new DocmosisException("No Image Name specified.");
		}
		builder.addTextBody("isSystemImage", String.valueOf(request.getIsSystemImage()));
	    HttpEntity payload = builder.build();
		CloseableHttpClient client = null;
	    CloseableHttpResponse chResponse = null;
	    GetImageResponse response = null;
	    DocmosisHTTPRequestExecutionHandler executionHandler = new DocmosisHTTPRequestExecutionHandler();
	    
	    try {
	    	//Execute request
	    	chResponse = executionHandler.executeHttpPost(request.getUrl(), request.getMaxTries(), request.getRetryDelay(), payload, client, log);
	    	//Extract Response
		    response = extractGetImageResponse(chResponse, request.getUrl(), executionHandler);
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
	 * Extract data from response object and store in GetImageResponse object.
	 */
	private static GetImageResponse extractGetImageResponse(CloseableHttpResponse chResponse, String url, DocmosisHTTPRequestExecutionHandler executionHandler) throws IOException
	{
		GetImageResponse response = new GetImageResponse();
		int status = chResponse.getStatusLine().getStatusCode();

		if (status == 200) { // succeeded

			response.setDocument(chResponse.getEntity().getContent());
    	}
		
		executionHandler.setFailureResponse(response, chResponse, url, status);
		
		return response;
	}
}
