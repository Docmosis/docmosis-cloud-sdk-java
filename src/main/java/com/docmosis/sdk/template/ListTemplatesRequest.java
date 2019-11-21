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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the List Templates service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the List request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   ListTemplatesResponse response = Template.list().execute();
 *   List&lt;TemplateDetails&gt; list = response.list();
 *   for(TemplateDetails td : list) {
 *       td.toString();
 *   }
 * </pre>
 */
public class ListTemplatesRequest extends DocmosisCloudRequest<ListTemplatesRequest> {
	
	private static final String SERVICE_PATH = "listTemplates";
	
	private ListTemplatesRequestParams params = new ListTemplatesRequestParams();
	
	public ListTemplatesRequest() {
		super(SERVICE_PATH);
	}
	
	public ListTemplatesRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public ListTemplatesRequestParams getParams()
    {
    	return params;
    }

	/**
	 * If set to true, Include extra detail about parameters.
	 * Default=true.
	 * 
	 * @param includeDetail flag
	 * @return this request for method chaining
	 */
	public ListTemplatesRequest includeDetail(boolean includeDetail) {
		params.setIncludeDetail(includeDetail);
		return this;
	}

	/**
	 * Whether or not to return results in pages. If
	 * true, pages of 1000 records are returned.
	 * Default=false.
	 * 
	 * @param paging flag.
	 * @return this request for method chaining
	 */
	public ListTemplatesRequest paging(boolean paging) {
		params.setPaging(paging);
		return this;
	}
	
	/**
	 * When paging is true, this token identifies the next page to
	 * retrieve. The page token is null for the first page. When the
	 * first page response returns, it contains the token required to
	 * request the next page.
	 * 
	 * @param pageToken The page token for the next page.
	 * @return this request for method chaining
	 */
	public ListTemplatesRequest pageToken(String pageToken) {
		params.setPageToken(pageToken);
		return this;
	}

	/**
	 * Execute a list templates request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, a list of TemplateDetails objects and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public ListTemplatesResponse execute() throws TemplateException {
		return Template.executelist(this);
	}

	/**
	 * Execute a list templates request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, a list of TemplateDetails objects and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public ListTemplatesResponse execute(String url, String accessKey) throws TemplateException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executelist(this);
	}

	/**
	 * Execute a list templates request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, a list of TemplateDetails objects and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public ListTemplatesResponse execute(String accessKey) throws TemplateException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executelist(this);
	}

	/**
	 * Execute a list templates request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, a list of TemplateDetails objects and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public ListTemplatesResponse execute(Environment environment) throws TemplateException {
		super.setEnvironment(environment);
		return Template.executelist(this);
	}

	@Override
	public String toString() {
		return "ListTemplatesRequest [" + super.toString() + "]";
	}
}
