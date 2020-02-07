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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Upload Template Batch service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Template request.
 * 
 */
public class UploadTemplateBatchRequestParams extends RequestParameters {

	private static final String TEMPLATE_ZIP			= "templateZip";
	private static final String USER_JOB_ID				= "userJobId";
	private static final String INTO_FOLDER				= "intoFolder";
	private static final String DEV_MODE				= "devMode";
	private static final String KEEP_PREV_ON_FAIL		= "keepPrevOnFail";
	private static final String FIELD_DELIM_PREFIX		= "fieldDelimPrefix";
	private static final String FIELD_DELIM_SUFFIX		= "fieldDelimSuffix";
	private static final String NORMALIZE_TEMPLATE_NAME	= "normalizeTemplateName";
	
	private static final String[] REQUIRED_PARAMS		= {TEMPLATE_ZIP};

    public UploadTemplateBatchRequestParams() {
    	super(REQUIRED_PARAMS);
    }

	/**
	 * Get currently set Template Zip File to be uploaded.
	 * @return File object of the templates to be uploaded.
	 */
	public File getTemplateZip() {
		return getFileParam(TEMPLATE_ZIP);
	}

	/**
	 * Set the Template Zip File to be uploaded.
	 * @param templateZip File object of the templates to be uploaded.
	 */
	public void setTemplateZip(File templateZip) {
		super.setParam(TEMPLATE_ZIP, templateZip);
	}

	/**
	 * Get the currently set userJobId.
	 * @return The id to use for the template upload job.
	 */
	public String getUserJobId() {
		return getStringParam(USER_JOB_ID);
	}

	/**
	 * Set the userJobId. This id can be used to check the job status and to cancel the job.
	 * @param userJobId The id to use for the template upload job.
	 */
	public void setUserJobId(String userJobId) {
		super.setParam(USER_JOB_ID, userJobId);
	}

	/**
	 * Get the path to store the templates to.
	 * @return The path to the folder to upload the templates to.
	 */
	public String getIntoFolder() {
		return getStringParam(INTO_FOLDER);
	}

	/**
	 * Set the path to store the templates to. If the path doesn't exist it will be created.
	 * @param intoFolder The path to the folder to upload the templates to.
	 */
	public void setIntoFolder(String intoFolder) {
		super.setParam(INTO_FOLDER, intoFolder);
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
