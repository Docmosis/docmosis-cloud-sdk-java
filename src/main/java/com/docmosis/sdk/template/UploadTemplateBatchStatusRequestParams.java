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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Upload Template Batch Status service.
 * See the Web Services Developer guide at <a href="https://resources.docmosis.com/">https://resources.docmosis.com/</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Template request.
 * 
 */
public class UploadTemplateBatchStatusRequestParams extends RequestParameters {

	private static final String USER_JOB_ID				= "userJobId";
	
	private static final String[] REQUIRED_PARAMS		= {USER_JOB_ID};

    public UploadTemplateBatchStatusRequestParams() {
    	super(REQUIRED_PARAMS);
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
}
