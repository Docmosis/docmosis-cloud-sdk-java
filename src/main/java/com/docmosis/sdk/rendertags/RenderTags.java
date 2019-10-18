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

import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpEntity;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.docmosis.sdk.request.RequestBuilder;
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
	protected static GetRenderTagsResponse executeGetRenderTags(GetRenderTagsRequest request) throws RenderTagsException
	{
	    GetRenderTagsResponse response = new GetRenderTagsResponse();
	    
	    try {
	    	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	String responseString = DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);

	    	//Extract data from Response String
	    	if (response.hasSucceeded()) {
			    if (responseString != null && responseString.length() > 0) {
			    	JsonObject jsonObject = new JsonParser().parse(responseString).getAsJsonObject();
					
					Type renderTagsListType = new TypeToken<List<RenderTag>>() {}.getType();
					//Type listTagType = new TypeToken<List<Tag>>() {}.getType();
					Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
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
