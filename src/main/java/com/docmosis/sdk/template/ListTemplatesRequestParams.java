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

package com.docmosis.sdk.template;

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the List Templates service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List Templates request.
 *
 */
public class ListTemplatesRequestParams extends RequestParameters {

	private static final String INCLUDE_DETAIL	= "includeDetail";
	private static final String PAGING			= "paging";
	private static final String PAGE_TOKEN		= "pageToken";
	
	private static final String[] REQUIRED_PARAMS	= {};

	public ListTemplatesRequestParams() {
		super(REQUIRED_PARAMS);
	}

	/**
	 * If set to true, Include extra detail about parameters.
	 * Default=true.
	 * 
	 * @return includeDetail flag
	 */
	public Boolean getIncludeDetail() {
		return getBooleanParam(INCLUDE_DETAIL);
	}

	/**
	 * If set to true, Include extra detail about parameters.
	 * Default=true.
	 * 
	 * @param includeDetail flag
	 */
	public void setIncludeDetail(boolean includeDetail) {
		super.setParam(INCLUDE_DETAIL, includeDetail);
	}

	/**
	 * Whether or not to return results in pages. If
	 * true, pages of 1000 records are returned.
	 * Default=false.
	 * 
	 * @return paging flag.
	 */
	public Boolean getPaging() {
		return getBooleanParam(PAGING);
	}

	/**
	 * Whether or not to return results in pages. If
	 * true, pages of 1000 records are returned.
	 * Default=false.
	 * 
	 * @param paging flag.
	 */
	public void setPaging(boolean paging) {
		super.setParam(PAGING, paging);
	}

	/**
	 * When paging is true, this token identifies the next page to
	 * retrieve. The page token is null for the first page. When the
	 * first page response returns, it contains the token required to
	 * request the next page.
	 * 
	 * @return The page token for the next page.
	 */
	public String getPageToken() {
		return getStringParam(PAGE_TOKEN);
	}

	/**
	 * When paging is true, this token identifies the next page to
	 * retrieve. The page token is null for the first page. When the
	 * first page response returns, it contains the token required to
	 * request the next page.
	 * 
	 * @param pageToken The page token for the next page.
	 */
	public void setPageToken(String pageToken) {
		super.setParam(PAGE_TOKEN, pageToken);
	}
}
