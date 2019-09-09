package com.docmosis.sdk.file;

import com.docmosis.sdk.response.DocmosisCloudResponse;

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
