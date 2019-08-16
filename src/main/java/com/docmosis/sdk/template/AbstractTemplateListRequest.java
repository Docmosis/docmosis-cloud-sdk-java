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

import java.util.ArrayList;
import java.util.List;


/**
 * 
 */
public abstract class AbstractTemplateListRequest<T extends AbstractTemplateRequest<?>> extends AbstractTemplateRequest<T> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	private List<String> templateNames = null;

	public AbstractTemplateListRequest(final Class<T> selfClass) {
		super(selfClass);
		templateNames = new ArrayList<String>();
	}
	
	public AbstractTemplateListRequest(final Class<T> selfClass, String url) {
		super(selfClass, url);
		templateNames = new ArrayList<String>();
	}

	public AbstractTemplateListRequest(final Class<T> selfClass, String url, String accessKey) {
		super(selfClass, url, accessKey);
		templateNames = new ArrayList<String>();
	}

	/**
	 * Get the currently set templateNames.
	 * 
	 * @return List of the name of the Templates on the docmosis server.
	 */
	public List<String> getTemplateNames() {
		return templateNames;
	}
	
	/**
	 * Set the Template Names.
	 * 
	 * @param templateNames The name(s) of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateNames(List<String> templateNames) {
		this.templateNames = templateNames;
	}
	
	/**
	 * Set the Template Names.
	 * 
	 * @param templateNames The name(s) of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public T templateNames(List<String> templateNames) {
		this.templateNames = templateNames;
		return self;
	}

	/**
	 * Add a Template Name.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public T addTemplateName(String templateName) {
		this.templateNames.add(templateName);
		return self;
	}

	@Override
	public String toString() {
		String rtn = super.toString() + ", templateNames=(";
		if (templateNames != null) {
			for (String tn: templateNames) {
				rtn += tn + "; ";
			}
			rtn = rtn.substring(0, rtn.length()-2) + ")";
		}
		return rtn;
	}
	
	

}
