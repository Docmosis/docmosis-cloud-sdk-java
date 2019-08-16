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
 * The object holds the instructions and data for a request to the Rename Files service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the File class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   RenameFileResponse renameFile = File.rename()
 *   									.fromPath(oldName)
 *   									.toPath(newName)
 *   									.execute(URL, ACCESS_KEY);
 *   if (renameFile.hasSucceeded()) {
 *  	System.out.println(oldName + " renamed to " + newName);
 *  }
 * </pre>
 */
public class RenameFilesRequest extends DocmosisCloudRequest<RenameFilesRequest> {
	
	private static final long serialVersionUID = 4338222626030786768L;
	
	private String fromPath;
	private String toPath;

	public RenameFilesRequest() {
		super(RenameFilesRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs" + "/renameFiles"); //Default url
	}
	
	public RenameFilesRequest(String url) {
		super(RenameFilesRequest.class, url);
	}

	public RenameFilesRequest(String url, String accessKey) {
		super(RenameFilesRequest.class, url, accessKey);
	}

	/**
	 * The original name of the file or folder.
	 * @return
	 */
	public String getFromPath() {
		return fromPath;
	}

	/**
	 * Set the original name of the file or folder.
	 * @param fromPath
	 */
	public void setFromPath(String fromPath) {
		this.fromPath = fromPath;
	}

	/**
	 * Set the original name of the file or folder.
	 * @param fromPath
	 * @return
	 */
	public RenameFilesRequest fromPath(String fromPath) {
		this.fromPath = fromPath;
		return self;
	}

	/**
	 * The new name for the file or folder.
	 * @return
	 */
	public String getToPath() {
		return toPath;
	}

	/**
	 * Set the new name for the file or folder.
	 * @param toPath
	 */
	public void setToPath(String toPath) {
		this.toPath = toPath;
	}

	/**
	 * Set the new name for the file or folder.
	 * @param toPath
	 * @return
	 */
	public RenameFilesRequest toPath(String toPath) {
		this.toPath = toPath;
		return self;
	}

	@Override
	public String toString() {
		return "RenameFileRequest [" + super.toString() + ", fromPath=" + fromPath + ", toPath=" + toPath + "]";
	}

	@Override
	public RenameFilesResponse execute() throws DocmosisException {
		return File.executeRenameFiles(self);
	}
	
	@Override
	public RenameFilesResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return File.executeRenameFiles(self);
	}
	
	@Override
	public RenameFilesResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return File.executeRenameFiles(self);
	}
}
