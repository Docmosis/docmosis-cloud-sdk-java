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


import java.io.File;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Put File service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Put File request.
 * 
 * Typically, you would use the FileStorage class to get an instance of this class, 
 * then set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   PutFileResponse uploadedFile = FileStorage
 *                                    .put()
 *                                    .file(uploadFile)
 *                                    .execute();
 *   if (uploadedFile.hasSucceeded()) {
 *       uploadedFile.toString();
 *   }
 * </pre>
 */
public class PutFileRequest extends DocmosisCloudRequest<PutFileRequest> {

	private static final String SERVICE_PATH = "putFile";
	
	private PutFileRequestParams params = new PutFileRequestParams();

	public PutFileRequest() {
		super(SERVICE_PATH);
	}

	public PutFileRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public PutFileRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the file stream to upload.
	 * @param file to upload
	 * @return this request for method chaining
	 */
	public PutFileRequest file(File file) {
		params.setFile(file);
		return this;
	}

	/**
	 * Set an optional overriding file name which may also include a path.
	 * @param fileName value
	 * @return this request for method chaining
	 */
	public PutFileRequest fileName(String fileName) {
		params.setFileName(fileName);
		return this;
	}

	/**
	 * Set the content-type of this file. Docmosis will attempt to work out
     * the content type if not specified.
	 * @param contentType of file
	 * @return this request for method chaining
	 */
	public PutFileRequest contentType(String contentType) {
		params.setContentType(contentType);
		return this;
	}

	/**
	 * Set an optional string of information to store with this file that can
	 * be retrieved with the file later.
	 * @param metaData string
	 * @return this request for method chaining
	 */
	public PutFileRequest metaData(String metaData) {
		params.setMetaData(metaData);
		return this;
	}

	/**
	 * Execute a put file request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public PutFileResponse execute() throws FileException {
		return FileStorage.executePutFile(this);
	}

	/**
	 * Execute a put file request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public PutFileResponse execute(String url, String accessKey) throws FileException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return FileStorage.executePutFile(this);
	}

	/**
	 * Execute a put file request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public PutFileResponse execute(String accessKey) throws FileException {
		getEnvironment().setAccessKey(accessKey);
		return FileStorage.executePutFile(this);
	}

	/**
	 * Execute a put file request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public PutFileResponse execute(Environment environment) throws FileException {
		super.setEnvironment(environment);
		return FileStorage.executePutFile(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
