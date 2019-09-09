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
package com.docmosis.sdk;

import com.docmosis.sdk.environmentconfiguration.Endpoint;
import com.docmosis.sdk.environmentconfiguration.Environment;
import com.docmosis.sdk.environmentconfiguration.InvalidEnvironmentException;
import com.docmosis.sdk.ping.Ping;

/**
 * This example shows 2 methods for using the Docmosis Ping service.
 *
 */
public class SimplePingExample {

	public static void main(String args[]) throws InvalidEnvironmentException
	{
		Environment.setDefaults(Endpoint.DWS_VERSION_3_AUS.getBaseUrl(), "");
		if (Ping.execute()) {
			System.out.println(Environment.getDefaultEnvironment().getBaseUrl() + " is active.");
		}


		Environment environment = new Environment(Endpoint.DWS_VERSION_2_USA.getBaseUrl(), ""); 
		if (Ping.execute(environment)) {
			System.out.println(environment.getBaseUrl() + " is active.");
		}
	}
}
