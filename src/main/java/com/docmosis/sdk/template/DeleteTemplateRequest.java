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

import java.util.ArrayList;
import java.util.List;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Delete Template service.
 * See the Web Services Developer guide at @see <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Delete Template request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteTemplateResponse deleteTemplate = Template
 *   										.delete()
 *											.addTemplateName(fileToDelete)
 *											.execute();
 *  if (deleteTemplate.hasSucceeded()) {
 *    //Succeeded
 *  }
 * </pre>
 */
public class DeleteTemplateRequest extends DocmosisCloudRequest<DeleteTemplateRequest> {
	
	private static final String SERVICE_PATH = "deleteTemplate";
	private boolean isSystemTemplate = false;
	private List<String> templateNames = null;

	public DeleteTemplateRequest() {
		super(SERVICE_PATH);
		templateNames = new ArrayList<String>();
	}
	
	public DeleteTemplateRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
		templateNames = new ArrayList<String>();
	}
	
	/**
	 * If set to "true", templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @return isSystemTemplate flag
	 */
	public boolean getIsSystemTemplate() {
		return isSystemTemplate;
	}

	/**
	 * If set to "true", templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 */
	public void setSystemTemplate(boolean isSystemTemplate) {
		this.isSystemTemplate = isSystemTemplate;
	}
	
	/**
	 * If set to "true", templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 * @return this request for method chaining
	 */
	public DeleteTemplateRequest isSystemTemplate(boolean isSystemTemplate) {
		this.isSystemTemplate = isSystemTemplate;
		return this;
	}
	
	/**
	 * Get the currently set templateNames.
	 * 
	 * @return List of the name of the Templates on the docmosis server.
	 */
	public List<String> getTemplateNames() {
		return templateNames;
	}
	
	/**
	 * Set the Template Names.
	 * 
	 * @param templateNames The name(s) of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateNames(List<String> templateNames) {
		this.templateNames = templateNames;
	}
	
	/**
	 * Set the Template Names.
	 * 
	 * @param templateNames The name(s) of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public DeleteTemplateRequest templateNames(List<String> templateNames) {
		this.templateNames = templateNames;
		return this;
	}

	/**
	 * Add a Template Name.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public DeleteTemplateRequest addTemplateName(String templateName) {
		this.templateNames.add(templateName);
		return this;
	}

	@Override
	public DeleteTemplateResponse execute() throws DocmosisException {
		return Template.executeDeleteTemplate(this);
	}
	
	@Override
	public DeleteTemplateResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeDeleteTemplate(this);
	}
	
	@Override
	public DeleteTemplateResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeDeleteTemplate(this);
	}
	
	@Override
	public DeleteTemplateResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Template.executeDeleteTemplate(this);
	}
	
//	@Override
//	protected DeleteTemplateRequest getThis()
//	{
//		return this;
//	}
	
	@Override
	public String toString() {
		String names = "(";
		if (templateNames != null) {
			for (String tn: templateNames) {
				names += tn + "; ";
			}
			names = names.substring(0, names.length()-2) + ") ";
		}
		return "DeleteTemplateRequest [isSystemTemplate=" + isSystemTemplate + ", templateNames=" + names + ", " + super.toString() + "]";
	}
	
}
