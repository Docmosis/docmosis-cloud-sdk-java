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

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.file.DeleteFilesResponse;
import com.docmosis.sdk.file.FileException;
import com.docmosis.sdk.file.FileStorage;


/**
 * 
 * This example connects to the public Docmosis cloud server and deletes a 
 * file stored on the server.
 * 
 * Note that file storage must be enabled on your account for File services 
 * to work.
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
public class SimpleDeleteFileExample
{
	// you get an access key when you sign up to the Docmosis cloud service
	private static final String ACCESS_KEY = "XXX";

	//Full path of File to be deleted
	private static final String FILE_TO_DELETE = "myFile.pdf";

	public static void main(String args[]) throws FileException, IOException
	{
		
		if (ACCESS_KEY.equals("XXX")) {
			System.err.println("Please set your ACCESS_KEY");
			System.exit(1);
		}

		//Set the default environment to use your access key
		Environment.setDefaults(ACCESS_KEY);

		//Create and execute the request
		DeleteFilesResponse response = FileStorage
										.delete()
										.path(FILE_TO_DELETE)
										.execute();

		if (response.hasSucceeded()) {
			// great - request succeeded.
			System.out.println("Successfully deleted " + FILE_TO_DELETE);
		} else {
			// something went wrong, tell the user
			System.err.println("Delete file failed: status="
					+ response.getStatus()
					+ " shortMsg="
					+ response.getShortMsg()
					+ ((response.getLongMsg() == null) ? "" : " longMsg="
							+ response.getLongMsg()));
		}
	}
}
