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
package com.docmosis.sdk.environment;


/**
 * This class helps to build an Environment instance with settings for communicating 
 * with the desired end point.
 */
public class EnvironmentBuilder
{
	
	private MutableEnvironment env;
	
	public EnvironmentBuilder() {
		env = new MutableEnvironment();
	}		

	public EnvironmentBuilder(Environment other) {
		this.env = new MutableEnvironment(other);
	}		

	/**
	 * Set the access key for using the end point.  If using the public Docmosis Cloud Services
	 * you must sign up (at least for a trial period) to obtain your access key.
	 * Not required for connecting to Tornado.
	 * 
	 * @param accessKey your unique docmosis accesskey.
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setAccessKey(String accessKey) {
		env.setAccessKey(accessKey);
		return this;
	}


	/**
	 * Set the end point to which requests will be sent.
	 * 
	 * @param endpoint the end point for service requests
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setEndpoint(Endpoint endpoint)
	{
		env.setBaseUrl(endpoint.getBaseUrl());
		return this;
	}
	
	
	/**
	 * Set the base url of the request.
	 * Use the Endpoint enum to get the base url of the different Docmosis servers,
	 * eg Endpoint.DWS_VERSION_3_AUS.getBaseUrl().
	 * 
	 * @param baseUrl base url of the service endpoint
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setBaseUrl(String baseUrl) {
		env.setBaseUrl(baseUrl);
		return this;
	}

	/**
	 * Set the maximum number of tries that should be made to service this request 
	 * when a communications / server error occurs. 
	 * 
	 * @param maxTries the maximum number of attempts to connect to the web service.
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setMaxTries(int maxTries) {
		env.setMaxTries(maxTries);
		return this;
	}

	/**
	 * Set the retry delay in milliseconds.
	 * 
	 * @param retryDelayMS in milliseconds
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setRetryDelay(long retryDelayMS) {
		env.setRetryDelay(retryDelayMS);
		return this;
	}

	/**
	 * The maximum time to establish the connection with the remote host.
	 * 
	 * @param connectTimeoutMS in milliseconds
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setConnectTimeoutMS(long connectTimeoutMS) {
		env.setConnectTimeout(connectTimeoutMS);
		return this;
	}

	/**
	 * The maximum time waiting for data (in milliseconds) – after establishing the connection;
	 * maximum time of inactivity between two data packets.
	 * 
	 * @param readTimeoutMS in milliseconds
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setReadTimeoutMS(long readTimeoutMS) {
		env.setReadTimeout(readTimeoutMS);
		return this;
	}

	/**
	 * Set the proxy for use with the request.
	 * 
	 * @param proxy object containing proxy details
	 * @return this object for method chaining
	 */
	public EnvironmentBuilder setProxy(Proxy proxy) {
		env.setProxy(proxy);
		return this;
	}
	
    /**
     * Set the proxy with the given host, port and credentials.
     * 
     * @param host the hostname
     * @param port the port
     * @param user the user name for the proxy
     * @param passwd the password for the proxy
     * @return this object for method chaining
     */
	public EnvironmentBuilder setProxy(String host, int port, String user, String passwd) {
		env.setProxy(host, port, user, passwd);
		return this;
	}

	/**
	 * The access key for using the end point.
	 * 
	 * @return access key of docmosis cloud or tornado endpoint.
	 */
	public String getAccessKey() {
		return env.getAccessKey();
	}

	/**
     * The URL for the web service end point.
     * 
     * @param servicePath name of service being called
     * @return url of docmosis cloud or tornado endpoint.
     */
	public String getUrl(String servicePath) {
		return env.getUrl(servicePath);
	}

	/**
	 * Get the maximum number of tries that should be attempted to service
	 * this request when a server/network error occurs.
	 * 
	 * @return the maximum number of tries configured.
	 */
	public int getMaxTries() {
		return env.getMaxTries();
	}

	/**
	 * Get the retry delay (milliseconds) to apply when
	 * a retry is required.
	 * 
	 * @return the configured retry delay
	 */
	public long getRetryDelayMS()	{
		return env.getRetryDelayMS();
	}

	/**
	 * Get the client connection timeout (milliseconds) to establish 
	 * the connection with the remote host.
	 * 
	 * @return the configured client connection timeout
	 */
	public long getConnectTimeoutMS()	{
		return env.getConnectTimeoutMS();
	}

	/**
	 * Get the read timeout (milliseconds) between packets.
	 * 
	 * @return the configured read timeout
	 */
	public long getReadTimeoutMS()	{
		return env.getReadTimeoutMS();
	}

	/**
	 * Get the Proxy object containing proxy host, port and user credentials.
	 * 
	 * @return EnvironmentConfiguration.Proxy object
	 */
	public Proxy getProxy() {
		return env.getProxy();
	}
	
	public String getSdkVersion()
	{
		return env.getSdkVersion();
	}
	
	public String getOS()
	{
		return env.getOS();
	}

	public static void validate(EnvironmentBuilder env) throws InvalidEnvironmentException {
		validate(env, true);
	}
	
	public static void validate(EnvironmentBuilder env, boolean accessKeyMandatory) 
		throws InvalidEnvironmentException 
	{
		if (env == null) {
			throw new InvalidEnvironmentException("Environment not initialised");
		}
		Environment.validate(env.build(), accessKeyMandatory);
	}

	public static EnvironmentBuilder copyFrom(Environment other) {
		return new EnvironmentBuilder(other);
	}

	public static EnvironmentBuilder getDefaultEnvironment() {
		return copyFrom(Environment.getDefaultEnvironment());
	}
	
	public Environment build()
	{
		return (Environment)env;
	}

	@Override
	public String toString() {
		return env.toString();
	}

	private static class MutableEnvironment extends Environment
	{
		public MutableEnvironment() {}
		
		public MutableEnvironment(Environment other) {
			super(other);
		}

		/**
		 * Set the access key for using the end point.  If using the public Docmosis Cloud Services
		 * you must sign up (at least for a trial period) to obtain your access key.
		 * Not required for connecting to Tornado.
		 * 
		 * @param accessKey your unique docmosis accesskey.
		 * @return this object for method chaining
		 */
		public Environment setAccessKey(String accessKey) {
			super.accessKey = accessKey;
			return this;
		}

		/**
		 * Set the base url of the request.
		 * Use the Endpoint enum to get the base url of the different Docmosis servers,
		 * eg Endpoint.DWS_VERSION_3_AUS.getBaseUrl().
		 * 
		 * @param baseUrl base url of the service endpoint
		 * @return this object for method chaining
		 */
		public Environment setBaseUrl(String baseUrl) {
			super.baseUrl = baseUrl;
			return this;
		}

		/**
		 * Set the maximum number of tries that should be made to service this request 
		 * when a communications / server error occurs. 
		 * 
		 * @param maxTries the maximum number of attempts to connect to the web service.
		 * @return this object for method chaining
		 */
		public Environment setMaxTries(int maxTries) {
			super.maxTries = maxTries;
			return this;
		}
		
		/**
		 * Set the retry delay in milliseconds.
		 * 
		 * @param retryDelayMS in milliseconds
		 * @return this object for method chaining
		 */
		public Environment setRetryDelay(long retryDelayMS) {
			super.retryDelayMS = retryDelayMS;
			return this;
		}

		/**
		 * Set the client connection timeout in milliseconds to establish 
		 * the connection with the remote host.
		 * 
		 * @param connectTimeoutMS in milliseconds
		 * @return this object for method chaining
		 */
		public Environment setConnectTimeout(long connectTimeoutMS) {
			super.connectTimeoutMS = connectTimeoutMS;
			return this;
		}

		/**
		 * The maximum time waiting for data (in milliseconds) – after establishing the connection;
		 * maximum time of inactivity between two data packets.
		 * 
		 * @param readTimeoutMS in milliseconds
		 * @return this object for method chaining
		 */
		public Environment setReadTimeout(long readTimeoutMS) {
			super.readTimeoutMS = readTimeoutMS;
			return this;
		}

		/**
		 * Set the proxy for use with the request.
		 * 
		 * @param proxy object containing proxy details
		 * @return this object for method chaining
		 */
		public Environment setProxy(Proxy proxy) {
			super.proxy = proxy;
			return this;
		}

		/**
	     * Set the proxy with the given host, port and credentials.
	     * 
	     * @param host the hostname
	     * @param port the port
	     * @param user the user name for the proxy
	     * @param passwd the password for the proxy
	     * @return this object for method chaining
	     */
		public Environment setProxy(String host, int port, String user, String passwd) {
			super.proxy = new Proxy(host, port, user, passwd);
			return this;
		}
	}
}