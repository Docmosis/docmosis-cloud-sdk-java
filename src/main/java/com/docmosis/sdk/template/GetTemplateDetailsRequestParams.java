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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Get Template Details service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Get Template Details request.
 *
 */
public class GetTemplateDetailsRequestParams extends RequestParameters {

	private static final String TEMPLATE_NAME		= "templateName";
	private static final String IS_SYSTEM_TEMPLATE	= "isSystemTemplate";

	/**
	 * Get the currently set templateName.
	 * 
	 * @return The name of the Template on the docmosis server.
	 */
	public String getTemplateName() {
		return getStringParam(TEMPLATE_NAME);
	}
	
	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateName(String templateName) {
		super.setParam(TEMPLATE_NAME, templateName);
	}
	
	/**
	 * If set to true, templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @return isSystemTemplate flag
	 */
	public Boolean getIsSystemTemplate() {
		return getBooleanParam(IS_SYSTEM_TEMPLATE);
	}

	/**
	 * If set to true, templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 */
	public void setIsSystemTemplate(boolean isSystemTemplate) {
		super.setParam(IS_SYSTEM_TEMPLATE, isSystemTemplate);
	}
}
