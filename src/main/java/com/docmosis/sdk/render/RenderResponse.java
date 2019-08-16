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
package com.docmosis.sdk.render;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;

import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;

/**
 * This class encapsulates a response to a render request.
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
public class RenderResponse
{
	private int status;
	private String requestId;
	private String shortMsg;
	private String longMsg;
	private InputStream document;
	private int pagesRendered;
	private String serverId;
	private int tries;
	private PreviousFailureInformation prevFailureInfo;
	private CloseableHttpClient clientHTTP = null;
	private CloseableHttpResponse responseHttp = null;
	
	/**
	 * Determine if render request succeeded.
	 * If the request succeeds AND the "stream" destination
	 * was part of the render request the document can be
	 * retrieved by calling {@link #getDocument()}. 
	 * 
	 * @return true if the request was successful
	 */
	public boolean hasSucceeded()
	{
		return status == 200;
	}
	
	/**
	 * Get the status code returned by the service.  This corresponds
	 * to HTTP status codes:
	 * <pre>
	 *   200 = success
	 *   4xx = client error (eg 404)
	 *   5xx = server error (eg 500)
	 * </pre>
	 * If this value is not a 200, then more information will be available
	 * by calling the {@link #getShortErrorMsg()} and {@link #getLongErrorMsg()}.
	 * 
	 * @return the http status code (200 means successful)
	 */
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	/**
	 * If an error has occurred (see {@link #hasSucceeded()}) then this will
	 * give a brief reason.
	 * 
	 * @return null if there is no message to report
	 */
	public String getShortMsg()
	{
		return shortMsg;
	}
	
	public void setShortMsg(String shortMsg)
	{
		this.shortMsg = shortMsg;
	}
	
	/**
	 * If an error has occurred (see {@link #hasSucceeded()}) then this will
	 * give a more detailed reason where possible
	 * 
	 * @return null if there is no message to report
	 */
	public String getLongMsg()
	{
		return longMsg;
	}
	
	public void setLongMsg(String longMsg)
	{
		this.longMsg = longMsg;
	}
	
	/**
	 * Get the generated document.  This method only applies where the "stream"
	 * destination was requested in the related render request.  For example, if the
	 * only destination was a "mailto" then no document is expected to be returned by
	 * this call. 
	 * <p>
	 * Note: calling {@link #cleanup()} in a finally block ensures this InputStream is closed.
	 * 
	 * @return null if there is no document delivered back by the request
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
	 * If the requestId was set in the render, it will be returned in this response.
	 * This helps asynchronous processing determine which response relates to which request.
	 *  
	 * @return null if not set
	 */
	public String getRequestId()
	{
		return requestId;
	}

	public void setRequestId(String requestId)
	{
		this.requestId = requestId;
	}
	
	
	/** 
	 * Determine the number of tries done before
	 * success or failure.
	 * 
	 * @return the number of tries.
	 */
	public int getTries()
	{
		return tries;
	}

	public void setTries(int tries)
	{
		this.tries = tries;
	}

	/**
	 * Get details of the previous failure.  This is only available when
	 * getTries() return > 1
	 * 
	 * @return null if no information is available.
	 */
	public PreviousFailureInformation getPreviousFailureInformation()
	{
		return prevFailureInfo;
	}

	/**
	 * Set the previous failure information from the given response.
	 * 
	 * @param failureInfo the details of the failure.
	 */
	public void setPreviousFailureInformation(PreviousFailureInformation failureInfo)
	{
		this.prevFailureInfo = failureInfo;
	}
	
	public void setClientHTTP(CloseableHttpClient clientHTTP) {
		this.clientHTTP = clientHTTP;
	}

	public void setResponseHttp(CloseableHttpResponse responseHttp) {
		this.responseHttp = responseHttp;
	}

	/**
	 * Send the rendered Document to the given file.  If there is no document, nothing is done.
	 * 
	 * NOTE: this method can only be used once since it exhausts the 
	 * InputStream returned by the render.  If you need to send the document
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
	 * Send the rendered Document to the given outputStream. If there is no document, nothing is done.
	 * 
	 * NOTE: this method can only be used once since it exhausts the 
	 * InputStream returned by the render.  If you need to send the document
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
		
		// input stream exhasted - might as well close it.
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
	 * Get the number of pages rendered on success.
	 * 
	 * @return 0 if unknown
	 */
	public int getPagesRendered()
	{
		return pagesRendered;
	}

	public void setPagesRendered(int pagesRendered)
	{
		this.pagesRendered = pagesRendered;
	}

	/**
	 * Get the id of the server used to execute the render.
	 * 
	 * @return null if unknown.
	 */
	public String getServerId()
	{
		return serverId;
	}

	public void setServerId(String serverId)
	{
		this.serverId = serverId;
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
		if (clientHTTP != null) {
			try {
				clientHTTP.close();
			} catch(IOException e) {
				// quietly ignore 
			}
			clientHTTP = null;
		}
		if (responseHttp != null) {
			try {
				responseHttp.close();
			} catch(IOException e) {
				// quietly ignore 
			}
			responseHttp = null;
		}
	}
}
