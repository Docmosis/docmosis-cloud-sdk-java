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

public class UploadTemplateResponse extends DocmosisCloudResponse {

	private TemplateDetails templateDetails = null;
	
	public UploadTemplateResponse() {
		super();
	}
	
	public TemplateDetails getDetails() {
		return templateDetails;
	}

	public void setTemplateDetails(TemplateDetails template) {
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
