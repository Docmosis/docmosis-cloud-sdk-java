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
package com.docmosis.sdk.file;

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a delete files request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
 * 
 * 
 * <pre>
 *   DeleteFileResponse response = FileStorage
 *                                   .delete()
 *                                   .path(fileName)
 *                                   .execute();
 *   if (response.hasSucceeded()) {
 *       //File deleted
 *   }
 * </pre>
 */
public class DeleteFilesResponse extends DocmosisCloudResponse {

	protected DeleteFilesResponse(DocmosisCloudResponse other) {
		super(other);
	}

	@Override
	public String toString() {
		return "DeleteFileResponse [Status=" + getStatus() + ", Short Message=" + getShortMsg()
				+ ", Long Message=" + getLongMsg() + "]";
	}
}