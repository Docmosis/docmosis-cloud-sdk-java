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

import java.util.Date;

/**
 * Class representing an Image Details object including:
 * name - the image file name
 * lastModifiedMillisSinceEpoch - last modified in milliseconds
 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
 * sizeBytes - the size in bytes
 * isSystemTemplate - whether a system template ("true" or "false")
 * md5 - the md5 hash code for the image
 *
 */
public class ImageDetails {

	private String name;
	private long lastModifiedMillisSinceEpoch;
	private Date lastModifiedISO8601;
	private long sizeBytes; //In Bytes
	private boolean isSystemImage;
	private String md5;

	public ImageDetails(String name, long lastModifiedMillisSinceEpoch, Date lastModifiedISO8601, long sizeBytes,
			boolean isSystemImage, String md5) {
		super();
		this.name = name;
		this.lastModifiedMillisSinceEpoch = lastModifiedMillisSinceEpoch;
		this.lastModifiedISO8601 = lastModifiedISO8601;
		this.sizeBytes = sizeBytes;
		this.isSystemImage = isSystemImage;
		this.md5 = md5;
	}

	/**
	 * 
	 * @return the image file name
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return last modified in milliseconds
	 */
	public long getLastModifiedMillisSinceEpoch() {
		return lastModifiedMillisSinceEpoch;
	}

	public void setLastModifiedMillisSinceEpoch(long lastModifiedMillisSinceEpoch) {
		this.lastModifiedMillisSinceEpoch = lastModifiedMillisSinceEpoch;
	}

	/**
	 * 
	 * @return last modified yyyy-MM-dd'T'HH:mm:ssZ
	 */
	public Date getLastModifiedISO8601() {
		return lastModifiedISO8601;
	}

	public void setLastModifiedISO8601(Date lastModifiedISO8601) {
		this.lastModifiedISO8601 = lastModifiedISO8601;
	}

	/**
	 * 
	 * @return the size in bytes
	 */
	public long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

	/**
	 * 
	 * @return whether a system template ("true" or "false")
	 */
	public boolean getIsSystemImage() {
		return isSystemImage;
	}

	public void setSystemImage(boolean isSystemImage) {
		this.isSystemImage = isSystemImage;
	}

	/**
	 * 
	 * @return the md5 hash code for the image
	 */
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Override
	public String toString() {
		return "ImageDetails [name=" + name + ", lastModifiedMillisSinceEpoch=" + lastModifiedMillisSinceEpoch
				+ ", lastModifiedISO8601=" + lastModifiedISO8601 + ", sizeBytes=" + sizeBytes + ", isSystemImage="
				+ isSystemImage + ", md5=" + md5 + "]";
	}
}
