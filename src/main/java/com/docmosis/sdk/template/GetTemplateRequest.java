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
package com.docmosis.sdk.template;

import java.util.List;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudFileRequest;

/**
 * The object holds the instructions and data for a request to the Get Template service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Get Template request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetTemplateResponse getTemplate = Template
 *                                      .get()
 *                                      .templateName(fileToGet)
 *                                      .sendTo(outputFileOrStream)
 *                                      .execute();
 *  if (getTemplate.hasSucceeded()) {
 *      //File saved to outputFileOrStream
 *  }
 * </pre>
 */
public class GetTemplateRequest extends DocmosisCloudFileRequest<GetTemplateRequest> {
	
	private static final String SERVICE_PATH = "getTemplate";
	
	private GetTemplateRequestParams params = new GetTemplateRequestParams();

	public GetTemplateRequest() {
		super(SERVICE_PATH);
	}
	
	public GetTemplateRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public GetTemplateRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the names of the Templates to get.
	 * If getting more than one template the templates will be returned in a .zip file.
	 * 
	 * @param templateNames The name(s) of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public GetTemplateRequest templateNames(List<String> templateNames) {
		params.setTemplateNames(templateNames);
		return this;
	}
	
	/**
	 * Add the name of a Template to get.
	 * If getting more than one template the templates will be returned in a .zip file.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public GetTemplateRequest templateName(String templateName) {
		params.setTemplateName(templateName);
		return this;
	}
	
	/**
	 * If set to true, templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 * @return this request for method chaining
	 */
	public GetTemplateRequest isSystemTemplate(boolean isSystemTemplate) {
		params.setIsSystemTemplate(isSystemTemplate);
		return this;
	}

	/**
	 * Execute a get template request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetTemplateResponse execute() throws TemplateException {
		return Template.executeGetTemplate(this);
	}

	/**
	 * Execute a get template request based on contained settings.
	 *  
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetTemplateResponse execute(String url, String accessKey) throws TemplateException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeGetTemplate(this);
	}

	/**
	 * Execute a get template request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetTemplateResponse execute(String accessKey) throws TemplateException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeGetTemplate(this);
	}

	/**
	 * Execute a get template request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetTemplateResponse execute(Environment environment) throws TemplateException {
		super.setEnvironment(environment);
		return Template.executeGetTemplate(this);
	}
	
	@Override
	protected GetTemplateRequest getThis()
	{
		return this;
	}
	
	@Override
	public String toString() {
		return params.toString();
	}
}
