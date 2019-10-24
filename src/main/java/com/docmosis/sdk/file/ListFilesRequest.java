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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the List Files service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the FileStorage class to get an instance of this class, 
 * then set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   ListFilesResponse files = FileStorage.list().execute();
 *   List&lt;FileDetails&gt; list = files.list();
 *   for(FileDetails fd : list) {
 *        ...
 *   }
 * </pre>
 */
public class ListFilesRequest extends DocmosisCloudRequest<ListFilesRequest> {
	
	private static final String SERVICE_PATH = "listFiles";
	private String folder = null;
	private boolean includeSubFolders = false;
	private boolean includeMetaData = false;
	
	public ListFilesRequest() {
		super(SERVICE_PATH);
	}
	
	public ListFilesRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	/**
	 * Get the currently set starting folder (path). If not specified all files in your storage will be listed.
	 * @return path of starting folder
	 */
	public String getFolder() {
		return folder;
	}
	
	/**
	 * Set a starting folder (path). If not specified all files in your storage will be listed.
	 * @param folder path of starting folder
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	/**
	 * Set a starting folder (path). If not specified all files in your storage will be listed.
	 * @param folder path of starting folder
	 * @return this request for method chaining
	 */
	public ListFilesRequest folder(String folder) {
		this.folder = folder;
		return this;
	}
	
	/**
	 * An optional specification as to whether you would like the list to include items within sub-folders.
	 * Defaults to false.
	 * @return include sub folders setting
	 */
	public boolean getIncludeSubFolders() {
		return includeSubFolders;
	}
	
	/**
	 * An optional specification as to whether you would like the list to include items within sub-folders.
	 * Defaults to false.
	 * @param includeSubFolders value
	 */
	public void setIncludeSubFolders(boolean includeSubFolders) {
		this.includeSubFolders = includeSubFolders;
	}
	
	/**
	 * An optional specification as to whether you would like the list to include items within sub-folders.
	 * Defaults to false.
	 * @param includeSubFolders value
	 * @return this request for method chaining
	 */
	public ListFilesRequest includeSubFolders(boolean includeSubFolders) {
		this.includeSubFolders = includeSubFolders;
		return this;
	}
	
	/**
	 * If true meta data for each file will be included in the results.
	 * Defaults to false.
	 * @return include meta data setting
	 */
	public boolean getIncludeMetaData() {
		return includeMetaData;
	}

	/**
	 * If true meta data for each file will be included in the results.
	 * Defaults to false.
	 * @param includeMetaData value
	 */
	public void setIncludeMetaData(boolean includeMetaData) {
		this.includeMetaData = includeMetaData;
	}
	
	/**
	 * If true meta data for each file will be included in the results.
	 * Defaults to false.
	 * @param includeMetaData value
	 * @return this request for method chaining
	 */
	public ListFilesRequest includeMetaData(boolean includeMetaData) {
		this.includeMetaData = includeMetaData;
		return this;
	}

	/**
	 * Execute a list files request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, a list of FileDetails objects and possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public ListFilesResponse execute() throws FileException {
		return FileStorage.executelist(this);
	}

	/**
	 * Execute a list files request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, a list of FileDetails objects and possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public ListFilesResponse execute(String url, String accessKey) throws FileException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return FileStorage.executelist(this);
	}

	/**
	 * Execute a list files request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, a list of FileDetails objects and possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public ListFilesResponse execute(String accessKey) throws FileException {
		getEnvironment().setAccessKey(accessKey);
		return FileStorage.executelist(this);
	}

	/**
	 * Execute a list files request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, a list of FileDetails objects and possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public ListFilesResponse execute(Environment environment) throws FileException {
		super.setEnvironment(environment);
		return FileStorage.executelist(this);
	}

	@Override
	public String toString() {
		return "ListFilesRequest [folder=" + folder + ", includeSubFolders=" + includeSubFolders + ", includeMetaData="
				+ includeMetaData + ", " + super.toString() + "]";
	}
}
