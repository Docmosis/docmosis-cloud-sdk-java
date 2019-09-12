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
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.handlers.DocmosisHTTPRequestExecutionHandler;

/**
 * This class manages the interaction with the Docmosis convert service endpoint - converting
 * documents between formats
 *
 */
public class Converter {
    
    /**
     * Create the conversion request.
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

    	if (request.getFileToConvert() == null) {
    		throw new ConverterException("No conversion file specified");
    	}
        if (!request.getFileToConvert().canRead()) {
            throw new ConverterException("cannot read file to convert: ["
                            + request.getFileToConvert() + "]");
        }
        
        //Set environment
//    	try {
//    		EnvironmentBuilder.validate(request.getEnvironment());
//		} catch (InvalidEnvironmentException e1) {
//			throw new ConverterException(e1);
//		}
        
        //Build request
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		if (request.getAccessKey() != null) {
			builder.addTextBody("accessKey", request.getAccessKey());
		}
	    builder.addBinaryBody("file", request.getFileToConvert(), ContentType.APPLICATION_OCTET_STREAM, request.getFileToConvert().getName());
	    builder.addTextBody("outputName", request.getOutputName());

	    HttpEntity payload = builder.build();
        ConverterResponse response = new ConverterResponse();
        
        try {
	    	//Execute request
	    	DocmosisHTTPRequestExecutionHandler.executeHttpPost(response, request, payload);
	    }
	    catch (DocmosisException e) {
	    	throw new ConverterException(e);
	    }
		return response;
    }
}
