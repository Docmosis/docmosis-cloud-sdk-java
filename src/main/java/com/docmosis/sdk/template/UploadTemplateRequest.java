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

import com.docmosis.sdk.handlers.DocmosisException;

/**
 * The object holds the instructions and data for a request to the Upload Template service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   UploadTemplateResponse uploadedTemplate = Template.upload()
 *											.templateFile(uploadFile)
 *											.execute(URL, ACCESS_KEY);
 *   uploadedTemplate.toString();
 * </pre>
 */
public class UploadTemplateRequest extends AbstractTemplateNameRequest<UploadTemplateRequest> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	private File templateFile = null;
	private String templateDescription = "";
	private boolean devMode = true;
	private boolean keepPrevOnFail = false;
	private String fieldDelimPrefix = "<<";
	private String fieldDelimSuffix = ">>";
	private boolean normalizeTemplateName = false;

	public UploadTemplateRequest() {
		super(UploadTemplateRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs/uploadTemplate"); //Default url
	}
	
	public UploadTemplateRequest(String url) {
		super(UploadTemplateRequest.class, url);
	}

	public UploadTemplateRequest(String url, String accessKey) {
		super(UploadTemplateRequest.class, url, accessKey);
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
	 */
	public UploadTemplateRequest templateFile(File templateFile) {
		this.templateFile = templateFile;
		return self;
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
	 */
	public UploadTemplateRequest templateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
		return self;
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
	 */
	public UploadTemplateRequest devMode(boolean devMode) {
		this.devMode = devMode;
		return self;
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
	 */
	public UploadTemplateRequest keepPrevOnFail(boolean keepPrevOnFail) {
		this.keepPrevOnFail = keepPrevOnFail;
		return self;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "<<".
	 * @return Value of prefix delimiter.
	 */
	public String getFieldDelimPrefix() {
		return fieldDelimPrefix;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "<<".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 */
	public void setFieldDelimPrefix(String fieldDelimPrefix) {
		this.fieldDelimPrefix = fieldDelimPrefix;
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "<<".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 */
	public UploadTemplateRequest fieldDelimPrefix(String fieldDelimPrefix) {
		this.fieldDelimPrefix = fieldDelimPrefix;
		return self;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is ">>".
	 * @return Value of suffix delimiter.
	 */
	public String getFieldDelimSuffix() {
		return fieldDelimSuffix;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is ">>".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 */
	public void setFieldDelimSuffix(String fieldDelimSuffix) {
		this.fieldDelimSuffix = fieldDelimSuffix;
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is ">>".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 */
	public UploadTemplateRequest fieldDelimSuffix(String fieldDelimSuffix) {
		this.fieldDelimSuffix = fieldDelimSuffix;
		return self;
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
	 */
	public UploadTemplateRequest normalizeTemplateName(boolean normalizeTemplateName) {
		this.normalizeTemplateName = normalizeTemplateName;
		return self;
	}

	@Override
	public String toString() {
		return "UploadTemplateRequest [" + super.toString() + ", templateFile=" + templateFile + ", templateDescription=" + templateDescription
				+ ", devMode=" + devMode + ", keepPrevOnFail=" + keepPrevOnFail + ", fieldDelimPrefix="
				+ fieldDelimPrefix + ", fieldDelimSuffix=" + fieldDelimSuffix + ", normalizeTemplateName="
				+ normalizeTemplateName + "]";
	}

	@Override
	public UploadTemplateResponse execute() throws DocmosisException {
		return Template.executeUploadTemplate(self);
	}
	
	@Override
	public UploadTemplateResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Template.executeUploadTemplate(self);
	}
	
	@Override
	public UploadTemplateResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return Template.executeUploadTemplate(self);
	}
}
