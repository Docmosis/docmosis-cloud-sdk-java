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
package com.docmosis.sdk.image;

import java.util.List;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Delete Image service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Delete Image request.
 * 
 * Typically, you would use the Image class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteImageResponse response = Image
 *                                   .delete()
 *                                   .addImageName(fileToDelete)
 *                                   .execute();
 *  if (response.hasSucceeded()) {
 *      //Succeeded
 *  }
 * </pre>
 */
public class DeleteImageRequest extends DocmosisCloudRequest<DeleteImageRequest> {

	private static final String SERVICE_PATH = "deleteImage";

	private DeleteImageRequestParams params = new DeleteImageRequestParams();

	public DeleteImageRequest() {
		super(SERVICE_PATH);
	}

	public DeleteImageRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	public DeleteImageRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the names of the Images to be deleted.
	 * 
	 * @param imageNames The name of the Image(s) on the Docmosis server. Should include path, eg "HeaderImages/companyLogo.png"
	 * @return this request for method chaining
	 */
	public DeleteImageRequest imageNames(List<String> imageNames) {
		params.setImageNames(imageNames);
		return this;
	}
	
	/**
	 * Add the name of an Image to be deleted.
	 *
	 * @param imageName The name of the Image on the Docmosis server. Should include path, eg "HeaderImages/companyLogo.png"
	 * @return this request for method chaining
	 */
	public DeleteImageRequest imageName(String imageName) {
		params.setImageName(imageName);
		return this;
	}

	/**
	 * Execute a delete image request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteImageResponse execute() throws ImageException {
		return Image.executeDeleteImage(this);
	}

	/**
	 * Execute a delete image request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteImageResponse execute(String url, String accessKey) throws ImageException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Image.executeDeleteImage(this);
	}

	/**
	 * Execute a delete image request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteImageResponse execute(String accessKey) throws ImageException {
		getEnvironment().setAccessKey(accessKey);
		return Image.executeDeleteImage(this);
	}

	/**
	 * Execute a delete image request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public DeleteImageResponse execute(Environment environment) throws ImageException {
		super.setEnvironment(environment);
		return Image.executeDeleteImage(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
