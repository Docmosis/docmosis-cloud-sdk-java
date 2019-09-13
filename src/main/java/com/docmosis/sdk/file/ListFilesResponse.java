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

import java.util.List;

import com.docmosis.sdk.response.DocmosisCloudResponse;

public class ListFilesResponse extends DocmosisCloudResponse {
	
	private boolean storedFileListStale;
	private List<FileDetails> files = null;

	/**
	 * If Docmosis detects changes to the stored file list are in progress
	 * (such as updates or deletions) this flag will be set to "true" to
	 * indicate the list is not necessarily up to date. This is only ever
	 * expected to be "true" for a short period after deletes or updates.
	 * @return stored file list stale result
	 */
	public boolean getStoredFileListStale() {
		return storedFileListStale;
	}
	public void setStoredFileListStale(boolean storedFileListStale) {
		this.storedFileListStale = storedFileListStale;
	}
	/**
	 * The list of files having the following attributes for each file:
	 * name - the file name
	 * lastModifiedMillisSinceEpoch - last modified in milliseconds
	 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
	 * sizeBytes - the size in bytes
	 * metaData - the metadata stored with the file (if requested)
	 * @return List of FileDetails Objects
	 */
	public List<FileDetails> getFiles() {
		return files;
	}
	
	/**
	 * The list of files having the following attributes for each file:
	 * name - the file name
	 * lastModifiedMillisSinceEpoch - last modified in milliseconds
	 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
	 * sizeBytes - the size in bytes
	 * metaData - the metadata stored with the file (if requested)
	 * @return List of FileDetails Objects
	 */
	public List<FileDetails> list() {
		return files;
	}
	
	public void setFiles(List<FileDetails> files) {
		this.files = files;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("storedFileListStale: " + getStoredFileListStale());
		if (files != null) { //Build formatted String to return.
			for(FileDetails id : files) {
				sb.append(System.lineSeparator());
				sb.append(id.toString());
			}
		}
		return sb.toString();
	}

}
