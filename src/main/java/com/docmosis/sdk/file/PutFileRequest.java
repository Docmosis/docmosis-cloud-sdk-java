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
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Put File service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Put File request.
 * 
 * Typically, you would use the FileStorage class to get an instance of this class, 
 * then set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   PutFileResponse uploadedFile = FileStorage
 *   									.put()
 *   									.file(uploadFile)
 *   									.execute();
 *   if (uploadedFile.hasSucceeded()) {
 *   	uploadedFile.toString();
 *   }
 * </pre>
 */
public class PutFileRequest extends DocmosisCloudRequest<PutFileRequest> {

	private static final String SERVICE_PATH = "putFile";
	private File file = null;
	private String fileName;
	private String contentType;
	private String metaData;

	public PutFileRequest() {
		super(SERVICE_PATH);
	}

	public PutFileRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	/**
	 * The file stream to upload.
	 * @param file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Set the file stream to upload.
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Set the file stream to upload.
	 * @param file
	 */
	public PutFileRequest file(File file) {
		this.file = file;
		return getThis();
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
		return getThis();
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
		return getThis();
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
		return getThis();
	}

	@Override
	public PutFileResponse execute() throws DocmosisException {
		return FileStorage.executePutFile(getThis());
	}
	
	@Override
	public PutFileResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return FileStorage.executePutFile(getThis());
	}
	
	@Override
	public PutFileResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return FileStorage.executePutFile(getThis());
	}

	@Override
	public PutFileResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return FileStorage.executePutFile(getThis());
	}

	@Override
	protected PutFileRequest getThis()
	{
		return this;
	}

	@Override
	public String toString() {
		return "PutFileRequest [file=" + file + ", fileName=" + fileName + ", contentType=" + contentType
				+ ", metaData=" + metaData + ", " + super.toString() + "]";
	}
}
