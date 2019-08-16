package com.docmosis.sdk.file;

import com.docmosis.sdk.response.DocmosisFileResponse;

public class GetFileResponse extends DocmosisFileResponse {

	public GetFileResponse() {
		super();
	}

	@Override
	public String toString() {
		return "GetFileResponse [Status=" + getStatus() + ", Short Message=" + getShortMsg()
				+ ", Long Message=" + getLongMsg() + "]";
	}
}
