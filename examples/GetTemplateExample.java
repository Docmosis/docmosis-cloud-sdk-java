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

import java.io.File;
import java.io.IOException;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.template.GetTemplateResponse;
import com.docmosis.sdk.template.Template;
import com.docmosis.sdk.template.TemplateException;


/**
 * 
 * This example connects to the public Docmosis cloud server and returns a 
 * template stored on the server. Note that multiple templates can be requested
 * and returned in a zip file.
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
public class GetTemplateExample
{
	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = "XXX";

	// Full path of Template to get
	private static final String TEMPLATE_TO_GET = "myTemplate.docx";

	public static void main(String args[]) throws TemplateException, IOException
	{

		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}

		//Set the default environment to use your access key
		Environment.setDefaults(ACCESS_KEY);

		//Set the file we are going to write the returned document to.
		File outputFile = new File(TEMPLATE_TO_GET);

		//Create and execute the request
		GetTemplateResponse response = Template
										.get()
										.templateName(TEMPLATE_TO_GET)
										.sendTo(outputFile)
										.execute();

		if (response.hasSucceeded()) {
			// great - request succeeded.
			System.out.println("Template written to: " + outputFile.getAbsolutePath());

		} else {
			// something went wrong, tell the user
			System.err.println("Get template failed: status="
					+ response.getStatus()
					+ " shortMsg="
					+ response.getShortMsg()
					+ ((response.getLongMsg() == null) ? "" : " longMsg="
							+ response.getLongMsg()));
		}
	}
}
