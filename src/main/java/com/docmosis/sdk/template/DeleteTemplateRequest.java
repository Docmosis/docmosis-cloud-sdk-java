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
 * The object holds the instructions and data for a request to the Delete Template service.
 * See the Web Services Developer guide at {@link http://www.docmosis.com/support}
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   DeleteTemplateResponse deleteTemplate = Template.delete()
 *											.addTemplateName(fileToDelete)
 *											.execute(URL, ACCESS_KEY);
 *  if (deleteTemplate.hasSucceeded()) {
 *    //Succeeded
 *  }
 * </pre>
 */
public class DeleteTemplateRequest extends AbstractTemplateListRequest<DeleteTemplateRequest> {

	private static final long serialVersionUID = 4338222626030786768L;
	
	public DeleteTemplateRequest() {
		super(DeleteTemplateRequest.class);
		setUrl("https://dws2.docmosis.com/services/rs/deleteTemplate"); //Default url
	}
	
	public DeleteTemplateRequest(String url) {
		super(DeleteTemplateRequest.class, url);
	}

	public DeleteTemplateRequest(String url, String accessKey) {
		super(DeleteTemplateRequest.class, url, accessKey);
	}

	@Override
	public String toString() {
		return "DeleteTemplateRequest [" + super.toString() + "]";
	}

	@Override
	public DeleteTemplateResponse execute() throws DocmosisException {
		return Template.executeDeleteTemplate(self);
	}
	
	@Override
	public DeleteTemplateResponse execute(String url, String accessKey) throws DocmosisException {
		self.setUrl(url);
		self.setAccessKey(accessKey);
		return Template.executeDeleteTemplate(self);
	}
	
	@Override
	public DeleteTemplateResponse execute(String accessKey) throws DocmosisException {
		self.setAccessKey(accessKey);
		return Template.executeDeleteTemplate(self);
	}
}
