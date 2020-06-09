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

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a batch upload template status request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
 * 
 * 
 * <pre>
 *   UploadTemplateBatchResponse response = Template
 *                                       	.uploadBatch()
 *                                       	.templateZip(uploadFiles)
 *                                       	.execute();
 *   if (response.hasSucceeded()) {
 *       UploadTemplateBatchStatusResponse responseStatus = response
 *       													.statusRequest()
 *       													.execute();
 *   }
 * </pre>
 */
public class UploadTemplateBatchStatusResponse extends DocmosisCloudResponse {

	private JobStatus jobStatus = null;
	
	protected UploadTemplateBatchStatusResponse(DocmosisCloudResponse other) {
		super(other);
	}

	/**
	 * The status of the job:
	 * userJobId – An identifier for the batch upload job which can be used to check the status of the job or to cancel the job. If no id was provided in the request a generated one is returned.
	 * isEnded – "true" or "false".
	 * status – The current status of the job.
	 * type – The type of job being run.
	 * processingMsg – A message about the template processing.
	 * startedTime – Started time in milliseconds (Epoch time).
	 * finishedTime – Finished time in milliseconds (Epoch time). 0 if job is ongoing.
	 * duration – Duration of job in milliseconds.
	 * pctComplete – Percentage complete, 0 – 100.
	 * jobResult – The result of the job (when isEnded = true)
	 * -	errorsDetected – true if any of the uploaded templates have errors
	 * -	uploadedIntoFolder – the provided folder path to upload the templates to.
	 * -	devMode – the dev mode setting used when it was uploaded.
	 * -	uploadedTotalCount – total number of templates uploaded.
	 * -	processedWithErrorsCount – number of uploaded templates processed with errors.
	 * -	processedWithoutErrorsCount – number of uploaded templates processed without errors.
	 * @return TemplateDetails Object
	 */
	public JobStatus getJobStatus() {
		return jobStatus;
	}

	protected void setJobStatus(JobStatus jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	public UploadTemplateBatchStatusRequest statusRequest() {
		final UploadTemplateBatchStatusRequest req = new UploadTemplateBatchStatusRequest();
		req.userJobId(this.getJobStatus().getUserJobId());
		return req;
	}
	
	public UploadTemplateBatchCancelRequest cancelRequest() {
		final UploadTemplateBatchCancelRequest req = new UploadTemplateBatchCancelRequest();
		req.userJobId(this.getJobStatus().getUserJobId());
		return req;
	}
	
	@Override
	public String toString() {
		if (jobStatus != null) {
			return jobStatus.toString();
		}
		else {
			return "";
		}
	}
}
