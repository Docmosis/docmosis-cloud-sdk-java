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

import java.util.List;

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a get render tags request.
 * 
 * Typically you would use this response to check for success, then access the 
 * returned list of RenderTag objects.  For example:
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
public class GetRenderTagsResponse extends DocmosisCloudResponse {

	private List<RenderTag> renderTags = null;

	protected GetRenderTagsResponse(DocmosisCloudResponse other) {
		super(other);
	}

	/**
	 * A list of RenderTags having the following attributes for each RenderTag:
	 * year – the year
	 * month – the month number
	 * tags – a list of tags having the following attributes for each tag:
	 * 		name – the tag
	 * 		countPages – the number of pages rendered for this tag in the year
	 * 		and month
	 * 		countDocuments – the number of documents rendered against this tag
	 * 		in the year and month
	 * For each tag queried, there will be an entry for that tag in the year
	 * and month. There will also be a “combined” result showing the statistics
	 * for the combined set of tags queried. This combined tag will report the
	 * stats that match all of the tags specified in the call to the
	 * getRenderTags request (that is, it will the stats only for renders that
	 * included all the tags).
	 * @return list RenderTag objects
	 */
	public List<RenderTag> getRenderTags() {
		return renderTags;
	}

	/**
	 * A list of RenderTags having the following attributes for each RenderTag:
	 * year – the year
	 * month – the month number
	 * tags – a list of tags having the following attributes for each tag:
	 * 		name – the tag
	 * 		countPages – the number of pages rendered for this tag in the year
	 * 		and month
	 * 		countDocuments – the number of documents rendered against this tag
	 * 		in the year and month
	 * For each tag queried, there will be an entry for that tag in the year
	 * and month. There will also be a “combined” result showing the statistics
	 * for the combined set of tags queried. This combined tag will report the
	 * stats that match all of the tags specified in the call to the
	 * getRenderTags request (that is, it will the stats only for renders that
	 * included all the tags).
	 * @return  list RenderTag objects
	 */
	public List<RenderTag> list() {
		return renderTags;
	}

	protected void setRenderTags(List<RenderTag> renderTags) {
		this.renderTags = renderTags;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		if (renderTags != null) { //Build formatted String to return.
			for(RenderTag rt : renderTags) {
				sb.append(rt.toString());
			}
		}
		return sb.toString();
	}
}
