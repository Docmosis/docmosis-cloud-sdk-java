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
package com.docmosis.sdk.rendertags;

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * The object holds the parameters for a request to the Get Render Tags service.
 * See the Web Services Developer guide at <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the request.  The properties set in this class 
 * are parameters for the Render request.
 *
 */
public class GetRenderTagsRequestParams extends RequestParameters {
	
	private static final String TAGS				= "tags";
	private static final String YEAR				= "year";
	private static final String MONTH				= "month";
	private static final String N_MONTHS			= "nMonths";
	private static final String PAD_BLANKS			= "padBlanks";
	
	private static final String[] REQUIRED_PARAMS	= {TAGS};

	public GetRenderTagsRequestParams() {
		super(REQUIRED_PARAMS);
	}

	/**
	 * Get current tags to query.
	 * @return current tags
	 */
	public String getTags() {
		return getStringParam(TAGS);
	}

	/**
	 * The tags to query. This can be a single tag or a list of tags separated by the ; character.
	 * @param tags "list;of;tags;"
	 */
	public void setTags(String tags) {
		super.setParam(TAGS, tags);
	}
	
	/**
	 * Get the year on which to report statistics.
	 * @return year
	 */
	public Integer getYear() {
		return getIntegerParam(YEAR);
	}

	/**
	 * The year on which to report statistics. Defaults to the current year.
	 * @param year a valid year
	 */
	public void setYear(int year) {
		if (year > 2000 && year < 2050) {
			super.setParam(YEAR, year);
		}
	}
	
	/**
	 * Get the month on which to report statistics (1=Jan).
	 * @return month
	 */
	public Integer getMonth() {
		return getIntegerParam(MONTH);
	}

	/**
	 * The month on which to report statistics (1=Jan). Defaults to the current month.
	 * @param month a valid month
	 */
	public void setMonth(int month) {
		if (month > 0 && month < 13) {
			super.setParam(MONTH, month);
		}
	}
	
	/**
	 * Get the number of months on which to report statistics.
	 * @return number of months
	 */
	public Integer getnMonths() {
		return getIntegerParam(N_MONTHS);
	}

	/**
	 * The number of months on which to report statistics. Defaults to 1.
	 * If more than one month is being reported, the months prior to the given year
	 * and month are included. This means that this call always reports up to the
	 * specified month.
	 * @param nMonths number of months
	 */
	public void setNMonths(int nMonths) {
		if (nMonths > 0) {
			super.setParam(N_MONTHS, nMonths);
		}
	}

	/**
	 * If true zero values will be included where no data exists.
	 * @return pad blanks setting
	 */
	public Boolean getPadBlanks() {
		return getBooleanParam(PAD_BLANKS);
	}

	/**
	 * If true zero values will be included where no data exists.
	 * This may make parsing the returned result easier since it will always contain
	 * values for the tags requested over the given time period by padding the data
	 * with zero-values as required.
	 * Defaults to false.
	 * @param padBlanks value
	 */
	public void setPadBlanks(boolean padBlanks) {
		super.setParam(PAD_BLANKS, padBlanks);
	}
}
