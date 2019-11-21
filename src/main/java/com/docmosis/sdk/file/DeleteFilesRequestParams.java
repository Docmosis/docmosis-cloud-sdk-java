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
 * The object holds the parameters for a request to the Delete File service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 *
 */
public class DeleteFilesRequestParams extends RequestParameters {

	private static final String PATH				= "path";
	private static final String INCLUDE_SUB_FOLDERS	= "includeSubFolders";
	
	private static final String[] REQUIRED_PARAMS	= {PATH};

	public DeleteFilesRequestParams() {
		super(REQUIRED_PARAMS);
	}

	/**
	 * The currently set name of the file or folder.
	 * @return currently set name of the file or folder
	 */
	public String getPath() {
		return getStringParam(PATH);
	}

	/**
	 * The name of the file or folder.
	 * @param path of file/folder
	 */
	public void setPath(String path) {
		super.setParam(PATH, path);
	}
	
	/**
	 * If true all files within the given path are deleted also.
	 * @return include sub folders value
	 */
	public boolean getIncludeSubFolders() {
		return getBooleanParam(INCLUDE_SUB_FOLDERS);
	}

	/**
	 * If true all files within the given path are deleted also.
	 * @param includeSubFolders value
	 */
	public void setIncludeSubFolders(boolean includeSubFolders) {
		super.setParam(INCLUDE_SUB_FOLDERS, includeSubFolders);
	}
}
