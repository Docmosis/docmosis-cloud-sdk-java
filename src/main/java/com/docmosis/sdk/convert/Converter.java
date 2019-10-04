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
package com.docmosis.sdk.convert;

import org.apache.http.HttpEntity;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;
import com.docmosis.sdk.request.RequestBuilder;

/**
 * This class manages the interaction with the Docmosis convert service endpoint - converting
 * documents between formats
 *
 */
public class Converter {
    
    /**
     * Create the conversion request.
     * 
     * @return Conversion request object
     */
    public static ConverterRequest convert()
    {
    	final ConverterRequest req = new ConverterRequest();
    	
    	return req;
    }

    /**
     * Convert a document using Docmosis Web Services 
     * 
     * @param request the convert data.
     * @return a response object encapsulating the result of the operation
     * 
     * @throws ConverterException if something goes wrong executing the request
     */
    public static ConverterResponse executeConvert(final ConverterRequest request) throws ConverterException 
    {
        ConverterResponse response = new ConverterResponse();
        
        try {
        	//Build request
	    	HttpEntity payload = RequestBuilder.buildMultiPartRequest(request.getEnvironment().getAccessKey(), request.getParams());

	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    }
	    catch (DocmosisException e) {
	    	throw new ConverterException(e);
	    }
		return response;
    }
}
