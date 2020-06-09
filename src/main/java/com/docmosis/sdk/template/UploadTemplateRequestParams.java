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
import java.io.InputStream;

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Upload Template service.
 * See the Web Services Developer guide at <a href="https://resources.docmosis.com/">https://resources.docmosis.com/</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Template request.
 * 
 */
public class UploadTemplateRequestParams extends RequestParameters {

	private static final String TEMPLATE_FILE			= "templateFile";
	private static final String TEMPLATE_NAME			= "templateName";
	private static final String TEMPLATE_DESCRIPTION	= "templateDescription";
	private static final String DEV_MODE				= "devMode";
	private static final String KEEP_PREV_ON_FAIL		= "keepPrevOnFail";
	private static final String FIELD_DELIM_PREFIX		= "fieldDelimPrefix";
	private static final String FIELD_DELIM_SUFFIX		= "fieldDelimSuffix";
	private static final String NORMALIZE_TEMPLATE_NAME	= "normalizeTemplateName";
	
	private static final String[] REQUIRED_PARAMS		= {TEMPLATE_FILE};

    public UploadTemplateRequestParams() {
    	super(REQUIRED_PARAMS);
    }

	/**
	 * Get currently set Template File to be uploaded.
	 * @return File object of the template to be uploaded.
	 */
	public File getTemplateFile() {
		return getFileParam(TEMPLATE_FILE);
	}

	/**
	 * Set the Template File to be uploaded.
	 * @param templateFile File object of the template to be uploaded.
	 */
	public void setTemplateFile(File templateFile) {
		super.setParam(TEMPLATE_FILE, templateFile);
	}
	
	/**
	 * Set the Template input stream to be uploaded.
	 * @param templateFile InputStream object of the template to be uploaded.
	 */
	public void setTemplateFile(InputStream templateStream) {
		super.setParam(TEMPLATE_FILE, templateStream);
	}
	
	/**
	 * Set the Template byte array to be uploaded.
	 * @param templateFile byte[] object of the template to be uploaded.
	 */
	public void setTemplateFile(byte[] templateBytes) {
		super.setParam(TEMPLATE_FILE, templateBytes);
	}

	/**
	 * Get the currently set templateName.
	 * 
	 * @return The name of the Template on the Docmosis server.
	 */
	public String getTemplateName() {
		return getStringParam(TEMPLATE_NAME);
	}

	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the Docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateName(String templateName) {
		super.setParam(TEMPLATE_NAME, templateName);
	}

	/**
	 * Get current description of the template.
	 * @return Template Description.
	 */
	public String getTemplateDescription() {
		return getStringParam(TEMPLATE_DESCRIPTION);
	}

	/**
	 * Set the optional template description parameter.
	 * @param templateDescription description of the template.
	 */
	public void setTemplateDescription(String templateDescription) {
		super.setParam(TEMPLATE_DESCRIPTION, templateDescription);
	}

	/**
	 * If set to true the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to true.
	 * @return value of development mode
	 */
	public Boolean getDevMode() {
		return getBooleanParam(DEV_MODE);
	}

	/**
	 * If set to true the upload is run in developer mode - meaning that Docmosis will do it's best to handle errors and report them within a rendered document to ease development.
	 * Defaults to true.
	 * @param devMode true if you want Docmosis to handle errors and report them in the rendered document.
	 */
	public void setDevMode(boolean devMode) {
		super.setParam(DEV_MODE, devMode);
	}
	
	/**
	 * If set to true the previous template of the same name will be left in place if the uploaded template has errors. If not specified 
	 * (or false) the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to false.
	 * @return Value of keep previous on fail.
	 */
	public Boolean getKeepPrevOnFail() {
		return getBooleanParam(KEEP_PREV_ON_FAIL);
	}

	/**
	 * If set to true the previous template of the same name will be left in place if the uploaded template has errors. If not specified 
	 * (or false) the original template is always removed, even if this uploaded template has errors.
	 * This only has effect when devMode is disabled (since devMode is intended to allow templates with errors to be displayed by Docmosis).
	 * This parameter means that in production mode (non-developer mode) template uploads will not replace a working template with a bad template.
	 * Defaults to false.
	 * @param keepPrevOnFail value of keep previous on fail.
	 */
	public void setKeepPrevOnFail(boolean keepPrevOnFail) {
		super.setParam(KEEP_PREV_ON_FAIL, keepPrevOnFail);
	}
	
	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @return Value of prefix delimiter.
	 */
	public String getFieldDelimPrefix() {
		return getStringParam(FIELD_DELIM_PREFIX);
	}

	/**
	 * This specifies the prefix delimiter identifying a field in your template. The default is "&lt;&lt;".
	 * @param fieldDelimPrefix value of prefix delimiter.
	 */
	public void setFieldDelimPrefix(String fieldDelimPrefix) {
		super.setParam(FIELD_DELIM_PREFIX, fieldDelimPrefix);
	}
	
	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @return Value of suffix delimiter.
	 */
	public String getFieldDelimSuffix() {
		return getStringParam(FIELD_DELIM_SUFFIX);
	}

	/**
	 * This specifies the suffix delimiter identifying the end of a field in your template. The default is "&gt;&gt;".
	 * @param fieldDelimSuffix value of suffix delimiter.
	 */
	public void setFieldDelimSuffix(String fieldDelimSuffix) {
		super.setParam(FIELD_DELIM_SUFFIX, fieldDelimSuffix);
	}
	
	/**
	 * If set to true the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @return Value of normalize template.
	 */
	public Boolean getNormalizeTemplateName() {
		return getBooleanParam(NORMALIZE_TEMPLATE_NAME);
	}

	/**
	 * If set to true the template name given will be NFC normalized (Unicode NFC normalization).
	 * The default is false.
	 * @param normalizeTemplateName value of normalize template.
	 */
	public void setNormalizeTemplateName(boolean normalizeTemplateName) {
		super.setParam(NORMALIZE_TEMPLATE_NAME, normalizeTemplateName);
	}
}
