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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Put File service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Put File request.
 *
 */
public class PutFileRequestParams extends RequestParameters {

	private static final String FILE				= "file";
	private static final String FILE_NAME			= "fileName";
	private static final String CONTENT_TYPE		= "contentType";
	private static final String META_DATA			= "metaData";
	
	private static final String[] REQUIRED_PARAMS	= {FILE};

	public PutFileRequestParams() {
		super(REQUIRED_PARAMS);
	}

	/**
	 * The file stream to upload.
	 * @return file to upload
	 */
	public File getFile() {
		return getFileParam(FILE);
	}

	/**
	 * Set the file stream to upload.
	 * @param file to upload
	 */
	public void setFile(File file) {
		super.setParam(FILE, file);
	}

	/**
	 * The name of the file, optionally including its path.
	 * @return file name
	 */
	public String getFileName() {
		return getStringParam(FILE_NAME);
	}

	/**
	 * Set the name of the file, optionally including its path.
	 * @param fileName name and path of the file
	 */
	public void setFileName(String fileName) {
		super.setParam(FILE_NAME, fileName);
	}

	/**
	 * An optional setting for the content-type of this file. Docmosis will
	 * attempt to work out the content type if not specified.
	 * @return content-type of file
	 */
	public String getContentType() {
		return getStringParam(CONTENT_TYPE);
	}

	/**
	 * Set the content-type of this file. Docmosis will attempt to work out
     * the content type if not specified.
	 * @param contentType of file
	 */
	public void setContentType(String contentType) {
		super.setParam(CONTENT_TYPE, contentType);
	}
	
	/**
	 * An optional string of information to store with this file that can be
	 * retrieved with the file later.
	 * @return meta data string
	 */
	public String getMetaData() {
		return getStringParam(META_DATA);
	}

	/**
	 * Set an optional string of information to store with this file that can
	 * be retrieved with the file later.
	 * @param metaData string
	 */
	public void setMetaData(String metaData) {
		super.setParam(META_DATA, metaData);
	}
}
