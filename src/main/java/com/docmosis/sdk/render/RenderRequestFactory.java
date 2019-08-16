/*
 *   Copyright 2012 Docmosis.com or its affiliates.  All Rights Reserved.
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

import java.util.Properties;

/**
 * This factory produces RenderRequest instances that can be used to invoke document production
 * by Docmosis web service end-points.
 * 
 * The factory should be initialised before use by calling the individual <code>set*</code> methods or if
 * more convenient, the <code>setDefaultsFromProperties()</code> method.  By default the factory assumes
 * you want to reach the public Docmosis Cloud Services by using the public URL (https://dws.docmosis.com/services/rs/render).
 * 
 * As an example:
 * <pre>
 *   // plug in my private access key
 *   RenderRequestFactory.setDefaultAccessKey("xxx-xxxx....");
 *   // set proxy if necessary to reach out to the internet
 *   RenderRequestFactory.setDefaultProxy("myGateway", 8080, "my auth", "my passwd");
 * </pre>
 */
public class RenderRequestFactory {

//    private static final RendererLogger log = RendererLogManager.getLogger(RenderRequestFactory.class);
    
    
    private static final String PROP_URL = "docmosis.render.url";
    private static final String PROP_ACCESS_KEY = "docmosis.render.accessKey";
    private static final String PROP_PROXY_HOST = "docmosis.render.proxy.hostname";
    private static final String PROP_PROXY_PORT = "docmosis.render.proxy.port";
    private static final String PROP_PROXY_USER_NAME = "docmosis.render.proxy.username";
    private static final String PROP_PROXY_PASSWORD = "docmosis.render.proxy.password";
    private static final String PROP_MAX_TRIES = "docmosis.render.maxTries";
    
    public static String DOCMOSIS_CLOUD_URL = "https://dws2.docmosis.com/services/rs/render";
    public static int DEFAULT_MAX_TRIES = 3;
    // back off for 1 second if retrying
    public static int DEFAULT_RETRY_DELAY_MS = 1000;
    
    private static String defaultUrl = DOCMOSIS_CLOUD_URL;
    private static RenderProxy defaultProxy;
    private static String defaultAccessKey;
    private static int defaultMaxTries = DEFAULT_MAX_TRIES;
    private static int defaultRetryDelayMS = DEFAULT_RETRY_DELAY_MS;

    public static void setDefaultUrl(String url)
    {
    	if (url == null) {
    		defaultUrl = DOCMOSIS_CLOUD_URL;
    	} else {
    		defaultUrl = url;
    	}
    }
    
    public static void setDefaultAccessKey(String accessKey)
    {
    	defaultAccessKey = accessKey;
    }
    
    /**
     * Set the default proxy settings.
     * 
     * @param host the host name
     * @param port the port number
     * @param userName the user name (may be null)
     * @param password the password (may be null) 
     */
    public static void setDefaultProxy(String host, int port, String userName, String password)
    {
    	defaultProxy = new RenderProxy(host, port, userName, password);    	
    }
    
	/**
	 * Set the default maximum number of tries that should be made to 
	 * service requests when a communications / server error occurs. 
	 * 
	 * @param maxTries the max tries to use, if less than 1, then DEFAULT_MAX_TRIES
	 * will be used.
	 */
    public static void setDefaultMaxTries(int maxTries)
    {
    	if (maxTries < 1) {
    		defaultMaxTries = DEFAULT_MAX_TRIES;
    	} else {
    		defaultMaxTries = maxTries;
    	}
    }
    /**
     * Clear any default proxy settings.
     */
    public static void clearDefaultProxy()
    {
    	defaultProxy = null;
    }
    
    /**
     * Set the factory defaults from the given properties object.  The property names used are:
     * <pre>
     *   docmosis.render.url
     *   docmosis.render.accessKey
     *   docmosis.render.proxy.hostname
     *   docmosis.render.proxy.port
     *   docmosis.render.proxy.username
     *   docmosis.render.proxy.password
     * </pre>
     *    
     * @param p the Properties instance from which any of the known properties will be extracted
     */
    public static void setDefaultsFromProperties(Properties p)
    {
    	setDefaultUrl(p.getProperty(PROP_URL));
    	setDefaultAccessKey(p.getProperty(PROP_ACCESS_KEY));

    	int maxTries = DEFAULT_MAX_TRIES;
    	try {
    		maxTries = Integer.parseInt(p.getProperty(PROP_MAX_TRIES));
    	} catch(NumberFormatException e) {
    		throw new IllegalArgumentException("Invalid value for [" + PROP_MAX_TRIES + "]", e);
    	}
    	setDefaultMaxTries(maxTries);
    	
    	if (p.getProperty(PROP_PROXY_HOST) != null) {
    		
    		try {
	    		setDefaultProxy(
	    				p.getProperty(PROP_PROXY_HOST),
	    				Integer.parseInt(p.getProperty(PROP_PROXY_PORT)),
	    				p.getProperty(PROP_PROXY_USER_NAME),
	    				p.getProperty(PROP_PROXY_PASSWORD)
	    				);
    		} catch(NumberFormatException e) {
    			throw new IllegalArgumentException("Proxy port property (\"" +
    					PROP_PROXY_PORT + "\") must be an integer type of value.  " +
						"Given [" + p.getProperty("docmosis.render.proxy.port") + "].");
    		}
    	}
    }
    
    /**
     * Get a request object based on current defaults
     * 
     * @return the request object
     */
    public static RenderRequest getRequest()
    {
    	RenderRequest rr = new RenderRequest(defaultUrl, defaultAccessKey);
    	
    	rr.setRenderProxy(defaultProxy);
    	rr.setMaxTries(defaultMaxTries);
    	rr.setRetryDelay(defaultRetryDelayMS);
    	
    	return rr;
    }
    
    /**
     * Get a request object based on current defaults and specifying the access key to use.
     * The access key is a private identifier for using the public Docmosis Cloud Services.
     * 
     * @param accessKey the access key
     * 
     * @return the request instance
     */
    public static RenderRequest getRequest(String accessKey)
    {
    	RenderRequest rr = getRequest();
    	
    	rr.setAccessKey(accessKey);
    	
    	return rr;
    }
    public static RenderRequest getRequest(String accessKey, String url)
    {
    	RenderRequest rr = getRequest();
    	
    	rr.setAccessKey(accessKey);
    	rr.setUrl(url);
    	
    	return rr;
    }
}
