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

import java.io.File;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Upload Template service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Template request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   UploadTemplateResponse uploadedTemplate = Template
 *                                              .upload()
 *                                              .templateFile(uploadFile)
 *                                              .execute();
 *   if (uploadedTemplate.hasSucceeded()) {
 *       uploadedTemplate.toString();
 *   }
 * </pre>
 */
public class UploadTemplateRequest extends DocmosisCloudRequest<UploadTemplateRequest> {
	
	private static final String SERVICE_PATH = "uploadTemplate";
	
	private UploadTemplateRequestParams params = new UploadTemplateRequestParams();

	public UploadTemplateRequest() {
		super(SERVICE_PATH);
	}
	
	public UploadTemplateRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	public UploadTemplateRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the Template File to be uploaded.
	 * @param templateFile File object of the template to be uploaded.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest templateFile(File templateFile) {
		params.setTemplateFile(templateFile);
		return this;
	}

	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the Docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest templateName(String templateName) {
		params.setTemplateName(templateName);
		return this;
	}

	/**
	 * Set the optional template description parameter.
	 * @param templateDescription description of the template.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest templateDescription(String templateDescription) {
		params.setTemplateDescription(templateDescription);
		return this;
	}
	
	/**
	 * If set to true, templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest isSystemTemplate(boolean isSystemTemplate) {
		params.setIsSystemTemplate(isSystemTemplate);
		return this;
	}

	/**
	 * If set to true the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to true.
	 * @param devMode true if you want Docmosis to handle errors and report them in the rendered document.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest devMode(boolean devMode) {
		params.setDevMode(devMode);
		return this;
	}

	/**
	 * If set to true the previous template of the same name will be left in place if the uploaded template has errors. If not specified (or "n", "no" or "false") the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to false.
	 * @param keepPrevOnFail value of keep previous on fail.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest keepPrevOnFail(boolean keepPrevOnFail) {
		params.setKeepPrevOnFail(keepPrevOnFail);
		return this;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest fieldDelimPrefix(String fieldDelimPrefix) {
		params.setFieldDelimPrefix(fieldDelimPrefix);
		return this;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest fieldDelimSuffix(String fieldDelimSuffix) {
		params.setFieldDelimSuffix(fieldDelimSuffix);
		return this;
	}

	/**
	 * If set to true the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @param normalizeTemplateName value of normalize template.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest normalizeTemplateName(boolean normalizeTemplateName) {
		params.setNormalizeTemplateName(normalizeTemplateName);
		return this;
	}

	@Override
	public UploadTemplateResponse execute() throws DocmosisException {
		return Template.executeUploadTemplate(this);
	}
	
	@Override
	public UploadTemplateResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeUploadTemplate(this);
	}
	
	@Override
	public UploadTemplateResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeUploadTemplate(this);
	}
	
	@Override
	public UploadTemplateResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Template.executeUploadTemplate(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
