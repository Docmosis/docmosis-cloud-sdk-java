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
 *   ListImagesResponse response = Image.list().execute();
 *   List&lt;ImageDetails&gt; list = response.list();
 *   for(ImageDetails id : list) {
 *       System.out.println(id.toString());
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

	/**
	 * Execute a list images request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, a list of ImageDetails objects and possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public ListImagesResponse execute() throws ImageException {
		return Image.executelist(this);
	}

	/**
	 * Execute a list images request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, a list of ImageDetails objects and possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public ListImagesResponse execute(String url, String accessKey) throws ImageException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Image.executelist(this);
	}

	/**
	 * Execute a list images request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, a list of ImageDetails objects and possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public ListImagesResponse execute(String accessKey) throws ImageException {
		getEnvironment().setAccessKey(accessKey);
		return Image.executelist(this);
	}

	/**
	 * Execute a list images request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, a list of ImageDetails objects and possible error messages.
	 * 
	 * @throws ImageException if a problem occurs invoking the service 
	 */
	@Override
	public ListImagesResponse execute(Environment environment) throws ImageException {
		super.setEnvironment(environment);
		return Image.executelist(this);
	}

	@Override
	public String toString() {
		return "ListImagesRequest [" + super.toString() + "]";
	}
}
