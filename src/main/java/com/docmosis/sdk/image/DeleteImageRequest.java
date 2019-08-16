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

import com.docmosis.sdk.handlers.DocmosisException;

public class DeleteImageRequest extends AbstractImagesRequest<DeleteImageRequest> {
	
	private static final long serialVersionUID = 4338222626030786768L;
	
	public DeleteImageRequest() {
		super(DeleteImageRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs/deleteImage"); //Default url
	}
	
	public DeleteImageRequest(String url) {
		super(DeleteImageRequest.class, url);
	}

	public DeleteImageRequest(String url, String accessKey) {
		super(DeleteImageRequest.class, url, accessKey);
	}

	@Override
	public String toString() {
		return "DeleteImageRequest [" + super.toString() + "]";
	}

	@Override
	public DeleteImageResponse execute() throws DocmosisException {
		return Image.executeDeleteImage(self);
	}
	
	@Override
	public DeleteImageResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Image.executeDeleteImage(self);
	}
	
	@Override
	public DeleteImageResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return Image.executeDeleteImage(self);
	}
}
