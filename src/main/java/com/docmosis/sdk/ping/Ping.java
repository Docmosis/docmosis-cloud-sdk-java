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
package com.docmosis.sdk.ping;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.environment.EnvironmentBuilder;
import com.docmosis.sdk.environment.InvalidEnvironmentException;

/**
 * This class manages the interaction with the Docmosis ping service endpoint.
 * This class can be used directly to ping the Docmosis endpoint to check that the Docmosis REST web
 * services are online and there is at least one Docmosis server listening.
 *  
 * <pre>
 *   Environment.setDefaults(Endpoint.DWS_VERSION_3_AUS.getBaseUrl(), ACCESS_KEY);
 *   System.out.println(Ping.execute());
 * </pre>
 *
 */
public class Ping {
	
    /**
     * The ping service provides a direct check that the Docmosis REST web services are online and there is at least one Docmosis server listening.
     * This is useful for diagnostics and for monitoring purposes.
     * @return true if at least one Docmosis server is listening.
     * @throws InvalidEnvironmentException if environment url is invalid
     *
     */
    public static boolean execute() throws InvalidEnvironmentException
    {
    	EnvironmentBuilder envBuilder = new EnvironmentBuilder(Environment.getDefaultEnvironment());
    	EnvironmentBuilder.validate(envBuilder, false);
    	return execute(envBuilder.getUrl("ping"));
    }
    
    /**
     * The ping service provides a direct check that the Docmosis REST web services are online and there is at least one Docmosis server listening.
     * This is useful for diagnostics and for monitoring purposes.
     * @param environment the set of environment properties to use
     * @return true if at least one Docmosis server is listening.
     * @throws InvalidEnvironmentException if environment url is invalid
     *
     */
    public static boolean execute(Environment environment) throws InvalidEnvironmentException
    {
    	EnvironmentBuilder.validate(new EnvironmentBuilder(environment));
    	return execute(Environment.getDefaultEnvironment().getUrl("ping"));
    }

    /**
     * The ping service provides a direct check that the Docmosis REST web services are online and there is at least one Docmosis server listening.
     * This is useful for diagnostics and for monitoring purposes.
     * @return true if at least one Docmosis server is listening.
     */   
    private static boolean execute(String url)
    {
    	try {
    		CloseableHttpClient client = HttpClients
    										.custom()
    										.build();
    		CloseableHttpResponse responseHttp = client.execute(new HttpPost(url));
    		return responseHttp.getStatusLine().getStatusCode() == 200;
    	} catch (Exception e) {
            return false;
        }	  
    }
}
