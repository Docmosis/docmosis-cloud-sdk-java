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
package com.docmosis.sdk.request;

import java.io.Serializable;

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * The object holds the instructions and data for a request to the Docmosis cloud.
 * This is an abstract super object with the minimum requirements to make a request.
 * 
 * 
 * <pre>
 *   
 * </pre>
 */
//TODO Update Java Doc Above
public abstract class DocmosisCloudRequest<T extends DocmosisCloudRequest<?>> implements Serializable {

	private static final long serialVersionUID = 4338222626030786768L;
	
	//reference to self as the subclass type
	protected final T self;
   
    // rarely changed settings
    private String url;
    private String accessKey;
    private int maxTries = 3;
    private int retryDelayMS = 1000;

    public DocmosisCloudRequest(final Class<T> selfClass)
    {
    	this.self = selfClass.cast(this);
    }
    
    /**
     * Construct a new request with the specified end point url.
     * 
     * @param url the url to the Docmosis service.
     */
    public DocmosisCloudRequest(final Class<T> selfClass, String url)
    {
    	this.self = selfClass.cast(this);
    	this.url = url;
    }
    
    /**
     * Construct a new request with the specified end point url and access key.
     * 
     * @param url the url to the Docmosis service.
     * @param accessKey the access key to use for the render
     */
    public DocmosisCloudRequest(final Class<T> selfClass, String url, String accessKey)
    {
    	this.self = selfClass.cast(this);
    	this.url = url;
    	this.accessKey = accessKey;
    }    

    /**
     * The URL for the web service end point.
     * This is defaulted to the public cloud end-point for Docmosis Cloud Services.
     * @return url of docmosis cloud or tornado endpoint.
     */
	public String getUrl()
	{
		return url;
	}

    /**
     * Set the URL for the web service end point.
     * This is defaulted to the public cloud end-point for Docmosis Cloud Services. 
     * 
     * @param url url of docmosis cloud or tornado endpoint.
     */
	public void setUrl(String url)
	{
		this.url = url;
	}

    /**
     * Set the URL for the web service end point.
     * This is defaulted to the public cloud end-point for Docmosis Cloud Services. 
     * 
     * @param url url of docmosis cloud or tornado endpoint.
     */
	public T url(String url)
	{
		this.url = url;
		return self;
	}

	public String getAccessKey()
	{
		return accessKey;
	}

	/**
	 * Set the access key for using the end point.  If using the public Docmosis Cloud Services
	 * you must sign up (at least for a trial period) to obtain your access key.
	 * Not required for connecting to Tornado.
	 * 
	 * @param accessKey your unique docmosis accesskey.
	 */
	public void setAccessKey(String accessKey)
	{
		this.accessKey = accessKey;
	}

	/**
	 * Set the access key for using the end point.  If using the public Docmosis Cloud Services
	 * you must sign up (at least for a trial period) to obtain your access key.
	 * Not required for connecting to Tornado.
	 * 
	 * @param accessKey your unique docmosis accesskey.
	 */
	public T accessKey(String accessKey)
	{
		this.accessKey = accessKey;
		return self;
	}

	/**
	 * Get the maximum number of tries that should be attempted to service
	 * this request when a server/network error occurs.
	 * 
	 * @return the maximum number of tries configured.
	 */
	public int getMaxTries()
	{
		return maxTries;
	}

	/**
	 * Set the maximum number of tries that should be made to service this request 
	 * when a communications / server error occurs. 
	 * 
	 * @param maxTries the maximum number of tries.
	 */
	public void setMaxTries(int maxTries)
	{
		this.maxTries = maxTries;
	}

	/**
	 * Set the maximum number of tries that should be made to service this request 
	 * when a communications / server error occurs. 
	 * 
	 * @param maxTries the maximum number of tries.
	 */
	public T maxTries(int maxTries)
	{
		this.maxTries = maxTries;
		return self;
	}

	/**
	 * Get the retry delay (milliseconds) to apply when
	 * a retry is required.
	 * 
	 * @return the configured retry delay
	 */
	public int getRetryDelay()
	{
		return retryDelayMS;
	}

	/**
	 * Set the retry delay in milliseconds.
	 * 
	 * @param retryDelayMS in milliseconds
	 */
	public void setRetryDelay(int retryDelayMS)
	{
		this.retryDelayMS = retryDelayMS;
	}

	/**
	 * Set the retry delay in milliseconds.
	 * 
	 * @param retryDelayMS in milliseconds
	 */
	public T retryDelay(int retryDelayMS)
	{
		this.retryDelayMS = retryDelayMS;
		return self;
	}
	
	@Override
	public String toString() {
		return "url=" + url + ", accessKey=" + accessKey + ", maxTries="
				+ maxTries + ", retryDelayMS=" + retryDelayMS;
	}

	/**
	 * Execute a request to the Docmosis cloud based on contained settings.
     * 
	 * @return a response object giving status, possible error messages and optional
	 * document payload.
	 * 
	 * @throws Exception if a problem occurs invoking the service. 
	 */
	public abstract DocmosisCloudResponse execute() throws Exception;
	public abstract DocmosisCloudResponse execute(String url, String accessKey) throws Exception;
	public abstract DocmosisCloudResponse execute(String accessKey) throws Exception;

}
