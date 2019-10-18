
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

import java.io.IOException;

import com.docmosis.sdk.environment.Endpoint;
import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.template.GetTemplateDetailsResponse;
import com.docmosis.sdk.template.Template;
import com.docmosis.sdk.template.TemplateDetails;
import com.docmosis.sdk.template.TemplateException;


/**
 * 
 * This example connects to the public Docmosis cloud server and returns 
 * details about a template stored on the server.
 * 
 * How to use:
 * 
 *  1) sign up to the Docmosis Cloud Services
 *  2) plug your ACCESS_KEY into this class
 *  3) run the class and see the result
 *  
 * You can find a lot more about the Docmosis conversion capability by reading
 * the Web Services Guide and the Docmosis Template guide in the support area
 * of the Docmosis web site (http://www.docmosis.com/support) 
 *  
 */
public class SimpleGetTemplateDetailsExample
{
	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = "XXX";
	private static final String TEMPLATE_FILE = "samples/WelcomeTemplate.docx";

	public static void main(String args[]) throws TemplateException, IOException
	{
		
		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}
		
		Environment.setDefaults(Endpoint.DWS_VERSION_3_AUS.getBaseUrl(), ACCESS_KEY);

		GetTemplateDetailsResponse templateDetails =  Template
														.getDetails()
														.templateName(TEMPLATE_FILE)
														.execute();

		if (templateDetails.hasSucceeded()) {
			TemplateDetails template = templateDetails.getTemplateDetails();
			System.out.println("Template Details:");
			System.out.println("Template Name: " + template.getName());
			System.out.println("Last Modified: " + template.getLastModifiedISO8601());
			System.out.println("Size: " + ((double)template.getSizeBytes() / 1000000.0) + " mb");
		} else {
			// something went wrong, tell the user
			System.err.println("Get Template Details failed: status="
					+ templateDetails.getStatus()
					+ " shortMsg="
					+ templateDetails.getShortMsg()
					+ ((templateDetails.getLongMsg() == null) ? "" : " longMsg="
							+ templateDetails.getLongMsg()));
		}
	}
}
