package com.docmosis.sdk.file;

import com.docmosis.sdk.response.DocmosisCloudResponse;

/**
/**
 * This class encapsulates a response to a get file request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.  For example:
 * 
 * 
 * <pre>
 *  GetFileResponse getFile = FileStorage
 *                              .get()
 *                              .fileName(fileToGet)
 *                              .sendTo(outputFileOrStream)
 *                              .execute();
 *  if (getFile.hasSucceeded()) {
 *      //File saved to outputFileOrStream
 *  }
 * </pre>
 */
public class GetFileResponse extends DocmosisCloudResponse {

	public GetFileResponse() {
		super();
	}

	@Override
	public String toString() {
		return "GetFileResponse [Status=" + getStatus() + ", Short Message=" + getShortMsg()
				+ ", Long Message=" + getLongMsg() + "]";
	}
}
