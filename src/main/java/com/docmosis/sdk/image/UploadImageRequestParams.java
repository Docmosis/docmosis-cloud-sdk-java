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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Upload Image service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Image request.
 *
 */
public class UploadImageRequestParams extends RequestParameters {
	
	private static final String IMAGE_FILE				= "imageFile";
	private static final String IMAGE_NAME				= "imageName";
	private static final String IMAGE_DESCRIPTION		= "imageDescription";
	private static final String NORMALIZE_IMAGE_NAME	= "normalizeImageName";
	
	private static final String[] REQUIRED_PARAMS		= {IMAGE_FILE};

	public UploadImageRequestParams() {
		super(REQUIRED_PARAMS);
	}

	/**
	 * The file stream of the image.
	 * @return imageFile File Object
	 */
	public File getImageFile() {
		return getFileParam(IMAGE_FILE);
	}

	/**
	 * Set the file stream to upload.
	 * @param imageFile The file to upload
	 */
	public void setImageFile(File imageFile) {
		super.setParam(IMAGE_FILE, imageFile);
	}

	/**
	 * The name of the image on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @return The image name.
	 */
	public String getImageName() {
		return getStringParam(IMAGE_NAME);
	}

	/**
	 * Set the name of the image on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @param imageName the image name.
	 */
	public void setImageName(String imageName) {
		super.setParam(IMAGE_NAME, imageName);
	}
	
	/**
	 * A short description for the image.
	 * @return image description String
	 */
	public String getImageDescription() {
		return getStringParam(IMAGE_DESCRIPTION);
	}

	/**
	 * Set a short description for the image.
	 * @param imageDescription the image description
	 */
	public void setImageDescription(String imageDescription) {
		super.setParam(IMAGE_DESCRIPTION, imageDescription);
	}

	/**
	 * If set to true the image name given will be NFC normalized (Unicode NFC normalization). The default is false.
	 * @return normalizeImageName flag
	 */
	public boolean getNormalizeImageName() {
		return getBooleanParam(NORMALIZE_IMAGE_NAME);
	}

	/**
	 * If set to true the image name given will be NFC normalized (Unicode NFC normalization). The default is false.
	 * @param normalizeImageName value
	 */
	public void setNormalizeImageName(boolean normalizeImageName) {
		super.setParam(NORMALIZE_IMAGE_NAME, normalizeImageName);
	}
}
