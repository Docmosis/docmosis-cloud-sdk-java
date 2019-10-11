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

/**
 * This object holds the parameters for the render request.
 * 
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the Render request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 */
public class RenderRequestParams extends AbstractRenderRequestParams {

    public static final String DATA = "data";

	public RenderRequestParams() {
		super();
	}

    public String getData() {
        return getStringParam(DATA);
    }

    /**
     * Set the data for the render. The format of the string is either JSON 
     * and the structure of your data should match the template you are using.
     * 
     * @param data JSON or XML data.
     */
    public void setData(String data) 
    {
    	setParam(DATA, data);
    }

	@Override
	public String toString()
	{
		return toString(false);
	}
    
	public String toString(boolean includeData)
	{
		return super.toString(includeData ? null : new String[] { DATA } );
	}
}
