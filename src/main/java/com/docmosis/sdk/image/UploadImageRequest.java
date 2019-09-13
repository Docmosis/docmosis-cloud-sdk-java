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
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Upload Image service.
 * See the Web Services Developer guide at {@link "http://www.docmosis.com/support"}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Upload Image request.
 * 
 * Typically, you would use the Image class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   UploadImageResponse uploadedImage = Image.upload()
 *											.imageFile(uploadFile)
 *											.execute();
 *	 if (getImage.hasSucceeded()) {
 *   	uploadedImage.toString();
 *   }
 * </pre>
 */
public class UploadImageRequest extends DocmosisCloudRequest<UploadImageRequest> {
	
	private static final String SERVICE_PATH = "uploadImage";
	private String imageName;
	private boolean isSystemImage = false;
	private File imageFile = null;
	private String imageDescription = "";
	private boolean normalizeImageName = false;

	public UploadImageRequest() {
		super(SERVICE_PATH);
	}
	
	public UploadImageRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	/**
	 * The name of the image on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @return The image name.
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * Set the name of the image on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @param imageName the image name.
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	/**
	 * Set the name of the image on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @param imageName the image name.
	 * @return this request for method chaining
	 */
	public UploadImageRequest imageName(String imageName) {
		this.imageName = imageName;
		return getThis();
	}

	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * @return isSystemImage flag
	 */
	public boolean getIsSystemImage() {
		return isSystemImage;
	}

	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * @param isSystemImage Is system image flag
	 */
	public void setSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
	}
	
	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * @param isSystemImage Is system image flag
	 * @return this request for method chaining
	 */
	public UploadImageRequest isSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
		return getThis();
	}

	/**
	 * The file stream of the image.
	 * @return imageFile File Object
	 */
	public File getImageFile() {
		return imageFile;
	}

	/**
	 * The file stream of the image.
	 * @param imageFile The file to upload
	 */
	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}
	
	/**
	 * The file stream of the image.
	 * @param imageFile The file to upload
	 * @return this request for method chaining
	 */
	public UploadImageRequest imageFile(File imageFile) {
		this.imageFile = imageFile;
		return getThis();
	}

	/**
	 * A short description for the image.
	 * @return image description String
	 */
	public String getImageDescription() {
		return imageDescription;
	}

	/**
	 * A short description for the image.
	 * @param imageDescription the image description
	 */
	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	/**
	 * A short description for the image.
	 * @param imageDescription the image description
	 * @return this request for method chaining
	 */
	public UploadImageRequest imageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
		return getThis();
	}

	/**
	 * If set to "true" the image name given will be NFC normalized (Unicode NFC normalization). The default is false.
	 * @return normalizeImageName flag
	 */
	public boolean getNormalizeImageName() {
		return normalizeImageName;
	}

	/**
	 * If set to "true" the image name given will be NFC normalized (Unicode NFC normalization). The default is false.
	 * @param normalizeImageName value
	 */
	public void setNormalizeImageName(boolean normalizeImageName) {
		this.normalizeImageName = normalizeImageName;
	}

	/**
	 * If set to "true" the image name given will be NFC normalized (Unicode NFC normalization). The default is false.
	 * @param normalizeImageName value
	 * @return this request for method chaining
	 */
	public UploadImageRequest normalizeImageName(boolean normalizeImageName) {
		this.normalizeImageName = normalizeImageName;
		return getThis();
	}

	@Override
	public UploadImageResponse execute() throws DocmosisException {
		return Image.executeUploadImage(getThis());
	}
	
	@Override
	public UploadImageResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Image.executeUploadImage(getThis());
	}
	
	@Override
	public UploadImageResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Image.executeUploadImage(getThis());
	}
	
	@Override
	public UploadImageResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Image.executeUploadImage(getThis());
	}

	@Override
	public String toString() {
		return "UploadImageRequest [imageName=" + imageName + ", isSystemImage=" + isSystemImage
				+ ", imageFile=" + imageFile + ", imageDescription=" + imageDescription
				+ ", normalizeImageName=" + normalizeImageName + ", " + super.toString() + "]";
	}

	@Override
	protected UploadImageRequest getThis()
	{
		return this;
	}
}
