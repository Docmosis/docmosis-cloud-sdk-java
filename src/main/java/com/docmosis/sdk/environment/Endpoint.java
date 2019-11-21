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
package com.docmosis.sdk.environment;

/**
 * Docmosis End Points where the web services can be obtained.
 */
public enum Endpoint {

	/** Legacy USA */
	DWS_VERSION_2_USA("https://dws2.docmosis.com/services/rs/");
	
	private final String baseUrl;
	
	private Endpoint(final String baseUrl)
	{
		this.baseUrl = baseUrl;
	}
	
	/**
	 * Get the service endpoint base url.
	 * 
	 * @return service endpoint base url
	 */
	public String getBaseUrl()
	{
		return baseUrl;
	}
}
