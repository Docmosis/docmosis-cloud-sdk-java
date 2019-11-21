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

import java.io.File;

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Convert service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Convert request.
 *
 */
public class ConverterRequestParams extends RequestParameters {
	
	private static final String FILE 				= "file";
	private static final String OUTPUT_NAME			= "outputName";
	
	private static final String[] REQUIRED_PARAMS	= {FILE, OUTPUT_NAME};

	public ConverterRequestParams() {
		super(REQUIRED_PARAMS);
	}
	
    public File getFileToConvert()
	{
    	return getFileParam(FILE);
	}

	/**
	 * Specify the local file to convert.  This file will be sent to the cloud and converted.
	 * 
	 * @param fileToConvert the file
	 */
	public void setFileToConvert(File fileToConvert)
	{
		super.setParam(FILE, fileToConvert);
	}

	public String getOutputName()
    {
    	return getStringParam(OUTPUT_NAME);
    }

    /**
     * Set the name to give the output document.  The format of the resulting document 
     * is derived from the extension of this name. For example "resume1.pdf" implies a PDF format document. 
     * 
     * @param outputName the name of the result
     */
    public void setOutputName(String outputName)
    {
    	super.setParam(OUTPUT_NAME, outputName);
    }
}
