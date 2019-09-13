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
 * This class manages the interaction with the Docmosis Image services endpoint
 *
 */
public class Image {
	
	/**
	 * Create a List Images Request. Run .execute() to run the request and return a List Images Response.
	 * @return ListImagesRequest
	 */
	public static ListImagesRequest list()
	{
		final ListImagesRequest req = new ListImagesRequest();
		
		return req;
	}

	/**
	 * Create a Get Image Request. Run .execute() to run the request and return a Get Image Response.
	 * @return GetImageeRequest
	 */
	public static GetImageRequest get()
	{
		final GetImageRequest req = new GetImageRequest();
		
		return req;
	}

	/**
	 * Create an Upload Image Request. Run .execute() to run the request and return an Upload Image Response.
	 * @return UploadImageRequest
	 */
	public static UploadImageRequest upload()
	{
		final UploadImageRequest req = new UploadImageRequest();
		
		return req;
	}

	/**
	 * Create a Delete Image Request. Run .execute() to run the request and return a Delete Image Response.
	 * @return DeleteImageRequest
	 */
	public static DeleteImageRequest delete()
	{
		final DeleteImageRequest req = new DeleteImageRequest();
		
		return req;
	}

	/**
	 * Execute a listImages request.
	 * @param request Object
	 * @return Response Object
	 * @throws ImageException if execution fails or cannot extract data from response
	 */
	public static ListImagesResponse executelist(ListImagesRequest request) throws ImageException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}

		HttpEntity payload = builder.build();
	    ListImagesResponse response = new ListImagesResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    	
	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
	
					response.setImageListStale(Boolean.parseBoolean(jsonObject.get("imageListStale").getAsString()));
					Type listType = new TypeToken<List<ImageDetails>>() {}.getType();
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
					List<ImageDetails> images = gson.fromJson(jsonObject.get("imageList"), listType);
					response.setImages(images);
			    } else {
			    	throw new ImageException("Cannot extract data from response.");
			    }
	    	}
	    }
	    catch (DocmosisException e) {
	    	throw new ImageException(e);
	    }
		return response;
	}
	
	/**
	 * Execute an uploadImage request.
	 * @param request Object
	 * @return Response Object
	 * @throws ImageException if execution fails or cannot extract data from response
	 */	
	public static UploadImageResponse executeUploadImage(UploadImageRequest request) throws ImageException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getImageFile() != null) {
			if (request.getImageFile().canRead()) {
				builder.addBinaryBody("imageFile", request.getImageFile(), ContentType.APPLICATION_OCTET_STREAM, request.getImageFile().getName());
			} else {
				throw new ImageException("cannot read image: ["
	                    + request.getImageFile() + "]");
			}
		} else {
			throw new ImageException("No Image File specified.");
		}
		if (request.getImageName() != null) {
			builder.addTextBody("imageName", request.getImageName());
		}
		builder.addTextBody("imageDescription", request.getImageDescription());
		builder.addTextBody("isSystemImage", String.valueOf(request.getIsSystemImage()));
		builder.addTextBody("normalizeImageName", String.valueOf(request.getNormalizeImageName()));

	    HttpEntity payload = builder.build();
	    UploadImageResponse response = new UploadImageResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
					JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
					ImageDetails image = gson.fromJson(jsonObject.get("imageDetails"), ImageDetails.class);
					response.setImageDetails(image);
			    } else {
			    	throw new ImageException("Cannot extract data from response.");
			    }
	    	}
	    } 
	    catch (DocmosisException e) {
	    	throw new ImageException(e);
	    }
		return response;
	}
	
	/**
	 * Execute a deleteImage request.
	 * @param request Object
	 * @return Response Object
	 * @throws ImageException if execution fails or cannot extract data from response
	 */
	public static DeleteImageResponse executeDeleteImage(DeleteImageRequest request) throws ImageException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getImageNames() != null) {
			List<String> imageNames = request.getImageNames();
			for(String imageName : imageNames) {
				builder.addTextBody("imageName", imageName);
			}
		}
		else {
			throw new ImageException("No Image Name specified.");
		}
		builder.addTextBody("isSystemImage", String.valueOf(request.getIsSystemImage()));
		
	    HttpEntity payload = builder.build();
	    DeleteImageResponse response = new DeleteImageResponse();
	    
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
			    	throw new ImageException("Cannot extract data from response.");
			    }
	    	}
	    }
	    catch (DocmosisException e) {
	    	throw new ImageException(e);
	    }
		return response;
	}

	/**
	 * Execute a getImage request.
	 * @param request Object
	 * @return Response Object
	 * @throws ImageException if execution fails or cannot extract data from response
	 */
	public static GetImageResponse executeGetImage(GetImageRequest request) throws ImageException
	{
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getImageNames() != null) {
			List<String> imageNames = request.getImageNames();
			for(String imageName : imageNames) {
				builder.addTextBody("imageName", imageName);
			}
		}
		else {
			throw new ImageException("No Image Name specified.");
		}
		builder.addTextBody("isSystemImage", String.valueOf(request.getIsSystemImage()));

	    HttpEntity payload = builder.build();
	    GetImageResponse response = new GetImageResponse();

	    try {
	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    }
	    catch (DocmosisException e) {
	    	throw new ImageException(e);
	    }
		return response;
	}

//	private static void setEnvironment(DocmosisCloudRequest<?> request) throws ImageException {
//		try {
//    		EnvironmentBuilder.validate(request.getEnvironment());
//		} catch (InvalidEnvironmentException e1) {
//			throw new ImageException(e1);
//		}
//	}
}
