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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * Abstract class for template requests. contains 2 common parameters.
 * @param <T> Request class
 */
public abstract class AbstractTemplateRequest<T> extends DocmosisCloudRequest<T> {
	
	private boolean isSystemTemplate = false;
	private String templateName;

	public AbstractTemplateRequest(final String servicePath) {
		super(servicePath);
	}
	
	public AbstractTemplateRequest(final String servicePath, final Environment environment) {
		super(servicePath, environment);
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
	public void isSystemTemplate(boolean isSystemTemplate) {
		this.isSystemTemplate = isSystemTemplate;
	}
	
	/**
	 * Get the currently set templateName.
	 * 
	 * @return The name of the Template on the Docmosis server.
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the Docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}


	@Override
	public String toString() {
		return "isSystemTemplate=" + isSystemTemplate + ", templateName=" + templateName + ", " + super.toString();
	}
}
