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

import com.docmosis.sdk.response.DocmosisCloudResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

/**
 * This class encapsulates a response to a get template structure request.
 * 
 * Typically you would use this response to check for success, then access the returned template structure.   
 * For example:
 * 
 * 
 * <pre>
 *   GetTemplateStructureResponse response = Template
 *                                             .getStructure()
 *                                             .templateName("MasterTemplates/MyMasterTemplate.docx")
 *                                             .execute();
 *   if (response.hasSucceeded()) {
 *       JsonElement structure = response.getTemplateStructure();
 *   }
 * </pre>
 * 
 */
public class GetTemplateStructureResponse extends DocmosisCloudResponse {

	private JsonElement templateStructure = null;
	
	protected GetTemplateStructureResponse(DocmosisCloudResponse other) {
		super(other);
	}

	/**
	 * Get a json representation of the template's structure. The JSON keys use the terms “field”, “repeat”, 
	 * “condition” and “image” to instruct what type of item it is, and then an index starting at 0.
	 * Importantly, items are in order and nested structures matching the template. This means that fields 
	 * within repeating sections will be depicted within a matching structure, eg:
	 * {
	 *    “field.0”:”firstName”,
	 *    “field.1”:”lastName”,
	 *    “repeat.0.addresses”:{
	 *       “field.3”:”addressLine1”,
	 *       “field.4”:”addressLine2”,
	 *    }
	 * }
	 * 
	 * @return template structure as a JsonElement
	 */
	public JsonElement getTemplateStructure() {
		return templateStructure;
	}

	protected void setTemplateStructure(JsonElement templateStructure) {
		this.templateStructure = templateStructure;
	}

	/**
	 * Get a json representation of the template's structure. The JSON keys use the terms “field”, “repeat”, 
	 * “condition” and “image” to instruct what type of item it is, and then an index starting at 0.
	 * Importantly, items are in order and nested structures matching the template. This means that fields 
	 * within repeating sections will be depicted within a matching structure, eg:
	 * {
	 *    “field.0”:”firstName”,
	 *    “field.1”:”lastName”,
	 *    “repeat.0.addresses”:{
	 *       “field.3”:”addressLine1”,
	 *       “field.4”:”addressLine2”,
	 *    }
	 * }
	 * 
	 * @return template structure as a formatted String
	 */
	public String getTemplateStructureString() {
		return toString();
	}
	
	@Override
	public String toString() {
		if (templateStructure != null) { //Build formatted Json String to return.
			Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
			return gson.toJson(templateStructure);
		}
		else {
			return "";
		}
	}
}
