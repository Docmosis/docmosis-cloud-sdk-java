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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.environment.EnvironmentBuilder;
import com.docmosis.sdk.environment.Proxy;
import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
 * The object holds the common instructions and data for a request to the Docmosis cloud.
 * This is an abstract super object with the minimum requirements to make a request.
 * @param <T> Request class
 */
public abstract class DocmosisCloudRequest<T> {
	
	private final String servicePath;
	private EnvironmentBuilder environmentBuilder;

    public DocmosisCloudRequest(final String servicePath) {
    	this(servicePath, Environment.getDefaultEnvironment());
    }
    
    public DocmosisCloudRequest(final String servicePath, final Environment environment) {
    	this.servicePath = servicePath;
    	
    	this.environmentBuilder = EnvironmentBuilder.copyFrom(environment);
    }

    public void setEnvironment(Environment environment) {
    	this.environmentBuilder = EnvironmentBuilder.copyFrom(environment);
    }

    
    public EnvironmentBuilder getEnvironment() {
		return environmentBuilder;
	}

	/**
     * The URL for the web service end point.
     * 
     * @return url of docmosis cloud or tornado endpoint.
     */
	public String getUrl() {
		return environmentBuilder.getUrl(servicePath);
	}

	/**
	 * The access key for using the end point.
	 * 
	 * @return access key of docmosis cloud or tornado endpoint.
	 */
	public String getAccessKey()
	{
		return environmentBuilder.getAccessKey();
	}

	/**
	 * Get the maximum number of tries that should be attempted to service
	 * this request when a server/network error occurs.
	 * 
	 * @return the maximum number of tries configured.
	 */
	public int getMaxTries()
	{
		return environmentBuilder.getMaxTries();
	}

	/**
	 * Get the retry delay (milliseconds) to apply when
	 * a retry is required.
	 * 
	 * @return the configured retry delay
	 */
	public long getRetryDelay()
	{
		return environmentBuilder.getRetryDelayMS();
	}
	
	/**
	 * Get the client connection timeout (milliseconds) to establish 
	 * the connection with the remote host.
	 * 
	 * @return the configured client connection timeout
	 */
	public long getConnectTimeout()
	{
		return environmentBuilder.getConnectTimeoutMS();
	}

	/**
	 * Get the read timeout (milliseconds) between packets.
	 * 
	 * @return the configured read timeout
	 */
	public long getReadTimeout()
	{
		return environmentBuilder.getReadTimeoutMS();
	}

	/**
	 * Get the Proxy object containing proxy host, port and user credentials.
	 * 
	 * @return EnvironmentConfiguration.Proxy object
	 */
	public Proxy getProxy() {
		return environmentBuilder.getProxy();
	}
	
	@Override
	public String toString() {
		return "servicePath=" + servicePath + ", environment=" + environmentBuilder.toString();
	}


	/**
	 * Execute a request to the Docmosis cloud based on contained settings.
     * 
	 * @return a response object giving status, possible error messages and optional
	 * document payload.
	 * 
	 * @throws DocmosisException if a problem occurs invoking the service. 
	 */
	public abstract DocmosisCloudResponse execute() throws DocmosisException;
	public abstract DocmosisCloudResponse execute(String url, String accessKey) throws DocmosisException;
	public abstract DocmosisCloudResponse execute(String accessKey) throws DocmosisException;
	public abstract DocmosisCloudResponse execute(Environment env) throws DocmosisException;
	

}
