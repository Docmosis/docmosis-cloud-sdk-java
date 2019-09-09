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

import com.docmosis.sdk.environmentconfiguration.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Delete File service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the FileStorage class to get an instance of this class, 
 * then set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteFileResponse deletedFile = FileStorage
 *   									.delete()
 *   									.path(fileName)
 *   									.execute();
 *   if (deletedFile.hasSucceeded()) {
 *   	deletedFile.toString();
 *   }
 * </pre>
 */
public class DeleteFilesRequest extends DocmosisCloudRequest<DeleteFilesRequest>  {

	private static final String SERVICE_PATH = "deleteFiles";
	private String path;
	private boolean includeSubFolders = false;
	
	public DeleteFilesRequest() {
		super(SERVICE_PATH);
	}
	
	public DeleteFilesRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
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
		return getThis();
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
		return getThis();
	}

	@Override
	public DeleteFilesResponse execute() throws DocmosisException {
		return FileStorage.executeDeleteFiles(getThis());
	}
	
	@Override
	public DeleteFilesResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return FileStorage.executeDeleteFiles(getThis());
	}
	
	@Override
	public DeleteFilesResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return FileStorage.executeDeleteFiles(getThis());
	}

	@Override
	public DeleteFilesResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return FileStorage.executeDeleteFiles(getThis());
	}

	@Override
	protected DeleteFilesRequest getThis()
	{
		return this;
	}

	@Override
	public String toString() {
		return "DeleteFilesRequest [path=" + path + ", includeSubFolders=" + includeSubFolders + ", " + super.toString() + "]";
	}
}
