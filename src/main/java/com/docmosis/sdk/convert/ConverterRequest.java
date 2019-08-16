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
import java.io.IOException;
import java.io.Serializable;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.image.Image;
import com.docmosis.sdk.image.ListImagesRequest;
import com.docmosis.sdk.image.ListImagesResponse;
import com.docmosis.sdk.render.RenderResponse;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for the convert request.
 * 
 * 
 * <pre>
 *   RenderRequest request = RenderRequestFactory.getRequest(accessKey);
 *   
 *   request.setTemplateName("samples/WelcomeTemplate.doc");
 *   request.setOutputName("result.pdf");
 *   
 *   // stream is the default - which means send the result back to the caller
 *   request.setstoreTo("stream");
 *   
 *   RenderResponse response = request.execute();
 *   
 *   // process response
 *   ...
 * </pre>
 */
//TODO Update Java Doc Above
public class ConverterRequest extends DocmosisCloudRequest<ConverterRequest> {

    private static final long serialVersionUID = 4338222626030786768L;
    
    // settings that change more frequently.
    private File fileToConvert;
    private String outputName;
    
    public ConverterRequest() {
    	super(ConverterRequest.class);
    	setUrl("https://dws2.docmosis.com/services/rs/convert"); //Default url
    }
    
    /**
     * Construct a new request with the specified end point url.
     * 
     * @param converterUrl the url to the converter service.
     */
    public ConverterRequest(String converterUrl)
    {
    	super(ConverterRequest.class, converterUrl);
    }
    
    /**
     * Construct a new request with the specified end point url and access key.
     * 
     * @param converterUrl the url to the converter service.
     * @param accessKey the access key to use for the render
     */
    public ConverterRequest(String converterUrl, String accessKey)
    {
    	super(ConverterRequest.class, converterUrl, accessKey);
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
        return self;
    }

    
    public File getFileToConvert()
	{
		return fileToConvert;
	}

	public void setFileToConvert(File fileToConvert)
	{
		this.fileToConvert = fileToConvert;
	}
	
	public ConverterRequest fileToConvert(File fileToConvert)
	{
		this.fileToConvert = fileToConvert;
		return self;
	}

	/**
	 * Execute a convert based on contained settings.
     * <p>
     * NOTE: call {@link RenderResponse#cleanup()} on the response returned
     *       to cleanup resources  
	 * 
	 * @return a response object giving status, possible error messages and optional
	 * document payload.
	 * 
	 * @throws ConverterException if a problem occurs invoking the service 
	 */
	@Override
	public ConverterResponse execute() throws ConverterException
	{
		return Converter.executeConvert(self);
	}

	@Override
	public ConverterResponse execute(String url, String accessKey) throws ConverterException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Converter.executeConvert(self);
	}
	
	@Override
	public ConverterResponse execute(String accessKey) throws ConverterException {
		self.setAccessKey(accessKey);
		return Converter.executeConvert(self);
	}

	@Override
	public String toString()
	{
		return "ConverterRequest [" + super.toString() + ", fileToConvert="
				+ fileToConvert + ", outputName=" + outputName + "]";
	}
}
