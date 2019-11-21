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
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Delete Template service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Delete Template request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteTemplateResponse response = Template
 *                                       .delete()
 *                                       .templateName(fileToDelete)
 *                                       .execute();
 *  if (response.hasSucceeded()) {
 *      //Succeeded
 *  }
 * </pre>
 */
public class DeleteTemplateRequest extends DocmosisCloudRequest<DeleteTemplateRequest> {
	
	private static final String SERVICE_PATH = "deleteTemplate";

	private DeleteTemplateRequestParams params = new DeleteTemplateRequestParams();

	public DeleteTemplateRequest() {
		super(SERVICE_PATH);
	}
	
	public DeleteTemplateRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public DeleteTemplateRequestParams getParams()
    {
    	return params;
    }
	
	/**
	 * Set the list of Template Names to be deleted.
	 * 
	 * @param templateNames The name(s) of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public DeleteTemplateRequest templateNames(List<String> templateNames) {
		params.setTemplateNames(templateNames);
		return this;
	}

	/**
	 * Add a Template Name to be deleted.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public DeleteTemplateRequest templateName(String templateName) {
		params.setTemplateName(templateName);
		return this;
	}

	/**
	 * Execute a delete template request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteTemplateResponse execute() throws TemplateException {
		return Template.executeDeleteTemplate(this);
	}

	/**
	 * Execute a delete template request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteTemplateResponse execute(String url, String accessKey) throws TemplateException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeDeleteTemplate(this);
	}

	/**
	 * Execute a delete template request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteTemplateResponse execute(String accessKey) throws TemplateException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeDeleteTemplate(this);
	}

	/**
	 * Execute a delete template request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteTemplateResponse execute(Environment environment) throws TemplateException {
		super.setEnvironment(environment);
		return Template.executeDeleteTemplate(this);
	}
	
	@Override
	public String toString() {
		return params.toString();
	}
}
