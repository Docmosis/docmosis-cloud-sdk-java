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
package com.docmosis.sdk.file;

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the List Files service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 *
 */
public class ListFilesRequestParams extends RequestParameters {
	
	private static final String FOLDER = "folder";
	private static final String INCLUDE_SUB_FOLDERS = "includeSubFolders";
	private static final String INCLUDE_META_DATA = "includeMetaData";
	
	private static final String[] REQUIRED_PARAMS	= {};
	
	public ListFilesRequestParams() {
		super(REQUIRED_PARAMS);
	}

	/**
	 * Get the currently set starting folder (path). If not specified all files in your storage will be listed.
	 * @return path of starting folder
	 */
	public String getFolder() {
		return getStringParam(FOLDER);
	}
	
	/**
	 * Set a starting folder (path). If not specified all files in your storage will be listed.
	 * @param folder path of starting folder
	 */
	public void setFolder(String folder) {
		super.setParam(FOLDER, folder);
	}
	
	/**
	 * An optional specification as to whether you would like the list to include items within sub-folders.
	 * Defaults to false.
	 * @return include sub folders setting
	 */
	public boolean getIncludeSubFolders() {
		return getBooleanParam(INCLUDE_SUB_FOLDERS);
	}
	
	/**
	 * An optional specification as to whether you would like the list to include items within sub-folders.
	 * Defaults to false.
	 * @param includeSubFolders value
	 */
	public void setIncludeSubFolders(boolean includeSubFolders) {
		super.setParam(INCLUDE_SUB_FOLDERS, includeSubFolders);
	}
	
	/**
	 * If true meta data for each file will be included in the results.
	 * Defaults to false.
	 * @return include meta data setting
	 */
	public boolean getIncludeMetaData() {
		return getBooleanParam(INCLUDE_META_DATA);
	}

	/**
	 * If true meta data for each file will be included in the results.
	 * Defaults to false.
	 * @param includeMetaData value
	 */
	public void setIncludeMetaData(boolean includeMetaData) {
		super.setParam(INCLUDE_META_DATA, includeMetaData);
	}
}
