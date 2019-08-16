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

import com.docmosis.sdk.request.DocmosisCloudRequest;

/**
 * 
 */
public abstract class AbstractTemplateRequest<T extends DocmosisCloudRequest<?>> extends DocmosisCloudRequest<T> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	private boolean isSystemTemplate = false;

	public AbstractTemplateRequest(final Class<T> selfClass) {
		super(selfClass);
	}
	
	public AbstractTemplateRequest(final Class<T> selfClass, String url) {
		super(selfClass, url);
	}

	public AbstractTemplateRequest(final Class<T> selfClass, String url, String accessKey) {
		super(selfClass, url, accessKey);
	}

	/**
	 * If set to "true", templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @return isSystemTemplate flag
	 */
	public boolean getIsSystemTemplate() {
		return isSystemTemplate;
	}

	/**
	 * If set to "true", templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 */
	public void setSystemTemplate(boolean isSystemTemplate) {
		this.isSystemTemplate = isSystemTemplate;
	}
	
	/**
	 * If set to "true", templateName refers to a System template, as opposed to your own template. System templates are managed by administrators.
	 * 
	 * @param isSystemTemplate Is system template flag
	 */
	public T isSystemTemplate(boolean isSystemTemplate) {
		this.isSystemTemplate = isSystemTemplate;
		return self;
	}

	@Override
	public String toString() {
		return super.toString() + ", isSystemTemplate=" + isSystemTemplate;
	}
}
