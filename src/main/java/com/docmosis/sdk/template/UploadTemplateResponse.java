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

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a upload template request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
 * 
 * 
 * <pre>
 *   UploadTemplateResponse response = Template
 *                                       .upload()
 *                                       .templateFile(uploadFile)
 *                                       .execute();
 *   if (response.hasSucceeded()) {
 *       //File uploaded
 *   }
 * </pre>
 */
public class UploadTemplateResponse extends DocmosisCloudResponse {

	private TemplateDetails templateDetails = null;
	
	protected UploadTemplateResponse(DocmosisCloudResponse other) {
		super(other);
	}

	/**
	 * The attributes of the uploaded template:
	 * name - the template file name
	 * lastModifiedMillisSinceEpoch - last modified in milliseconds
	 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
	 * sizeBytes - the size in bytes
	 * templatePlainTextFieldPrefix - the prefix used when it was uploaded
	 * templatePlainTextFieldSuffix - the suffix used when it was uploaded
	 * templateDevMode - the dev mode setting used when it was uploaded
	 * templateHasErrors - true if the uploaded template has errors
	 * templateDescription- the description uploaded with the template
	 * @return TemplateDetails Object
	 */
	public TemplateDetails getTemplateDetails() {
		return templateDetails;
	}

	protected void setTemplateDetails(TemplateDetails template) {
		this.templateDetails = template;
	}
	
	@Override
	public String toString() {
		if (templateDetails != null) {
			return templateDetails.toString();
		}
		else {
			return "";
		}
	}
}
