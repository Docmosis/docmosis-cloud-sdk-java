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
package com.docmosis.sdk.render;

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * This object holds the parameters for the render form request.
 * 
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the Render request.  The properties set in this class 
 * are parameters for the Render Form request.
 * 
 */
public class RenderFormRequestParams extends AbstractRenderRequestParams {

	private RequestParameters dataParams = new RequestParameters(new String[0]);

	public RenderFormRequestParams() {
		super();
	}

    public RequestParameters getDataParams() {
        return dataParams;
    }

    /**
     * Set a data value in the form of a name/value pair.
     * @param name or key of the data pair
     * @param value of the data pair
     */
    public void setDataParam(String name, String value) {
    	dataParams.setParam(name, value);
    }

    /**
     * Get the value for the provided name/key.
     * @param name or key of the data pair
     * @return value of the data pair
     */
    public String getDataParam(String name) {
    	return dataParams.getStringParam(name);
    }

	@Override
	public String toString()
	{
		return toString(false);
	}
	
	public String toString(boolean includeDataParams)
	{
		String dataParamsString = (includeDataParams) ? getDataParams().toString() : "";
		return super.toString() + ((dataParamsString != null && !dataParamsString.isEmpty()) ? ", " + dataParamsString : "");
	}
}
