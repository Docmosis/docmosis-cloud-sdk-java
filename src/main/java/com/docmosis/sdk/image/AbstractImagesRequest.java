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

import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * 
 */
public abstract class AbstractImagesRequest<T extends DocmosisCloudRequest<?>> extends DocmosisCloudRequest<T> {

	private static final long serialVersionUID = 4338222626030786768L;

	private List<String> imageNames = null;
	private boolean isSystemImage = false;

	public AbstractImagesRequest(final Class<T> selfClass) {
		super(selfClass);
		imageNames = new ArrayList<String>();
	}
	
	public AbstractImagesRequest(final Class<T> selfClass, String url) {
		super(selfClass, url);
		imageNames = new ArrayList<String>();
	}

	public AbstractImagesRequest(final Class<T> selfClass, String url, String accessKey) {
		super(selfClass, url, accessKey);
		imageNames = new ArrayList<String>();
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
	 */
	public T imageNames(List<String> imageNames) {
		this.imageNames = imageNames;
		return self;
	}

	/**
	 * Add a Image Name.
	 * 
	 * @param imageName The name of the Image on the docmosis server. Should include path, eg "HeaderImages/companyLogo.png"
	 */
	public T addImageName(String imageName) {
		this.imageNames.add(imageName);
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
	public T isSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
		return self;
	}

	@Override
	public String toString() {
		String rtn = super.toString() + ", imageNames=(";
		if (imageNames != null) {
			for (String in: imageNames) {
				rtn += in + "; ";
			}
			rtn = rtn.substring(0, rtn.length()-2) + ")";
		}
		return rtn;
	}

}
