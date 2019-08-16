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

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Upload Image service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Image class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   UploadImageResponse uploadedImage = Image.upload()
 *											.imageFile(uploadFile)
 *											.execute(URL, ACCESS_KEY);
 *   uploadedImage.toString();
 * </pre>
 */
public class UploadImageRequest extends DocmosisCloudRequest<UploadImageRequest> {
	
	private static final long serialVersionUID = 4338222626030786768L;
	
	private String imageName;
	private boolean isSystemImage = false;
	private File imageFile = null;
	private String imageDescription = "";
	private boolean normalizeImageName = false;

	public UploadImageRequest() {
		super(UploadImageRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs/uploadImage"); //Default url
	}
	
	public UploadImageRequest(String url) {
		super(UploadImageRequest.class, url);
	}

	public UploadImageRequest(String url, String accessKey) {
		super(UploadImageRequest.class, url, accessKey);
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
	 */
	public UploadImageRequest imageName(String imageName) {
		this.imageName = imageName;
		return self;
	}

	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * 
	 * @return isSystemImage flag
	 */
	public boolean getIsSystemImage() {
		return isSystemImage;
	}

	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * 
	 * @param isSystemImage Is system image flag
	 */
	public void setSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
	}
	
	/**
	 * Indicator as to whether the image is a system image or not (optional) - defaults to false.
	 * 
	 * @param isSystemImage Is system image flag
	 */
	public UploadImageRequest isSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
		return self;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File imageFile) {
		this.imageFile = imageFile;
	}
	
	public UploadImageRequest imageFile(File imageFile) {
		this.imageFile = imageFile;
		return self;
	}

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}
	
	public UploadImageRequest imageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
		return self;
	}

	public boolean getNormalizeImageName() {
		return normalizeImageName;
	}

	public void setNormalizeImageName(boolean normalizeImageName) {
		this.normalizeImageName = normalizeImageName;
	}
	
	public UploadImageRequest normalizeImageName(boolean normalizeImageName) {
		this.normalizeImageName = normalizeImageName;
		return self;
	}

	@Override
	public String toString() {
		return "UploadImageRequest [" + super.toString() + ", imageName=" + imageName + ", isSystemImage=" + isSystemImage
				+ ", imageFile=" + imageFile + ", imageDescription=" + imageDescription
				+ ", normalizeImageName=" + normalizeImageName + "]";
	}
	
	@Override
	public UploadImageResponse execute() throws DocmosisException {
		return Image.executeUploadImage(self);
	}
	
	@Override
	public UploadImageResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Image.executeUploadImage(self);
	}
	
	@Override
	public UploadImageResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return Image.executeUploadImage(self);
	}

}
