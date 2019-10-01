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

import java.util.ArrayList;
import java.util.List;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Delete Image service.
 * See the Web Services Developer guide at @see <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Delete Image request.
 * 
 * Typically, you would use the Image class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteImageRequest deleteImage = Image
 *   									.delete()
 *										.addImageName(fileToDelete)
 *										.execute();
 *  if (deleteTemplate.hasSucceeded()) {
 *    //Succeeded
 *  }
 * </pre>
 */
public class DeleteImageRequest extends DocmosisCloudRequest<DeleteImageRequest> {

	private static final String SERVICE_PATH = "deleteImage";
	private boolean isSystemImage = false;
	private List<String> imageNames = null;

	public DeleteImageRequest() {
		super(SERVICE_PATH);
		imageNames = new ArrayList<String>();
	}
	
	public DeleteImageRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
		imageNames = new ArrayList<String>();
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
	 * @return this request for method chaining
	 */
	public DeleteImageRequest isSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
		return this;
	}
	
	/**
	 * The names of the images on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @return The image names list.
	 */
	public List<String> getImageNames() {
		return imageNames;
	}

	/**
	 * Set the names of the images on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @param imageNames the image name list.
	 */
	public void setImageNames(List<String> imageNames) {
		this.imageNames = imageNames;
	}

	/**
	 * Set the names of the images on the Docmosis Server. Should include path, eg "HeaderImages/companyLogo.png".
	 * @param imageNames the image name list.
	 * @return this request for method chaining
	 */
	public DeleteImageRequest imageNames(List<String> imageNames) {
		this.imageNames = imageNames;
		return this;
	}

	/**
	 * Add a Image Name.
	 * 
	 * @param imageName The name of the Image on the docmosis server. Should include path, eg "HeaderImages/companyLogo.png"
	 * @return this request for method chaining
	 */
	public DeleteImageRequest addImageName(String imageName) {
		this.imageNames.add(imageName);
		return this;
	}

	@Override
	public DeleteImageResponse execute() throws DocmosisException {
		return Image.executeDeleteImage(this);
	}
	
	@Override
	public DeleteImageResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Image.executeDeleteImage(this);
	}
	
	@Override
	public DeleteImageResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Image.executeDeleteImage(this);
	}

	@Override
	public DeleteImageResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Image.executeDeleteImage(this);
	}


	@Override
	public String toString() {
		return "DeleteImageRequest [" + super.toString() + "]";
	}
}
