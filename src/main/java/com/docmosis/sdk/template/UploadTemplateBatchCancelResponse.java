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
 * This class encapsulates a response to a batch upload template cancel request.
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
 *       UploadTemplateBatchCancelResponse responseCancel = response
 *       													.cancelRequest()
 *       													.execute();
 *   }
 * </pre>
 */
public class UploadTemplateBatchCancelResponse extends DocmosisCloudResponse {

	protected UploadTemplateBatchCancelResponse(DocmosisCloudResponse other) {
		super(other);
	}
}
