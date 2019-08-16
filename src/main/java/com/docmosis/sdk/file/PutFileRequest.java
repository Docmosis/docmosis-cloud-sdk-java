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
 * The object holds the instructions and data for a request to the Put File service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the File class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   PutFileResponse uploadedFile = File.put()
 *   									.file(uploadFile)
 *   									.execute(URL, ACCESS_KEY);
 *   uploadedFile.toString();
 * </pre>
 */
public class PutFileRequest extends DocmosisCloudRequest<PutFileRequest> {
	
	private static final long serialVersionUID = 4338222626030786768L;
	
	private java.io.File file = null;
	private String fileName;
	private String contentType;
	private String metaData;

	public PutFileRequest() {
		super(PutFileRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs" + "/putFile"); //Default url
	}
	
	public PutFileRequest(String url) {
		super(PutFileRequest.class, url);
	}

	public PutFileRequest(String url, String accessKey) {
		super(PutFileRequest.class, url, accessKey);
	}

	/**
	 * The file stream to upload.
	 * @param file
	 */
	public java.io.File getFile() {
		return file;
	}

	/**
	 * Set the file stream to upload.
	 * @param file
	 */
	public void setFile(java.io.File file) {
		this.file = file;
	}

	/**
	 * Set the file stream to upload.
	 * @param file
	 */
	public PutFileRequest file(java.io.File file) {
		this.file = file;
		return self;
	}

	/**
	 * An optional overriding file name which may also include a path.
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Set an optional overriding file name which may also include a path.
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Set an optional overriding file name which may also include a path.
	 * @param fileName
	 */
	public PutFileRequest fileName(String fileName) {
		this.fileName = fileName;
		return self;
	}

	/**
	 * An optional setting for the content-type of this file. Docmosis will
	 * attempt to work out the content type if not specified.
	 * @return
	 */
	public String getContentType() {
		return contentType;
	}

	/**
	 * Set the content-type of this file. Docmosis will attempt to work out
     * the content type if not specified.
	 * @param contentType
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * Set the content-type of this file. Docmosis will attempt to work out
     * the content type if not specified.
	 * @param contentType
	 */
	public PutFileRequest contentType(String contentType) {
		this.contentType = contentType;
		return self;
	}

	/**
	 * An optional string of information to store with this file that can be
	 * retrieved with the file later.
	 * @return
	 */
	public String getMetaData() {
		return metaData;
	}

	/**
	 * Set an optional string of information to store with this file that can
	 * be retrieved with the file later.
	 * @param metaData
	 */
	public void setMetaData(String metaData) {
		this.metaData = metaData;
	}

	/**
	 * Set an optional string of information to store with this file that can
	 * be retrieved with the file later.
	 * @param metaData
	 */
	public PutFileRequest metaData(String metaData) {
		this.metaData = metaData;
		return self;
	}

	@Override
	public String toString() {
		return "PutFileRequest [" + super.toString() + ", file=" + file + ", fileName=" + fileName + ", contentType=" + contentType
				+ ", metaData=" + metaData + "]";
	}
	
	@Override
	public PutFileResponse execute() throws DocmosisException {
		return File.executePutFile(self);
	}
	
	@Override
	public PutFileResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return File.executePutFile(self);
	}
	
	@Override
	public PutFileResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return File.executePutFile(self);
	}

}
