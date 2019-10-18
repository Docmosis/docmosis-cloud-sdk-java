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

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a upload image request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
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
public class UploadImageResponse extends DocmosisCloudResponse {

	private ImageDetails imageDetails = null;
	
	public UploadImageResponse() {
		super();
	}

	/**
	 * The attributes of the uploaded image:
	 * name - the image file name
	 * lastModifiedMillisSinceEpoch - last modified in milliseconds
	 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
	 * sizeBytes - the size in bytes
	 * isSystemImage - whether a system image (true or false)
	 * md5 - the md5 hash code for the image
	 * @return ImageDetails Object
	 */
	public ImageDetails getImageDetails() {
		return imageDetails;
	}

	protected void setImageDetails(ImageDetails imageDetails) {
		this.imageDetails = imageDetails;
	}
	
	@Override
	public String toString() {
		if (imageDetails != null) {
			return imageDetails.toString();
		}
		else {
			return "";
		}
	}
}
