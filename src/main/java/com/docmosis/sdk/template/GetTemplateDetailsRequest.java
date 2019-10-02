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
import com.docmosis.sdk.handlers.DocmosisException;

/**
 * The object holds the instructions and data for a request to the Get Template Details service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Get Template Details request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetTemplateDetailsResponse templateDetails = Template
 *                                                 .getDetails()
 *                                                 .templateName("MasterTemplates/MyMasterTemplate.docx")
 *                                                 .execute();
 *   if (templateDetails.hasSucceeded()) {
 *       templateDetails.toString();
 *   }
 * </pre>
 */
public class GetTemplateDetailsRequest extends AbstractTemplateRequest<GetTemplateDetailsRequest> {

	private static final String SERVICE_PATH = "getTemplateDetails";

	public GetTemplateDetailsRequest() {
		super(SERVICE_PATH);
	}
	
	public GetTemplateDetailsRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	@Override
	public GetTemplateDetailsResponse execute() throws DocmosisException {
		return Template.executeGetTemplateDetails(getThis());
	}
	
	@Override
	public GetTemplateDetailsResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeGetTemplateDetails(getThis());
	}
	
	@Override
	public GetTemplateDetailsResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeGetTemplateDetails(getThis());
	}

	@Override
	public GetTemplateDetailsResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Template.executeGetTemplateDetails(getThis());
	}
	
	@Override
	protected GetTemplateDetailsRequest getThis()
	{
		return this;
	}

	@Override
	public String toString() {
		return "GetTemplateDetailsRequest [" + super.toString() + "]";
	}
}
