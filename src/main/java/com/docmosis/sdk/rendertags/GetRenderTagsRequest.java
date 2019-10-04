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
package com.docmosis.sdk.rendertags;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * The object holds the instructions and data for a request to the Get Render Tags service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetRenderTagsResponse renderTags = RenderTags
 *                                       .get()
 *                                       .tags("list;of;tags;")
 *                                       .year(2019)
 *                                       .month(6)
 *                                       .nMonths(2)
 *                                       .execute();
 *   List&lt;RenderTags&gt; list = renderTags.list();
 *   for(RenderTags rt : list) {
 *       ...
 *   }
 * </pre>
 */
public class GetRenderTagsRequest extends DocmosisCloudRequest<GetRenderTagsRequest> {
	
	private static final String SERVICE_PATH = "getRenderTags";
	
	private GetRenderTagsRequestParams params = new GetRenderTagsRequestParams();
	
	public GetRenderTagsRequest() {
		super(SERVICE_PATH);
	}
	
	public GetRenderTagsRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public GetRenderTagsRequestParams getParams()
    {
    	return params;
    }

	/**
	 * The tags to query. This can be a single tag or a list of tags separated by the ; character.
	 * @param tags "list;of;tags;"
	 * @return this request for method chaining
	 */
	public GetRenderTagsRequest tags(String tags) {
		params.setTags(tags);
		return this;
	}

	/**
	 * The year on which to report statistics. Defaults to the current year.
	 * @param year a valid year
	 * @return this request for method chaining
	 */
	public GetRenderTagsRequest year(int year) {
		params.setYear(year);
		return this;
	}

	/**
	 * The month on which to report statistics (1=Jan). Defaults to the current month.
	 * @param month a valid month
	 * @return this request for method chaining
	 */
	public GetRenderTagsRequest month(int month) {
		params.setMonth(month);
		return this;
	}

	/**
	 * The number of months on which to report statistics. Defaults to 1.
	 * If more than one month is being reported, the months prior to the given year
	 * and month are included. This means that this call always reports up to the
	 * specified month.
	 * @param nMonths number of months
	 * @return this request for method chaining
	 */
	public GetRenderTagsRequest nMonths(int nMonths) {
		params.setNMonths(nMonths);
		return this;
	}

	/**
	 * If true zero values will be included where no data exists.
	 * This may make parsing the returned result easier since it will always contain
	 * values for the tags requested over the given time period by padding the data
	 * with zero-values as required.
	 * Defaults to false.
	 * @param padBlanks value
	 * @return this request for method chaining
	 */
	public GetRenderTagsRequest padBlanks(boolean padBlanks) {
		params.setPadBlanks(padBlanks);
		return this;
	}

	@Override
	public GetRenderTagsResponse execute() throws DocmosisException {
		return RenderTags.executeGetRenderTags(this);
	}
	
	@Override
	public GetRenderTagsResponse execute(String url, String accessKey) throws DocmosisException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return RenderTags.executeGetRenderTags(this);
	}
	
	@Override
	public GetRenderTagsResponse execute(String accessKey) throws DocmosisException {
		getEnvironment().setAccessKey(accessKey);
		return RenderTags.executeGetRenderTags(this);
	}

	@Override
	public GetRenderTagsResponse execute(Environment environment) throws DocmosisException {
		super.setEnvironment(environment);
		return RenderTags.executeGetRenderTags(this);
	}

	@Override
	public String toString() {
		return params.toString();
	}
}