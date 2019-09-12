/*
 *   Copyright 2012 Docmosis.com or its affiliates.  All Rights Reserved.
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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudFileRequest;

/**
 * The object holds the instructions and data for a request to the Convert service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Convert request.
 * 
 * Typically, you would use the Converter class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   ConverterResponse response = Converter
 *									.convert()
 *									.fileToConvert(convertFile)
 *									.outputName(outputFileName)
 *									.sendTo(outputFileOrStream)
 *									.execute();
 *   if (response.hasSucceeded()) {
 *		//File converted and saved to outputFileOrStream
 *	 }
 *   ...
 * </pre>
 */
public class ConverterRequest extends DocmosisCloudFileRequest<ConverterRequest> {
    
	private static final String SERVICE_PATH = "convert";
	private File fileToConvert;
    private String outputName;
    
    public ConverterRequest() {
    	super(SERVICE_PATH);
    }
    
    /**
     * Construct a new request with the specified environment.
     * 
     * @param Environment environment object specifying the Cloud/Tornado service url and accesskey
     */
    public ConverterRequest(final Environment environment)
    {
    	super(SERVICE_PATH, environment);
    }    

    public String getOutputName() {
        return outputName;
    }

    /**
     * Set the name to give the output document.  The format of the resulting document 
     * is derived from the extension of this name. For example "resume1.pdf" implies a PDF format document. 
     * 
     * @param outputName the name of the result
     */
    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }
    
    /**
     * Set the name to give the output document.  The format of the resulting document 
     * is derived from the extension of this name. For example "resume1.pdf" implies a PDF format document. 
     * 
     * @param outputName the name of the result
     */
    public ConverterRequest outputName(String outputName) {
        this.outputName = outputName;
        return getThis();
    }

    
    public File getFileToConvert()
	{
		return fileToConvert;
	}

	public void setFileToConvert(File fileToConvert)
	{
		this.fileToConvert = fileToConvert;
	}
	
	/**
	 * Specify the local file to convert.  This file will be sent to the cloud and converted.
	 * 
	 * @param fileToConvert the file
	 * @return this request
	 */
	public ConverterRequest fileToConvert(File fileToConvert)
	{
		this.fileToConvert = fileToConvert;
		return getThis();
	}

	/**
	 * Execute a convert based on contained settings.
     * <p>
	 * @return a response object giving status, possible error messages and optional
	 * document payload.
	 * 
	 * @throws ConverterException if a problem occurs invoking the service 
	 */
	@Override
	public ConverterResponse execute() throws ConverterException
	{
		return Converter.executeConvert(getThis());
	}

	@Override
	public ConverterResponse execute(String url, String accessKey) throws ConverterException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Converter.executeConvert(getThis());
	}
	
	@Override
	public ConverterResponse execute(String accessKey) throws ConverterException {
		getEnvironment().setAccessKey(accessKey);
		return Converter.executeConvert(getThis());
	}
	
	@Override
	public ConverterResponse execute(Environment environment) throws ConverterException {
		super.setEnvironment(environment);
		return Converter.executeConvert(getThis());
	}

	@Override
	protected ConverterRequest getThis()
	{
		return this;
	}

	@Override
	public String toString()
	{
		return "ConverterRequest [fileToConvert="
				+ fileToConvert + ", outputName=" + outputName + ", " + super.toString() + "]";
	}
}
