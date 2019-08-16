/*
 *   Copyright 2012 Docmosis.com or its affiliates.  All Rights Reserved.
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
package com.docmosis.sdk.convert;

import com.docmosis.sdk.response.DocmosisFileResponse;

/**
 * This class encapsulates a response to a convert request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
 * 
 * <pre>
 * 
 *   RenderResponse response = rr.execute();
 *   try {  
 *     if (response.hasSucceeded()) {
 *       InputStream doc = response.getDocument();
 *       // do something with the doc
 *     } else {
 *       // read the error messages and status code to 
 *       // decide what to do - check error messages/tell user
 *     }
 *   } finally {
 *     response.cleanup();
 *   }
 * </pre>
 *
 * Since the response may contain an InputStream from the request, it is important that you 
 * have the finally block to cleanup.
 */
//TODO FIX JAVADOC above
public class ConverterResponse extends DocmosisFileResponse
{
	public static final String FIELD_HEADER_X_DOCMOSIS_BYTES_OUTPUT  = "X-Docmosis-BytesOutput";

	private int pagesConverted;
	private long length;
	
	
	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		
		cleanup();
	}
	
	/**
	 * Get the number of pages converted on success.
	 * 
	 * @return 0 if unknown
	 */
	public int getPagesConverted()
	{
		return pagesConverted;
	}

	public void setPagesConverted(int pagesConverted)
	{
		this.pagesConverted = pagesConverted;
	}
	
	public long getLength()
	{
		return length;
	}

	public void setLength(long length)
	{
		this.length = length;
	}

	@Override
	public String toString() {
		return "ConverterResponse [" + super.toString() + "pagesConverted=" + pagesConverted + 
				", length=" + length + "]";
	}
	
	
}
