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
 * The object holds the instructions and data for a request to the Get File service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the File class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetFileResponse getFile = File.get()
 *   							.fileName(fileToGet)
 *   							.execute(URL, ACCESS_KEY);
 *   if (getFile.hasSucceeded()) {
 *  		File f = new File(fileToGet);
 *			getFile.sendDocumentTo(f);
 *  }
 * </pre>
 */
public class GetFileRequest extends DocmosisCloudRequest<GetFileRequest> {
	
	private static final long serialVersionUID = 4338222626030786768L;
	
	private String fileName;

	public GetFileRequest() {
		super(GetFileRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs" + "/getFile"); //Default url
	}
	
	public GetFileRequest(String url) {
		super(GetFileRequest.class, url);
	}

	public GetFileRequest(String url, String accessKey) {
		super(GetFileRequest.class, url, accessKey);
	}

	/**
	 * The name of the file, optionally including its path.
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set the name of the file, optionally including its path.
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Set the name of the file, optionally including its path.
	 * @param fileName
	 */
	public GetFileRequest fileName(String fileName) {
		this.fileName = fileName;
		return self;
	}

	@Override
	public String toString() {
		return "GetFileRequest [" + super.toString() + ", fileName=" + fileName + "]";
	}

	@Override
	public GetFileResponse execute() throws DocmosisException {
		return File.executeGetFile(self);
	}
	
	@Override
	public GetFileResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return File.executeGetFile(self);
	}
	
	@Override
	public GetFileResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return File.executeGetFile(self);
	}
}
