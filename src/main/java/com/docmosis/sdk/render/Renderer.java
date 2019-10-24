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
package com.docmosis.sdk.render;



//TODO: Load testing. May need a queue around handler.
//TODO: Review simple examples.
//TODO: Easier way to build json data?



import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.docmosis.sdk.request.RequestBuilder;
import com.docmosis.sdk.request.param.ParamType;
import com.docmosis.sdk.request.param.RequestParameters;

/**
 * This class manages the interaction with the Docmosis render service endpoint - producing
 * documents.
 * 
 * Direct use of this class is not necessary since {@link RenderRequest#execute()} will invoke
 * this class as required.
 *
 */
public class Renderer {

    public Renderer()
    {
    }
 
    /**
	 * Create a Render Request. Run .execute() to run the request and return the Rendered File.
	 * @return RenderRequest
	 */
    public static RenderRequest render()
    {
    	final RenderRequest req = new RenderRequest();

    	return req;
    }
    
    /**
	 * Create a Render Form Request. Run .execute() to run the request and return the Rendered File.
	 * @return RenderFormRequest
	 */
    public static RenderFormRequest renderForm()
    {
    	final RenderFormRequest req = new RenderFormRequest();

    	return req;
    }

    /**
     * Render a Docmosis document.
     * 
     * @param request the render data.
     * @return a response object encapsulating the result of the render
     * 
     * @throws RendererException if something goes wrong constructing the request
     */
    protected static RenderResponse executeRender(final RenderRequest request) throws RendererException 
    {
        MutableRenderResponse response = new MutableRenderResponse();
        
    	final boolean requestIsJson = requestIsJson(request.getParams());
    	
    	try {
    		RequestBuilder.testRequiredParams(request.getParams());
    	}
    	catch (DocmosisException e) {
			throw new RendererException(e);
    	}

    	//Build request
    	final String accessKey = request.getEnvironment().getAccessKey();
        final String renderRequest = buildRequest(accessKey, request.getParams(), requestIsJson);

        StringEntity se = null;
        if (requestIsJson) {
        	se = new StringEntity(renderRequest, ContentType.create(ContentType.APPLICATION_JSON.getMimeType(), Charsets.UTF_8));
        }
        else {
        	se = new StringEntity(renderRequest, ContentType.create(ContentType.APPLICATION_XML.getMimeType(), Charsets.UTF_8));
        }
        
        try {
	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, se, requestIsJson);
	    }
	    catch (DocmosisException e) {
	    	throw new RendererException(e);
	    }
            
        return new RenderResponse(response.build());
    }

    /**
     * Render a Docmosis document.
     * 
     * @param request the render data.
     * @return a response object encapsulating the result of the render
     * 
     * @throws RendererException if something goes wrong constructing the request
     */
    protected static RenderResponse executeRenderForm(final RenderFormRequest request) throws RendererException 
    {
    	MutableRenderResponse response = new MutableRenderResponse();

        try {
        	//Combine request params with data params
        	RequestParameters combinedParams = new RequestParameters(request.getParams());
        	combinedParams.extend(request.getParams().getDataParams());
        	
        	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), combinedParams);

	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    }
	    catch (DocmosisException e) {
	    	throw new RendererException(e);
	    }
        return new RenderResponse(response.build());
    }

    
    /**
     * Determine if we are dealing with JSON or XML in general by inspecting 
     * the data in the request.  If in doubt, JSON.
     * 
     * @param req
     * @return
     */
    private static boolean requestIsJson(RenderRequestParams req)
    {
    	return req == null || isJson(req.getData());
    }
    
    /**
     * Determine if the given string looks like JSON
     * 
     * @param data full data string
     * @return true if it appears to be JSON or is null/blank
     */
    protected static boolean isJson(String data)
    {
    	boolean isJson = false;
    	
    	if (data == null || "".equals(data.trim())) {
    		// default to true
    		isJson = true;
    	} else {
    		// hunt for the first non-whitespace char - is it a '{'?
    		
    		for(int i=0; i < data.length(); i++) {
    			final char ch = data.charAt(i);
    			if (!Character.isWhitespace(ch)) {
    				if (ch == '{') {
    					isJson = true;
    				}
					break;
    			}
    		}
    		
    	}
    	
    	return isJson;
    }
    
    private static String buildRequest(final String accessKey, final RenderRequestParams request, 
    		final boolean jsonFormat) {
        StringBuilder buffer = new StringBuilder();

        
        // Start building the instruction.
        addField("accessKey", accessKey, buffer, true, jsonFormat);
        
        // add all parameters except for the DATA which we'll put last
        for(String paramName : request.getKeys()) {
        	if (!RenderRequestParams.DATA.equals(paramName)) {
        		ParamType value = request.getParam(paramName);
        		addField(paramName, value, buffer, true, jsonFormat);
        	}
        }

        // add the data formatted as required
        if (jsonFormat) {
        	String json = request.getData();
        	json = (json == null || "".equals(json.trim())) ? null : json;
        	
            addField("data", json, buffer, false, jsonFormat);
        	buffer = new StringBuilder(buffer.length() + 2).append("{").append(buffer).append("}");
        } else {
        	buffer.insert(0, "<?xml version=\"1.0\" encoding=\"utf-8\"?><render ");
        	buffer.append(">");
			if (request.getData().trim().startsWith("<data")) {
				buffer.append(request.getData());
			} else {
				buffer.append("<data>");
				buffer.append(request.getData());
				buffer.append("</data>");
			}

        	buffer.append("</render>");
        }

       
        return buffer.toString();
    }

    private static void addField(final String key, final ParamType value, final StringBuilder buffer,
            final boolean quoteValue, final boolean jsonFormat) 
	{
    	if (value != null) {
    		addField(key, value.stringValue(), buffer, quoteValue, jsonFormat);
    	}
	}


    private static void addField(final String key, final String value, final StringBuilder buffer,
                final boolean quoteValue, final boolean jsonFormat) 
    {
    	if (jsonFormat) {
    		addFieldJson(key, value, buffer, quoteValue);
    	} else {
    		addFieldXml(key, value, buffer, quoteValue);
    	}
    }
    
    private static void addFieldJson(final String key, final String value, final StringBuilder buffer,
            final boolean quoteValue)
    {
    	
        if (value != null) {
            if (buffer.length() > 0) {
                buffer.append(",");
            }
            buffer.append("\"");
            buffer.append(key);
        	buffer.append("\":");
            if (quoteValue) {
            	buffer.append("\"");
            }
            buffer.append(value);
            if (quoteValue) {
            	buffer.append("\"");
            }
        }
    }

    
    private static void addFieldXml(final String key, final String value, final StringBuilder buffer,
            final boolean quoteValue)
    {
    	if (value != null) {
    		buffer.append(" ").append(key).append("=\"").append(value).append('"');
    	}
    }

}
