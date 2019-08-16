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

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Delete File service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the File class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteFileResponse deletedFile = File.delete()
 *   									.path(fileName)
 *   									.execute(URL, ACCESS_KEY);
 *   deletedFile.toString();
 * </pre>
 */
public class DeleteFilesRequest extends DocmosisCloudRequest<DeleteFilesRequest>  {

	private static final long serialVersionUID = 4338222626030786768L;
	
	private String path;
	private boolean includeSubFolders = false;
	
	public DeleteFilesRequest() {
		super(DeleteFilesRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs" + "/deleteFiles"); //Default url
	}
	
	public DeleteFilesRequest(String url) {
		super(DeleteFilesRequest.class, url);
	}

	public DeleteFilesRequest(String url, String accessKey) {
		super(DeleteFilesRequest.class, url, accessKey);
	}

	/**
	 * The currently set name of the file or folder.
	 * @return
	 */
	public String getPath() {
		return path;
	}

	/**
	 * The name of the file or folder.
	 * @param path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * The name of the file or folder.
	 * @param path
	 * @return
	 */
	public DeleteFilesRequest path(String path) {
		this.path = path;
		return self;
	}

	/**
	 * If "true" all files within the given path are deleted also.
	 * @return
	 */
	public boolean getIncludeSubFolders() {
		return includeSubFolders;
	}

	/**
	 * If "true" all files within the given path are deleted also.
	 * @param includeSubFolders
	 */
	public void setIncludeSubFolders(boolean includeSubFolders) {
		this.includeSubFolders = includeSubFolders;
	}

	/**
	 * If "true" all files within the given path are deleted also.
	 * @param includeSubFolders
	 * @return
	 */
	public DeleteFilesRequest includeSubFolders(boolean includeSubFolders) {
		this.includeSubFolders = includeSubFolders;
		return self;
	}

	@Override
	public String toString() {
		return "DeleteFilesRequest [" + super.toString() + ", path=" + path + ", includeSubFolders=" + includeSubFolders + "]";
	}
	
	@Override
	public DeleteFilesResponse execute() throws DocmosisException {
		return File.executeDeleteFiles(self);
	}
	
	@Override
	public DeleteFilesResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return File.executeDeleteFiles(self);
	}
	
	@Override
	public DeleteFilesResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return File.executeDeleteFiles(self);
	}
	
}
