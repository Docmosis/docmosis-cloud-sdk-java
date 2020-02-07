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

import java.io.File;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Batch Upload Template service.
 * See the Web Services Developer guide at <a href="https://resources.docmosis.com/">https://resources.docmosis.com/</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Template request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   UploadTemplateBatchResponse response = Template
 *                                       	.uploadBatch()
 *                                       	.templateZip(uploadFiles)
 *                                       	.execute();
 *   if (response.hasSucceeded()) {
 *       response
 *       .statusRequest()
 *       .execute();
 *   }
 * </pre>
 */
public class UploadTemplateBatchRequest extends DocmosisCloudRequest<UploadTemplateBatchRequest> {
	
	private static final String SERVICE_PATH = "uploadTemplateBatch";
	
	private UploadTemplateBatchRequestParams params = new UploadTemplateBatchRequestParams();

	public UploadTemplateBatchRequest() {
		super(SERVICE_PATH);
	}
	
	public UploadTemplateBatchRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	public UploadTemplateBatchRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the Template Zip File to be uploaded.
	 * @param templateZip File object of the templates to be uploaded.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest templateZip(File templateZip) {
		params.setTemplateZip(templateZip);
		return this;
	}

	/**
	 * Set the userJobId. This id can be used to check the job status and to cancel the job.
	 * @param userJobId The id to use for the template upload job.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest userJobId(String userJobId) {
		params.setUserJobId(userJobId);
		return this;
	}

	/**
	 * Set the path to store the templates to. If the path doesn't exist it will be created.
	 * @param intoFolder The path to the folder to upload the templates to.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest intoFolder(String intoFolder) {
		params.setIntoFolder(intoFolder);
		return this;
	}

	/**
	 * If set to true the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to true.
	 * @param devMode true if you want Docmosis to handle errors and report them in the rendered document.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest devMode(boolean devMode) {
		params.setDevMode(devMode);
		return this;
	}

	/**
	 * If set to true the previous template of the same name will be left in place if the uploaded template has errors. If not specified 
	 * (or false) the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to false.
	 * @param keepPrevOnFail value of keep previous on fail.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest keepPrevOnFail(boolean keepPrevOnFail) {
		params.setKeepPrevOnFail(keepPrevOnFail);
		return this;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest fieldDelimPrefix(String fieldDelimPrefix) {
		params.setFieldDelimPrefix(fieldDelimPrefix);
		return this;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest fieldDelimSuffix(String fieldDelimSuffix) {
		params.setFieldDelimSuffix(fieldDelimSuffix);
		return this;
	}

	/**
	 * If set to true the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @param normalizeTemplateName value of normalize template.
	 * @return this request for method chaining
	 */
	public UploadTemplateBatchRequest normalizeTemplateName(boolean normalizeTemplateName) {
		params.setNormalizeTemplateName(normalizeTemplateName);
		return this;
	}

	/**
	 * Execute an upload template batch request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchResponse execute() throws TemplateException {
		return Template.executeUploadTemplateBatch(this);
	}

	/**
	 * Execute an upload template batch request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchResponse execute(String url, String accessKey) throws TemplateException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeUploadTemplateBatch(this);
	}

	/**
	 * Execute an upload template batch request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchResponse execute(String accessKey) throws TemplateException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeUploadTemplateBatch(this);
	}

	/**
	 * Execute an upload template batch request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public UploadTemplateBatchResponse execute(Environment environment) throws TemplateException {
		super.setEnvironment(environment);
		return Template.executeUploadTemplateBatch(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
