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

import java.io.File;
import java.io.IOException;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.image.GetImageResponse;
import com.docmosis.sdk.image.Image;


/**
 * 
 * This example connects to the public Docmosis cloud server and 
 * returns an image(s) stored on the Docmosis cloud server
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
public class SimpleGetImageExample
{
	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = Properties.accesskey;
	// If you are using our dws3 product please replace the URL below with the one specified
	// in the console under Account -> API URL.
	// If you are using dws2 in the EU:
	// private static final String URL = "https://eu-west.dws2.docmosis.com/services/rs/renderForm";
	private static final String URL = "https://dws2.docmosis.com/services/rs/getImage";
	//Full path of File to be uploaded
	private static final String FILE_TO_GET = "Image1.png";
	private static final String FILE_TO_GET2 = "Image2.jpg";

	public static void main(String args[]) throws DocmosisException, IOException
	{
		
		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}

		GetImageResponse image = null; //The response to the Get Template request.

		try {
			
			image = Image.get()
						.addImageName(FILE_TO_GET)
						//.addImageName(FILE_TO_GET2) // Can specify more than one file
						.execute(URL, ACCESS_KEY);

			if (image.hasSucceeded()) {
				File outputFile = new File(FILE_TO_GET);
				//File outputFile = new File("out.zip"); // If getting multiple templates they will be returned as a zip file.
				image.sendDocumentTo(outputFile);
				System.out.println("Output Image to: " + outputFile.getAbsolutePath());
			} else {
				// something went wrong, tell the user
				System.err.println("Get Image(s) failed: status="
						+ image.getStatus()
						+ " shortMsg="
						+ image.getShortMsg()
						+ ((image.getLongMsg() == null) ? "" : " longMsg="
								+ image.getLongMsg()));
			}
		} catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		} finally {
			//Close off document inputStream, http client and http response
			image.cleanup();
		}
	}
}
