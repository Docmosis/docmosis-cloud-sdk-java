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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the List Images service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the Image class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   ListImagesRequest images = Image.list().execute();
 *   List&lt;ImageDetails&gt; list = images.list();
 *   for(ImageDetails id : list) {
 *       id.toString();
 *   }
 * </pre>
 */
public class ListImagesRequest extends DocmosisCloudRequest<ListImagesRequest> {

	private static final String SERVICE_PATH = "listImages";

	public ListImagesRequest() {
		super(SERVICE_PATH);
	}
	
	public ListImagesRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}

	@Override
	public ListImagesResponse execute() throws DocmosisException {
		return Image.executelist(this);
	}
	
	@Override
	public ListImagesResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Image.executelist(this);
	}
	
	@Override
	public ListImagesResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return Image.executelist(this);
	}

	@Override
	public ListImagesResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return Image.executelist(this);
	}


	@Override
	public String toString() {
		return "ListImagesRequest [" + super.toString() + "]";
	}
}
