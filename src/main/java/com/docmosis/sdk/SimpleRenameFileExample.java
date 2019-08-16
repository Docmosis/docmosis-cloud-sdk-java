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


import java.io.IOException;

import com.docmosis.sdk.file.File;
import com.docmosis.sdk.file.RenameFilesResponse;
import com.docmosis.sdk.handlers.DocmosisException;


/**
 * 
 * This example connects to the public Docmosis cloud server and 
 * renames a file stored on the Docmosis cloud server
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
public class SimpleRenameFileExample
{
	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = Properties.accesskey;
	// If you are using our dws3 product please replace the URL below with the one specified
	// in the console under Account -> API URL.
	// If you are using dws2 in the EU:
	// private static final String URL = "https://eu-west.dws2.docmosis.com/services/rs/renderForm";
	private static final String URL = "https://au.dws.docmosis.com/v3/api/renameFiles";
	//Full path of File/Folder to be renamed
	private static final String FILE_TO_RENAME = "myFile1.pdf";
	//Full path of new name for File/Folder
	private static final String NEW_NAME = "myOtherFile1.pdf";

	public static void main(String args[]) throws DocmosisException, IOException
	{
		
		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}

		RenameFilesResponse renamedFile = null; //The response to the Rename File request.
		
		try {
			
			renamedFile = File.rename()
							.fromPath(FILE_TO_RENAME)
							.toPath(NEW_NAME)
							.execute(URL, ACCESS_KEY);

			if (renamedFile.hasSucceeded()) {
				System.out.println("Successfully renamed \"" + FILE_TO_RENAME + "\" to \"" + NEW_NAME + "\"");
			} else {
				// something went wrong, tell the user
				System.err.println("Rename File failed: status="
						+ renamedFile.getStatus()
						+ " shortMsg="
						+ renamedFile.getShortMsg()
						+ ((renamedFile.getLongMsg() == null) ? "" : " longMsg="
								+ renamedFile.getLongMsg()));
			}
		} catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		} finally {
			//Close off http client and http response
			renamedFile.cleanup();
		}		
	}
}
