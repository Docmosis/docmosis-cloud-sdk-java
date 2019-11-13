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

import com.docmosis.sdk.environment.Environment;

/**
 * This object holds the instructions and data for the render form request.  This class is helpful for templates with simple/flat
 * data which can be added as key/pair values.  See also {@link RenderRequest} which is suitable for structured JSON or XML data.
 * <p/>
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the Render Form request.  The properties set in this class 
 * are parameters for the Render Form request.
 * <p/>
 * Typically, you would use the Renderer class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * <pre>
 *   RenderResponse response = Renderer
 *                              .renderForm()
 *                              .templateName(TemplateName)
 *                              .outputName(outputFileName)
 *                              .sendTo(outputFileOrStream)
 *                              .data(name, value)
 *                              .data(name, value)
 *                              .execute();
 *   if (response.hasSucceeded()) {
 *       //File rendered and saved to outputFileOrStream
 *	 }
 *   ...
 * </pre>
 */
public class RenderFormRequest extends AbstractRenderRequest<RenderFormRequest> {

	private static final String SERVICE_PATH = "renderForm";

    public RenderFormRequest() {
    	super(SERVICE_PATH, new RenderFormRequestParams());
    }

    public RenderFormRequest(final Environment environment) {
    	super(SERVICE_PATH, environment, new RenderFormRequestParams());
    }

    @Override
    public RenderFormRequestParams getParams()
    {
    	return (RenderFormRequestParams) super.getParams();
    }

	/**
	 * Add a data value in the form of a name/value pair.<br/>
	 * If your data is hierarchical/structured use a {@link RenderRequest} instead.
	 *  
	 * @param name or key of the data pair
	 * @param value of the data pair
	 * @return this request for method chaining
	 */
    public RenderFormRequest data(String name, String value) 
    {
    	getParams().setDataParam(name, value);
        return getThis();
    }

 
	/**
	 * Execute a render based on current settings in this instance and using the default Environment.
	 * 
	 * @return a response object giving status and possible error messages
	 * 
	 * @throws RendererException if a problem occurs invoking the service 
	 */
    @Override
	public RenderResponse execute() throws RendererException
	{
		return Renderer.executeRenderForm(this);
	}

    /**
	 * Execute a render based on contained settings.
     * 
     * @param url the service url   
     * @param accessKey your unique Docmosis accesskey
	 * 
	 * @return a response object giving status and possible error messages
	 * 
	 * @throws RendererException if a problem occurs invoking the service 
	 */
    @Override
	public RenderResponse execute(String url, String accessKey) throws RendererException {
		getEnvironment().setBaseUrl(url).setAccessKey(accessKey);
		return Renderer.executeRenderForm(this);
	}

    /**
	 * Execute a render based on contained settings.
     *       
     * @param accessKey your unique Docmosis accesskey
	 * 
	 * @return a response object giving status and possible error messages
	 * 
	 * @throws RendererException if a problem occurs invoking the service 
	 */
    @Override
	public RenderResponse execute(String accessKey) throws RendererException {
		getEnvironment().setAccessKey(accessKey);
		return Renderer.executeRenderForm(this);
	}
	
	/**
	 * Execute a render based on contained settings.
     *       
     * @param environment the Environment to use instead of the default configured. 
	 * 
	 * @return a response object giving status and possible error messages
	 * 
	 * @throws RendererException if a problem occurs invoking the service 
	 */
    @Override
	public RenderResponse execute(Environment environment) throws RendererException
	{
		setEnvironment(environment);
		return Renderer.executeRenderForm(this);
	}
    
	@Override
	protected RenderFormRequest getThis() {
		return this;
	}
	
	@Override
	public String toString()
	{
		return getParams().toString();
	}
}
