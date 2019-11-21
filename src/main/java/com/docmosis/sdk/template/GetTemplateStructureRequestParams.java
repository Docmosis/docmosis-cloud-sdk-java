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
 * The object holds the parameters for a request to the Get Template Structure service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Get Template Structure request.
 *
 */
public class GetTemplateStructureRequestParams extends RequestParameters {

	private static final String TEMPLATE_NAME		= "templateName";
	
	private static final String[] REQUIRED_PARAMS	= {TEMPLATE_NAME};

	public GetTemplateStructureRequestParams() {
		super(REQUIRED_PARAMS);
	}
	
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
}
