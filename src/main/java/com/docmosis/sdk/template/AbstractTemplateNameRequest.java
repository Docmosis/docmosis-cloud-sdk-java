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

/**
 * 
 */
public abstract class AbstractTemplateNameRequest<T extends AbstractTemplateRequest<?>> extends AbstractTemplateRequest<T> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	private String templateName;

	public AbstractTemplateNameRequest(final Class<T> selfClass) {
		super(selfClass);
	}
	
	public AbstractTemplateNameRequest(final Class<T> selfClass, String url) {
		super(selfClass, url);
	}

	public AbstractTemplateNameRequest(final Class<T> selfClass, String url, String accessKey) {
		super(selfClass, url, accessKey);
	}

	/**
	 * Get the currently set templateName.
	 * 
	 * @return The name of the Template on the Docmosis server.
	 */
	public String getTemplateName() {
		return templateName;
	}

	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the Docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public void setTemplateNam(String templateName) {
		this.templateName = templateName;
	}

	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the Docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 */
	public T templateName(String templateName) {
		this.templateName = templateName;
		return self;
	}
	
	@Override
	public String toString() {
		return super.toString() + ", templateName=" + templateName;
	}
}
