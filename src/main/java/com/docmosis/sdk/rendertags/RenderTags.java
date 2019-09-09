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
package com.docmosis.sdk.rendertags;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.docmosis.sdk.render.Renderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * This class manages the interaction with the Docmosis Render Tags endpoint
 *
 */
public class RenderTags {
	
	private static final Logger log = Logger.getLogger(Renderer.class.getName());

    /**
	 * Create a Render Tags Request. Run .execute() to run the request and return the Render Tags Response.
	 * @return GetRenderTagsRequest
	 * 
	 */
    public static GetRenderTagsRequest get()
    {
    	final GetRenderTagsRequest req = new GetRenderTagsRequest();

    	return req;
    }
    
	/**
	 * Execute a GetRenderTags request.
	 * @param request Object
	 * @return Response Object
	 * @throws RenderTagsException if execution fails or cannot extract data from response
	 */
	public static GetRenderTagsResponse executeGetRenderTags(GetRenderTagsRequest request) throws RenderTagsException
	{
		//Initialize logger with environment settings.
    	try {
			DocmosisHTTPRequestExecutionHandler.logInit(log, request);
		} catch (IOException e1) {
			throw new RenderTagsException(e1);
		}

		DocmosisHTTPRequestExecutionHandler.logEntry(log, "GetRenderTags(" + request.toString() + ")");
		
		//Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
		if (request.getAccessKey() != null) {
			builder.addTextBody("tags", request.getTags());
		}
		else {
			throw new RenderTagsException("No Tags Specified.");
		}
		if (request.getYear() != 0) {
			builder.addTextBody("year", String.valueOf(request.getYear()));
		}
		if (request.getMonth() != 0) {
			builder.addTextBody("month", String.valueOf(request.getMonth()));
		}
		if (request.getnMonths() != 0) {
			builder.addTextBody("nMonths", String.valueOf(request.getnMonths()));
		}
		builder.addTextBody("padBlanks", String.valueOf(request.getPadBlanks()));
		
	    HttpEntity payload = builder.build();
	    GetRenderTagsResponse response = new GetRenderTagsResponse();
	    
	    try {
	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload, log);

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					Type renderTagsListType = new TypeToken<List<RenderTag>>() {}.getType();
					//Type listTagType = new TypeToken<List<Tag>>() {}.getType();
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssX").create();
					List<RenderTag> renderTags = gson.fromJson(jsonObject.get("renderTags"), renderTagsListType);
					response.setRenderTags(renderTags);
			    } else {
			    	throw new RenderTagsException("Cannot extract data from response.");
			    }
		    }
	    }
	    catch (DocmosisException e) {
	    	throw new RenderTagsException(e);
	    }
		return response;
	}
}
