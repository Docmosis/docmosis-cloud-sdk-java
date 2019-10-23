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

import org.w3c.dom.Document;

import com.docmosis.sdk.response.DocmosisCloudResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * This class encapsulates a response to a get sample data request.
 * 
 * Typically you would use this response to check for success, then access the returned sample data.  For example:
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
public class GetSampleDataResponse extends DocmosisCloudResponse {

	private JsonElement sampleDataJson = null;
	private Document sampleDataXml = null;
	private boolean isJson;
	
	protected GetSampleDataResponse(DocmosisCloudResponse other) {
		super(other);
	}

	/**
	 * Get the returned sample data as a JsonElement. Will return null if the requested format was xml.
	 * @return Sample Data in Json format
	 */
	public JsonElement getSampleDataJson() {
		return sampleDataJson;
	}

	protected void setSampleDataJson(JsonElement sampleDataJson) {
		this.sampleDataJson = sampleDataJson;
	}

	/**
	 * Get the returned sample data as a Document object. Will return null if the requested format was json.
	 * @return Sample Data in XML format
	 */
	public Document getSampleDataXml() {
		return sampleDataXml;
	}

	protected void setSampleDataXml(Document sampleDataXml) {
		this.sampleDataXml = sampleDataXml;
	}

	/**
	 * Returns true if this object contains json sample data, otherwise xml sample data.
	 * @return true if json sample data
	 */
	public boolean isJson() {
		return isJson;
	}

	protected void setJson(boolean isJson) {
		this.isJson = isJson;
	}

	/**
	 * Returns the Json / XML data as a formatted String for display
	 * @return Formatted String of the Sample Data
	 */
	public String getSampleDataString() {
		return toString();
	}

	@Override
	public String toString() {
		if (isJson()) { //Build formatted Json String to return.
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			return gson.toJson(sampleDataJson);
		}
		else { //Build formatted xml String to return.
			return getAsXMLPretty(sampleDataXml);
		}
	}
}
