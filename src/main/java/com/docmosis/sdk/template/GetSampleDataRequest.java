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
 * The object holds the instructions and data for a request to the Get Sample Data service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Get Sample Data request.
 * 
 * Typically, you would use the Template class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * 
 * <pre>
 *   GetSampleDataResponse templateSampleData = Template
 *                                               .getSampleData()
 *                                               .templateName("MasterTemplates/MyMasterTemplate.docx")
 *                                               .format("json")
 *                                               .execute();
 *   if (templateSampleData.hasSucceeded()) {
 *       templateSampleData.getSampleDataString();
 *   }
 * </pre>
 */
public class GetSampleDataRequest extends DocmosisCloudRequest<GetSampleDataRequest> {
	
	private static final String SERVICE_PATH = "getSampleData";
	
	private GetSampleDataRequestParams params = new GetSampleDataRequestParams();

	public GetSampleDataRequest() {
		super(SERVICE_PATH);
	}
	
	public GetSampleDataRequest(final Environment environment) {
		super(SERVICE_PATH, environment);
	}
	
	public GetSampleDataRequestParams getParams()
    {
    	return params;
    }

	/**
	 * Set the Template Name.
	 * 
	 * @param templateName The name of the Template on the docmosis server. Should include path, eg "samples/WelcomeTemplate.docx"
	 * @return this request for method chaining
	 */
	public GetSampleDataRequest templateName(String templateName) {
		params.setTemplateName(templateName);
		return this;
	}

	/**
	 * returns true if the docmosis server will return the Sample Data in json format.
	 * @return true if json sample data, otherwise xml
	 */
	public boolean isFormatJson() {
		String format = params.getFormat();
		return (format == null || format.isEmpty() || format.equalsIgnoreCase("json"));
	}
	
	/**
	 * Set the format of the Sample Data to be returned.
	 * 
	 * @param format format of the response, should be either "json" or "xml". Defaults to json.
	 * @return this request for method chaining
	 */
	public GetSampleDataRequest format(String format) {
		params.setFormat(format);
		return this;
	}

	/**
	 * Execute a get sample data request based on contained settings and using the default Environment.
     * 
	 * @return a response object giving status, sample data and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetSampleDataResponse execute() throws TemplateException {
		return Template.executeGetSampleData(this);
	}

	/**
	 * Execute a get sample data request based on contained settings.
     * 
     * @param url the service url
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, sample data and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetSampleDataResponse execute(String url, String accessKey) throws TemplateException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Template.executeGetSampleData(this);
	}

	/**
	 * Execute a get sample data request based on contained settings.
     * 
     * @param accessKey your unique Docmosis accesskey
     * 
	 * @return a response object giving status, sample data and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetSampleDataResponse execute(String accessKey) throws TemplateException {
		getEnvironment().setAccessKey(accessKey);
		return Template.executeGetSampleData(this);
	}

	/**
	 * Execute a get sample data request based on contained settings.
     * 
     * @param environment the environment configuration
     * 
	 * @return a response object giving status, sample data and possible error messages.
	 * 
	 * @throws TemplateException if a problem occurs invoking the service 
	 */
	@Override
	public GetSampleDataResponse execute(Environment environment) throws TemplateException {
		super.setEnvironment(environment);
		return Template.executeGetSampleData(this);
	}
	
	@Override
	public String toString() {
		return params.toString();
	}
}
