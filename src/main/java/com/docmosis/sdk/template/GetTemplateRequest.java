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

import com.docmosis.sdk.handlers.DocmosisException;

/**
 * The object holds the instructions and data for a request to the Get Template service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetTemplateResponse getTemplate = Template.get()
 *											.addTemplateName(fileToGet)
 *											.execute(URL, ACCESS_KEY);
 *  if (getTemplate.hasSucceeded()) {
 *  		File f = new File(fileToGet);
 *			getTemplate.sendDocumentTo(f);
 *  }
 * </pre>
 */
public class GetTemplateRequest extends AbstractTemplateListRequest<GetTemplateRequest> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	public GetTemplateRequest() {
		super(GetTemplateRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs/getTemplate"); //Default url
	}
	
	public GetTemplateRequest(String url) {
		super(GetTemplateRequest.class, url);
	}

	public GetTemplateRequest(String url, String accessKey) {
		super(GetTemplateRequest.class, url, accessKey);
	}

	@Override
	public String toString() {
		return "GetTemplateRequest [" + super.toString() + "]";
	}

	@Override
	public GetTemplateResponse execute() throws DocmosisException {
		return Template.executeGetTemplate(self);
	}
	
	@Override
	public GetTemplateResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Template.executeGetTemplate(self);
	}
	
	@Override
	public GetTemplateResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return Template.executeGetTemplate(self);
	}
}
