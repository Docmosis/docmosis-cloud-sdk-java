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
 * This object holds the instructions and data for the render request.
 * 
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the Render request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Renderer class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * <pre>
 *   RenderResponse response = Renderer
 *                              .render()
 *                              .templateName(TemplateName)
 *                              .outputName(outputFileName)
 *                              .sendTo(outputFileOrStream)
 *                              .data(dataString)
 *                              .execute();
 *   if (response.hasSucceeded()) {
 *       //File rendered and saved to outputFileOrStream
 *	 }
 *   ...
 * </pre>
 */
public class RenderRequest extends AbstractRenderRequest<RenderRequest> {

	private static final String SERVICE_PATH = "render";

    public RenderRequest() {
    	super(SERVICE_PATH, new RenderRequestParams());
    }

    public RenderRequest(final Environment environment) {
    	super(SERVICE_PATH, environment, new RenderRequestParams());
    }

    @Override
    public RenderRequestParams getParams()
    {
    	return (RenderRequestParams) super.getParams();
    }


    /**
     * Set the data for the render. The format of the string is either JSON 
     * and the structure of your data should match the template you are using.
     * 
     * @param data JSON or XML data.
     * @return this request for method chaining
     */
    public RenderRequest data(String data) 
    {
    	getParams().setData(data);
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
		return Renderer.executeRender(this);
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
		return Renderer.executeRender(this);
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
		return Renderer.executeRender(this);
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
		return Renderer.executeRender(this);
	}

    /**
     * Execute a render using current defaults (for url, accessKey and proxy) and specifying 
     * a common set of parameters.
     * This is a convenience method equivalent to:
     * <pre>
     *   renderRequest.TemplateName(templateName)
     *              .outputName(outputName);
     *              .data(data);
     *              .execute();
     * </pre>
     * 
     * 
     * @param templateName
     *            the location and name of the template within the Docmosis
     *            Template Store.
     * @param outputName
     *            the output location and file name; this is relative to the
     *            path of the current project.
     * @param data
     *            the data to inject into the Docmosis document; may be in JSON
     *            or XML format.
	 * @return a response object giving status and possible error messages
	 * @throws RendererException if a problem occurs invoking the service
     */
    public RenderResponse execute(final String templateName, final String outputName, final String data)
            throws RendererException {
    	getParams().setTemplateName(templateName);
    	getParams().setOutputName(outputName);
    	getParams().setData(data);
        
        return execute();
    }
    
	@Override
	protected RenderRequest getThis() {
		return this;
	}
	
	@Override
	public String toString()
	{
		return getParams().toString(false);
	}
}
