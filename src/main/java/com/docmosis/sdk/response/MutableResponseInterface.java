package com.docmosis.sdk.response;

import com.docmosis.sdk.response.DocmosisCloudResponse.PreviousFailureInformation;

public interface MutableResponseInterface {
	public void setStatus(int status);
	public String getShortMsg();
	public void setShortMsg(String shortMsg);
	public String getLongMsg();
	public void setLongMsg(String longMsg);
	public void setTries(int tries);
	public void setPreviousFailureInformation(PreviousFailureInformation failureInfo);
	public void setServerId(String serverId);
	public DocmosisCloudResponse build();
	public void setRequestId(String requestId);
	public void setPages(int pages);
	public void setLength(long length);
}
