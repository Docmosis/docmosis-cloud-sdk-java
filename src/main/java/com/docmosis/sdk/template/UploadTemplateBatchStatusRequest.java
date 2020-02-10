/*
 *   Copyright 2020 Docmosis.com or its affiliates.  All Rights Reserved.
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
 * The object holds the instructions and data for a request to the Batch Upload Template Status service.
 * See the Web Services Developer guide at <a href="https://resources.docmosis.com/">https://resources.docmosis.com/</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Batch Upload Template Status request.
 * 
 * Typically, you would use the UploadTemplateBatchResponse object to get an instance of this class and execute:
 * 
 * 
 * <pre>
 *   UploadTemplateBatchResponse response = Template
 *                                       	.uploadBatch()
 *                                       	.templateZip(uploadFiles)
 *                                       	.execute();
 *   if (response.hasSucceeded()) {
 *       UploadTemplateBatchStatusResponse responseStatus = response
 *       													.statusRequest()
 *       													.execute();
 *   }
 * </pre>
 */
public class UploadTemplateBatchStatusRequest extends DocmosisCloudRequest<UploadTemplateBatchStatusRequest> {
	
	private static final String SERVICE_PATH = "uploadTemplateBatchStatus";
	
	private UploadTemplateBatchStatusRequestParams params = new UploadTemplateBatchStatusRequestParams();

	public UploadTemplateBatchStatusRequest() {
		super(SERVICE_PATH);
	}
	
	public UploadTemplateBatchStatusRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	public UploadTemplateBatchStatusRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the userJobId. This id can be used to check the job status and to cancel the job.
	 * @param userJobId The id to use for the template upload job.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchStatusRequest userJobId(String userJobId) {
		params.setUserJobId(userJobId);
		return this;
	}

	/**
	 * Execute an upload template batch status request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchStatusResponse execute() throws TemplateException {
		return Template.executeUploadTemplateBatchStatus(this);
	}

	/**
	 * Execute an upload template batch status request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchStatusResponse execute(String url, String accessKey) throws TemplateException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeUploadTemplateBatchStatus(this);
	}

	/**
	 * Execute an upload template batch status request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchStatusResponse execute(String accessKey) throws TemplateException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeUploadTemplateBatchStatus(this);
	}

	/**
	 * Execute an upload template batch status request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchStatusResponse execute(Environment environment) throws TemplateException {
		super.setEnvironment(environment);
		return Template.executeUploadTemplateBatchStatus(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
