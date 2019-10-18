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

import java.io.File;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Upload Image service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Image request.
 * 
 * Typically, you would use the Image class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   UploadImageResponse uploadedImage = Image
 *                                        .upload()
 *                                        .imageFile(uploadFile)
 *                                        .execute();
 *	 if (getImage.hasSucceeded()) {
 *       uploadedImage.toString();
 *   }
 * </pre>
 */
public class UploadImageRequest extends DocmosisCloudRequest<UploadImageRequest> {
	
	private static final String SERVICE_PATH = "uploadImage";
	
	private UploadImageRequestParams params = new UploadImageRequestParams();

	public UploadImageRequest() {
		super(SERVICE_PATH);
	}
	
	public UploadImageRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public UploadImageRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the file stream to upload.
	 * @param imageFile The file to upload
	 * @return this request for method chaining
	 */
	public UploadImageRequest imageFile(File imageFile) {
		params.setImageFile(imageFile);
		return this;
	}
	
	/**
	 * Set the name of the image on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @param imageName the image name.
	 * @return this request for method chaining
	 */
	public UploadImageRequest imageName(String imageName) {
		params.setImageName(imageName);
		return this;
	}
	
	/**
	 * Set a short description for the image.
	 * @param imageDescription the image description
	 * @return this request for method chaining
	 */
	public UploadImageRequest imageDescription(String imageDescription) {
		params.setImageDescription(imageDescription);
		return this;
	}

	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * @param isSystemImage Is system image flag
	 * @return this request for method chaining
	 */
	public UploadImageRequest isSystemImage(boolean isSystemImage) {
		params.setIsSystemImage(isSystemImage);
		return this;
	}

	/**
	 * If set to true the image name given will be NFC normalized (Unicode NFC normalization). The default is false.
	 * @param normalizeImageName value
	 * @return this request for method chaining
	 */
	public UploadImageRequest normalizeImageName(boolean normalizeImageName) {
		params.setNormalizeImageName(normalizeImageName);
		return this;
	}

	/**
	 * Execute an upload image request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public UploadImageResponse execute() throws ImageException {
		return Image.executeUploadImage(this);
	}

	/**
	 * Execute an upload image request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public UploadImageResponse execute(String url, String accessKey) throws ImageException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Image.executeUploadImage(this);
	}

	/**
	 * Execute an upload image request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public UploadImageResponse execute(String accessKey) throws ImageException {
		getEnvironment().setAccessKey(accessKey);
		return Image.executeUploadImage(this);
	}

	/**
	 * Execute an upload image request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, success message or possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public UploadImageResponse execute(Environment environment) throws ImageException {
		super.setEnvironment(environment);
		return Image.executeUploadImage(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}
