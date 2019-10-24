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
import com.docmosis.sdk.request.DocmosisCloudFileRequest;

/**
 * The object holds the instructions and data for a request to the Get File service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Get File request.
 * 
 * Typically, you would use the FileStorage class to get an instance of this class, 
 * then set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *  GetFileResponse getFile = FileStorage
 *                              .get()
 *                              .fileName(fileToGet)
 *                              .sendTo(outputFileOrStream)
 *                              .execute();
 *  if (getFile.hasSucceeded()) {
 *      //File saved to outputFileOrStream
 *  }
 * </pre>
 */
public class GetFileRequest extends DocmosisCloudFileRequest<GetFileRequest> {
	
	private static final String SERVICE_PATH = "getFile";

	private GetFileRequestParams params = new GetFileRequestParams();

	public GetFileRequest() {
		super(SERVICE_PATH);
	}
	
	public GetFileRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public GetFileRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the name of the file, optionally including its path.
	 * @param fileName name and path of the file
	 * @return this request for method chaining
	 */
	public GetFileRequest fileName(String fileName) {
		params.setFileName(fileName);
		return this;
	}

	/**
	 * Execute a get file request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public GetFileResponse execute() throws FileException {
		return FileStorage.executeGetFile(this);
	}

	/**
	 * Execute a get file request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public GetFileResponse execute(String url, String accessKey) throws FileException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return FileStorage.executeGetFile(this);
	}

	/**
	 * Execute a get file request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public GetFileResponse execute(String accessKey) throws FileException {
		getEnvironment().setAccessKey(accessKey);
		return FileStorage.executeGetFile(this);
	}

	/**
	 * Execute a get file request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws FileException if a problem occurs invoking the service 
	 */
	@Override
	public GetFileResponse execute(Environment environment) throws FileException {
		super.setEnvironment(environment);
		return FileStorage.executeGetFile(this);
	}

	@Override
	protected GetFileRequest getThis()
	{
		return this;
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
