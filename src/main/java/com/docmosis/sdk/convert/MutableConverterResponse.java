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
package com.docmosis.sdk.convert;

import com.docmosis.sdk.response.MutableResponseInterface;

/**
 * This class is used internally to facilitate building the response to a request.
 */
public class MutableConverterResponse extends ConverterResponse implements MutableResponseInterface
{
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public void setShortMsg(String shortMsg)
	{
		this.shortMsg = shortMsg;
	}
	
	public void setLongMsg(String longMsg)
	{
		this.longMsg = longMsg;
	}

	public void setTries(int tries)
	{
		this.tries = tries;
	}

	/**
	 * Set the previous failure information from the given response.
	 * 
	 * @param failureInfo the details of the failure.
	 */
	public void setPreviousFailureInformation(PreviousFailureInformation failureInfo)
	{
		this.previousFailureInformation = failureInfo;
	}

	public void setServerId(String serverId)
	{
		this.serverId = serverId;
	}

	public void setPages(int pages)
	{
		this.pagesConverted = pages;
	}

	public void setLength(long length)
	{
		this.length = length;
	}

	public ConverterResponse build() {
		return (ConverterResponse) this;
	}

	@Override
	public void setRequestId(String requestId) {
		throw new IllegalStateException("Not implemented");
	}
}
