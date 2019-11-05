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
 *   GetTemplateStructureResponse templateStructure = Template
 *                                                     .getStructure()
 *                                                     .templateName("MasterTemplates/MyMasterTemplate.docx")
 *                                                     .execute();
 *   if (templateStructure.hasSucceeded()) {
 *       JsonElement structure = templateStructure.getTemplateStructure();
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
	 * Get a json representation of the template's structure, eg:
	 * [
	 *  {"type":"field", "fieldIdx":0, "text":"name", "dataRefs":["name"]},
	 *  {"type": "repeat", "repeatIdx": 0, "text": "rs_people", "dataRefs": ["people"],
	 *  "contains": [
	 *       {"type": "field", "fieldIdx": 0, "text": "name", "dataRefs": ["name"]}
	 *    ]
	 *  }
	 * ]
	 * The types reported are: “field”, “repeat”, “condition”, “image”, “templateRef” corresponding 
	 * to matching structures in the template. Each type has it’s own index which is global to the 
	 * document. “text” shows the string as presented in the template and dataRefs reports the 
	 * identified data elements correlated to the template element. The result also indicates the 
	 * nesting of elements.
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
	 * Get a formatted json string of the template's structure, eg:
	 * [
	 *  {"type":"field", "fieldIdx":0, "text":"name", "dataRefs":["name"]},
	 *  {"type": "repeat", "repeatIdx": 0, "text": "rs_people", "dataRefs": ["people"],
	 *  "contains": [
	 *       {"type": "field", "fieldIdx": 0, "text": "name", "dataRefs": ["name"]}
	 *    ]
	 *  }
	 * ]
	 * The types reported are: “field”, “repeat”, “condition”, “image”, “templateRef” corresponding 
	 * to matching structures in the template. Each type has it’s own index which is global to the 
	 * document. “text” shows the string as presented in the template and dataRefs reports the 
	 * identified data elements correlated to the template element. The result also indicates the 
	 * nesting of elements.
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
