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

/**
 * Class representing a User Job:
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
 */
public class JobStatus {

	private String userJobId;
	private boolean isEnded;
	private String status;
	private String type;
	private String processingMsg;
	private long startedTime;
	private long finishedTime;
	private long duration;
	private int pctComplete;
	private JobResult jobResult;

	public JobStatus(String userJobId, boolean isEnded, String status, String type, String processingMsg,
			long startedTime, long finishedTime, long duration, int pctComplete, JobResult jobResult) {
		this.userJobId = userJobId;
		this.isEnded = isEnded;
		this.status = status;
		this.type = type;
		this.processingMsg = processingMsg;
		this.startedTime = startedTime;
		this.finishedTime = finishedTime;
		this.duration = duration;
		this.pctComplete = pctComplete;
		this.jobResult = jobResult;
	}

	/**
	 * 
	 * @return the job identifier.
	 */
	public String getUserJobId() {
		return userJobId;
	}

	protected void setUserJobId(String userJobId) {
		this.userJobId = userJobId;
	}

	/**
	 * 
	 * @return true if job has completed.
	 */
	public boolean getIsEnded() {
		return isEnded;
	}

	protected void setIsEnded(boolean isEnded) {
		this.isEnded = isEnded;
	}

	/**
	 * 
	 * @return current status of the job.
	 */
	public String getStatus() {
		return status;
	}

	protected void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return the type of job being run.
	 */
	public String getType() {
		return type;
	}

	protected void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return a message about the template processing.
	 */
	public String getProcessingMsg() {
		return processingMsg;
	}

	protected void setProcessingMsg(String processingMsg) {
		this.processingMsg = processingMsg;
	}

	/**
	 * 
	 * @return started time in milliseconds (Epoch time).
	 */
	public long getStartedTime() {
		return startedTime;
	}

	protected void setStartedTime(long startedTime) {
		this.startedTime = startedTime;
	}

	/**
	 * 
	 * @return finished time in milliseconds (Epoch time). 0 if job is ongoing.
	 */
	public long getFinishedTime() {
		return finishedTime;
	}

	protected void setFinishedTime(long finishedTime) {
		this.finishedTime = finishedTime;
	}

	/**
	 * 
	 * @return duration of job in milliseconds.
	 */
	public long getDuration() {
		return duration;
	}

	protected void setDuration(long duration) {
		this.duration = duration;
	}

	/**
	 * 
	 * @return percentage completed.
	 */
	public int getPctComplete() {
		return pctComplete;
	}

	protected void setPctComplete(int pctComplete) {
		this.pctComplete = pctComplete;
	}

	/**
	 * 
	 * @return Object representing the result of the job. Null if job is ongoing (isEnded == false).
	 */
	public JobResult getJobResult() {
		return jobResult;
	}

	protected void setJobResult(JobResult jobResult) {
		this.jobResult = jobResult;
	}
	
	

	@Override
	public String toString() {
		return "JobStatus [userJobId=" + userJobId + ", isEnded=" + isEnded + ", status=" + status + ", type=" + type
				+ ", processingMsg=" + processingMsg + ", startedTime=" + startedTime + ", finishedTime=" + finishedTime
				+ ", duration=" + duration + ", pctComplete=" + pctComplete + ", jobResult=" + jobResult + "]";
	}



	/**
	 * Class representing a User Job result:
	 * errorsDetected – true if any of the uploaded templates have errors
	 * uploadedIntoFolder – the provided folder path to upload the templates to.
	 * devMode – the dev mode setting used when it was uploaded.
	 * uploadedTotalCount – total number of templates uploaded.
	 * processedWithErrorsCount – number of uploaded templates processed with errors.
	 * processedWithoutErrorsCount – number of uploaded templates processed without errors.
	 */
	public static class JobResult {
		private boolean errorsDetected;
		private String uploadedIntoFolder;
		private boolean devMode;
		private int uploadedTotalCount;
		private int processedWithErrorsCount;
		private int processedWithoutErrorsCount;

		public JobResult(boolean errorsDetected, String uploadedIntoFolder, boolean devMode, int uploadedTotalCount,
				int processedWithErrorsCount, int processedWithoutErrorsCount) {
			this.errorsDetected = errorsDetected;
			this.uploadedIntoFolder = uploadedIntoFolder;
			this.devMode = devMode;
			this.uploadedTotalCount = uploadedTotalCount;
			this.processedWithErrorsCount = processedWithErrorsCount;
			this.processedWithoutErrorsCount = processedWithoutErrorsCount;
		}

		/**
		 * 
		 * @return true if any of the uploaded templates have errors.
		 */
		public boolean getErrorsDetected() {
			return errorsDetected;
		}

		protected void setErrorsDetected(boolean errorsDetected) {
			this.errorsDetected = errorsDetected;
		}

		/**
		 * 
		 * @return the provided folder path to upload the templates to.
		 */
		public String getUploadedIntoFolder() {
			return uploadedIntoFolder;
		}

		protected void setUploadedIntoFolder(String uploadedIntoFolder) {
			this.uploadedIntoFolder = uploadedIntoFolder;
		}

		/**
		 * 
		 * @return the dev mode setting used when it was uploaded.
		 */
		public boolean getDevMode() {
			return devMode;
		}

		protected void setDevMode(boolean devMode) {
			this.devMode = devMode;
		}

		/**
		 * 
		 * @return total number of templates uploaded.
		 */
		public int getUploadedTotalCount() {
			return uploadedTotalCount;
		}

		protected void setUploadedTotalCount(int uploadedTotalCount) {
			this.uploadedTotalCount = uploadedTotalCount;
		}

		/**
		 * 
		 * @return number of uploaded templates processed with errors.
		 */
		public int getProcessedWithErrorsCount() {
			return processedWithErrorsCount;
		}

		protected void setProcessedWithErrorsCount(int processedWithErrorsCount) {
			this.processedWithErrorsCount = processedWithErrorsCount;
		}

		/**
		 * 
		 * @return number of uploaded templates processed without errors.
		 */
		public int getProcessedWithoutErrorsCount() {
			return processedWithoutErrorsCount;
		}

		protected void setProcessedWithoutErrorsCount(int processedWithoutErrorsCount) {
			this.processedWithoutErrorsCount = processedWithoutErrorsCount;
		}

		@Override
		public String toString() {
			return "JobResult [errorsDetected=" + errorsDetected + ", uploadedIntoFolder=" + uploadedIntoFolder
					+ ", devMode=" + devMode + ", uploadedTotalCount=" + uploadedTotalCount
					+ ", processedWithErrorsCount=" + processedWithErrorsCount + ", processedWithoutErrorsCount="
					+ processedWithoutErrorsCount + "]";
		}
	}
}
