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

import java.util.List;

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * This class encapsulates a response to a list templates request.
 * 
 * Typically you would use this response to check for success, then access the returned list of 
 * TemplateDetails objects.  For example:
 * 
 * 
 * <pre>
 *   ListTemplatesResponse templates = Template.list().execute();
 *   List&lt;TemplateDetails&gt; list = templates.list();
 *   for(TemplateDetails td : list) {
 *       td.toString();
 *   }
 * </pre>
 */
public class ListTemplatesResponse extends DocmosisCloudResponse {

	private boolean templateListStale;
	private List<TemplateDetails> templates = null;
	
	protected ListTemplatesResponse(DocmosisCloudResponse other) {
		super(other);
	}

	/**
	 * The list of templates having the following attributes for each template:
	 * name - the template file name
	 * lastModifiedMillisSinceEpoch - last modified in milliseconds
	 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
	 * sizeBytes - the size in bytes
	 * isSystemTemplate - whether a system template (true or false)
	 * templatePlainTextFieldPrefix - the prefix used when it was uploaded
	 * templatePlainTextFieldSuffix - the suffix used when it was uploaded
	 * templateDevMode - the dev mode setting used when it was uploaded
	 * templateHasErrors - true if the uploaded template has errors
	 * templateDescription- the description uploaded with the template
	 * @return List of TemplateDetails Objects
	 */
	public List<TemplateDetails> getTemplates() {
		return templates;
	}

	/**
	 * The list of templates having the following attributes for each template:
	 * name - the template file name
	 * lastModifiedMillisSinceEpoch - last modified in milliseconds
	 * lastModifiedISO8601 - last modified yyyy-MM-dd'T'HH:mm:ssZ
	 * sizeBytes - the size in bytes
	 * isSystemTemplate - whether a system template (true or false)
	 * templatePlainTextFieldPrefix - the prefix used when it was uploaded
	 * templatePlainTextFieldSuffix - the suffix used when it was uploaded
	 * templateDevMode - the dev mode setting used when it was uploaded
	 * templateHasErrors - true if the uploaded template has errors
	 * templateDescription- the description uploaded with the template
	 * @return List of TemplateDetails Objects
	 */
	public List<TemplateDetails> list() {
		return templates;
	}

	protected void setTemplates(List<TemplateDetails> templates) {
		this.templates = templates;
	}

	/**
	 * If Docmosis detects changes to the template list are in progress
	 * (such as updates or deletions) this flag will be set to true to
	 * indicate the list is not necessarily up to date. This is only ever
	 * expected to be true for a short period after deletes or updates.
	 * @return template list stale status
	 */
	public boolean getTemplateListStale() {
		return templateListStale;
	}

	protected void setTemplateListStale(boolean templateListStale) {
		this.templateListStale = templateListStale;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("templateListStale: " + getTemplateListStale());
		if (templates != null) { //Build formatted String to return.
			for(TemplateDetails td : templates) {
				sb.append(td.toString());
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}
}
