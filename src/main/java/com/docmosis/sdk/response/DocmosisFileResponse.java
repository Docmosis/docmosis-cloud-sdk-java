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
package com.docmosis.sdk.response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class encapsulates a response to Docmosis cloud request with a file attachment.
 * 
 * Typically you would use this response to check for success, then decide what action to take.
 */
public class DocmosisFileResponse extends DocmosisCloudResponse{

	private InputStream document = null;
	
	/**
	 * Get the generated document.  
	 * <p>
	 * Note: calling {@link #cleanup()} in a finally block ensures this InputStream is closed.
	 * 
	 * @return the converted document stream
	 */
	public InputStream getDocument()
	{
		return document;
	}
	
	public void setDocument(InputStream document)
	{
		this.document = document;
	}
	
	/**
	 * Send the returned Document to the given file.  If there is no document, nothing is done.
	 * 
	 * NOTE: this method can only be used once since it exhausts the 
	 * InputStream returned.  If you need to send the document
	 * to multiple destinations after you have received it, you will need to 
	 * read it into memory or to a file first.
	 * 
	 * @param outputStream the destination to send the document
	 * @throws IOException if an IO problem occurs 
	 */
	public void sendDocumentTo(File outputFile) throws IOException
	{
		if (document != null) {
			FileOutputStream fout = new FileOutputStream(outputFile);
			try {
				sendDocumentTo(fout);
			} finally {
				fout.close();
			}
		}
	}
	
	/**
	 * Send the returned Document to the given outputStream. If there is no document, nothing is done.
	 * 
	 * NOTE: this method can only be used once since it exhausts the 
	 * InputStream returned.  If you need to send the document
	 * to multiple destinations after you have received it, you will need to 
	 * read it into memory or to a file first.
	 * 
	 * @param outputStream the destination to send the document
	 * 
	 * @throws IOException if an IO problem occurs
	 */
	public void sendDocumentTo(OutputStream outputStream) throws IOException
	{
		byte[] buffer = new byte[8192];
		int len;
		
		long count = 0;
		while((len = document.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
			count += len;
		}
		outputStream.flush();
		
		// input stream exhausted - might as well close it.
		document.close();
		document = null;
	}
	
	
	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();

		cleanup();
	}
	
	/**
	 * Free/close any resources held by this request (such as the input stream for the document).
	 */
	public void cleanup()
	{
		if (document != null) {
			try {
				document.close();
			} catch(IOException e) {
				// quietly ignore 
			}
			document = null;
		}
		super.cleanup();
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
	
}
