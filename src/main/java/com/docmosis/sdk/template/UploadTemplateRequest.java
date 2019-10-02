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
public class UploadTemplateRequest extends AbstractTemplateRequest<UploadTemplateRequest> {
	
	private static final String SERVICE_PATH = "uploadTemplate";
	private File templateFile = null;
	private String templateDescription = "";
	private boolean devMode = true;
	private boolean keepPrevOnFail = false;
	private String fieldDelimPrefix = "<<";
	private String fieldDelimSuffix = ">>";
	private boolean normalizeTemplateName = false;

	public UploadTemplateRequest() {
		super(SERVICE_PATH);
	}
	
	public UploadTemplateRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	/**
	 * Get currently set Template File to be uploaded.
	 * @return File object of the template to be uploaded.
	 */
	public File getTemplateFile() {
		return templateFile;
	}

	/**
	 * Set the Template File to be uploaded.
	 * @param templateFile File object of the template to be uploaded.
	 */
	public void setTemplateFile(File templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * Set the Template File to be uploaded.
	 * @param templateFile File object of the template to be uploaded.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest templateFile(File templateFile) {
		this.templateFile = templateFile;
		return getThis();
	}

	/**
	 * Get current description of the template.
	 * @return Template Description.
	 */
	public String getTemplateDescription() {
		return templateDescription;
	}

	/**
	 * Set the optional template description parameter.
	 * @param templateDescription description of the template.
	 */
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	/**
	 * Set the optional template description parameter.
	 * @param templateDescription description of the template.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest templateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
		return getThis();
	}

	/**
	 * If set to "y", "yes" or "true" the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to "true".
	 * @return value of development mode
	 */
	public boolean getDevMode() {
		return devMode;
	}

	/**
	 * If set to "y", "yes" or "true" the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to "true".
	 * @param devMode true if you want Docmosis to handle errors and report them in the rendered document.
	 */
	public void setDevMode(boolean devMode) {
		this.devMode = devMode;
	}
	
	/**
	 * If set to "y", "yes" or "true" the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to "true".
	 * @param devMode true if you want Docmosis to handle errors and report them in the rendered document.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest devMode(boolean devMode) {
		this.devMode = devMode;
		return getThis();
	}

	/**
	 * If set to "y", "yes" or "true" the previous template of the same name will be left in place if the uploaded template has errors. If not specified (or "n", "no" or "false") the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to "false".
	 * @return Value of keep previous on fail.
	 */
	public boolean getKeepPrevOnFail() {
		return keepPrevOnFail;
	}

	/**
	 * If set to "y", "yes" or "true" the previous template of the same name will be left in place if the uploaded template has errors. If not specified (or "n", "no" or "false") the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to "false".
	 * @param keepPrevOnFail value of keep previous on fail.
	 */
	public void setKeepPrevOnFail(boolean keepPrevOnFail) {
		this.keepPrevOnFail = keepPrevOnFail;
	}

	/**
	 * If set to "y", "yes" or "true" the previous template of the same name will be left in place if the uploaded template has errors. If not specified (or "n", "no" or "false") the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to "false".
	 * @param keepPrevOnFail value of keep previous on fail.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest keepPrevOnFail(boolean keepPrevOnFail) {
		this.keepPrevOnFail = keepPrevOnFail;
		return getThis();
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @return Value of prefix delimiter.
	 */
	public String getFieldDelimPrefix() {
		return fieldDelimPrefix;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 */
	public void setFieldDelimPrefix(String fieldDelimPrefix) {
		this.fieldDelimPrefix = fieldDelimPrefix;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest fieldDelimPrefix(String fieldDelimPrefix) {
		this.fieldDelimPrefix = fieldDelimPrefix;
		return getThis();
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @return Value of suffix delimiter.
	 */
	public String getFieldDelimSuffix() {
		return fieldDelimSuffix;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 */
	public void setFieldDelimSuffix(String fieldDelimSuffix) {
		this.fieldDelimSuffix = fieldDelimSuffix;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest fieldDelimSuffix(String fieldDelimSuffix) {
		this.fieldDelimSuffix = fieldDelimSuffix;
		return getThis();
	}

	/**
	 * If set to "y", "yes" or "true" the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @return Value of normalize template.
	 */
	public boolean getNormalizeTemplateName() {
		return normalizeTemplateName;
	}

	/**
	 * If set to "y", "yes" or "true" the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @param normalizeTemplateName value of normalize template.
	 */
	public void setNormalizeTemplateName(boolean normalizeTemplateName) {
		this.normalizeTemplateName = normalizeTemplateName;
	}

	/**
	 * If set to "y", "yes" or "true" the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @param normalizeTemplateName value of normalize template.
	 * @return this request for method chaining
	 */
	public UploadTemplateRequest normalizeTemplateName(boolean normalizeTemplateName) {
		this.normalizeTemplateName = normalizeTemplateName;
		return getThis();
	}

	@Override
	public UploadTemplateResponse execute() throws DocmosisException {
		return Template.executeUploadTemplate(getThis());
	}
	
	@Override
	public UploadTemplateResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeUploadTemplate(getThis());
	}
	
	@Override
	public UploadTemplateResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeUploadTemplate(getThis());
	}
	
	@Override
	public UploadTemplateResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Template.executeUploadTemplate(getThis());
	}

	@Override
	protected UploadTemplateRequest getThis()
	{
		return this;
	}

	@Override
	public String toString() {
		return "UploadTemplateRequest [templateFile=" + templateFile + ", templateDescription=" + templateDescription
				+ ", devMode=" + devMode + ", keepPrevOnFail=" + keepPrevOnFail + ", fieldDelimPrefix="
				+ fieldDelimPrefix + ", fieldDelimSuffix=" + fieldDelimSuffix + ", normalizeTemplateName="
				+ normalizeTemplateName + ", " + super.toString() + "]";
	}
}
