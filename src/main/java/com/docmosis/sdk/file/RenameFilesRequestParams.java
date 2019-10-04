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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Rename Files service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 *
 */
public class RenameFilesRequestParams extends RequestParameters {

	private static final String FROM_PATH = "fromPath";
	private static final String TO_PATH = "toPath";

	/**
	 * The original name of the file or folder.
	 * @return original file name
	 */
	public String getFromPath() {
		return getStringParam(FROM_PATH);
	}

	/**
	 * Set the original name of the file or folder.
	 * @param fromPath original file name
	 */
	public void setFromPath(String fromPath) {
		super.setParam(FROM_PATH, fromPath);
	}
	
	/**
	 * The new name for the file or folder.
	 * @return new file name
	 */
	public String getToPath() {
		return getStringParam(TO_PATH);
	}

	/**
	 * Set the new name for the file or folder.
	 * @param toPath new file name
	 */
	public void setToPath(String toPath) {
		super.setParam(TO_PATH, toPath);
	}
}
