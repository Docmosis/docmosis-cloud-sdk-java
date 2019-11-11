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

import java.util.Date;

/**
 * Class representing a Template Details object including:
 * name - the template file name
 * lastModifiedMillisSinceEpoch - last modified in milliseconds
 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
 * sizeBytes - the size in bytes
 * templatePlainTextFieldPrefix - the prefix used when it was uploaded
 * templatePlainTextFieldSuffix - the suffix used when it was uploaded
 * templateDevMode - the dev mode setting used when it was uploaded
 * templateHasErrors - true if the uploaded template has errors
 * templateDescription- the description uploaded with the template if any
 * md5 - the md5 hash code for the template
 *
 */
public class TemplateDetails {

	private String name;
	private long lastModifiedMillisSinceEpoch;
	private Date lastModifiedISO8601;
	private long sizeBytes;
	private String templatePlainTextFieldPrefix;
	private String templatePlainTextFieldSuffix;
	private boolean templateHasErrors;
	private boolean templateDevMode;
	private String description = null;
	private String md5;
	
	public TemplateDetails(String name, long lastModifiedMillisSinceEpoch, Date lastModifiedISO8601, long sizeBytes, 
			String templatePlainTextFieldPrefix, String templatePlainTextFieldSuffix, boolean templateHasErrors, 
			boolean templateDevMode, String description, String md5) {
		this.name = name;
		this.lastModifiedMillisSinceEpoch = lastModifiedMillisSinceEpoch;
		this.lastModifiedISO8601 = lastModifiedISO8601;
		this.sizeBytes = sizeBytes;
		this.templatePlainTextFieldPrefix = templatePlainTextFieldPrefix;
		this.templatePlainTextFieldSuffix = templatePlainTextFieldSuffix;
		this.templateHasErrors = templateHasErrors;
		this.templateDevMode = templateDevMode;
		this.description = description;
		this.md5 = md5;
	}

	/**
	 * 
	 * @return the template file name
	 */
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return last modified in milliseconds
	 */
	public long getLastModifiedMillisSinceEpoch() {
		return lastModifiedMillisSinceEpoch;
	}

	protected void setLastModifiedMillisSinceEpoch(long lastModifiedMillisSinceEpoch) {
		this.lastModifiedMillisSinceEpoch = lastModifiedMillisSinceEpoch;
	}

	/**
	 * 
	 * @return last modified yyyy-MM-dd'T'HH:mm:ssZ
	 */
	public Date getLastModifiedISO8601() {
		return lastModifiedISO8601;
	}

	protected void setLastModifiedISO8601(Date lastModifiedISO8601) {
		this.lastModifiedISO8601 = lastModifiedISO8601;
	}

	/**
	 * 
	 * @return the size in bytes
	 */
	public long getSizeBytes() {
		return sizeBytes;
	}

	protected void setSizeBytes(long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	/**
	 * 
	 * @return the prefix used when it was uploaded
	 */
	public String getTemplatePlainTextFieldPrefix() {
		return templatePlainTextFieldPrefix;
	}

	protected void setTemplatePlainTextFieldPrefix(String templatePlainTextFieldPrefix) {
		this.templatePlainTextFieldPrefix = templatePlainTextFieldPrefix;
	}

	/**
	 * 
	 * @return the suffix used when it was uploaded
	 */
	public String getTemplatePlainTextFieldSuffix() {
		return templatePlainTextFieldSuffix;
	}

	protected void setTemplatePlainTextFieldSuffix(String templatePlainTextFieldSuffix) {
		this.templatePlainTextFieldSuffix = templatePlainTextFieldSuffix;
	}

	/**
	 * 
	 * @return true if the uploaded template has errors
	 */
	public boolean getTemplateHasErrors() {
		return templateHasErrors;
	}

	protected void setTemplateHasErrors(boolean templateHasErrors) {
		this.templateHasErrors = templateHasErrors;
	}

	/**
	 * 
	 * @return the dev mode setting used when it was uploaded
	 */
	public boolean getTemplateDevMode() {
		return templateDevMode;
	}

	protected void setTemplateDevMode(boolean templateDevMode) {
		this.templateDevMode = templateDevMode;
	}

	/**
	 * 
	 * @return the description uploaded with the template if any
	 */
	public String getDescription() {
		return description;
	}

	protected void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return the md5 hash code for the template
	 */
	public String getMd5() {
		return md5;
	}

	protected void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString() {
		return "TemplateDetails [name=" + name + ", lastModifiedMillisSinceEpoch=" + lastModifiedMillisSinceEpoch
				+ ", lastModifiedISO8601=" + lastModifiedISO8601 + ", sizeBytes=" + sizeBytes
				+ ", templatePlainTextFieldPrefix=" + templatePlainTextFieldPrefix
				+ ", templatePlainTextFieldSuffix=" + templatePlainTextFieldSuffix + ", templateHasErrors=" 
				+ templateHasErrors + ", templateDevMode=" + templateDevMode + ", description=" + description
				+ ", md5=" + md5 + "]";
	}
}
