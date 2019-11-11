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
package com.docmosis.sdk.render;

import com.docmosis.sdk.environment.Environment;
import com.docmosis.sdk.request.DocmosisCloudFileRequest;

/**
 * Abstract class containing the common parameters for a render and renderForm request.
 * @param <T> Request class
 */
public abstract class AbstractRenderRequest<T extends DocmosisCloudFileRequest<?>> extends DocmosisCloudFileRequest<T> {

	private AbstractRenderRequestParams params;

    public AbstractRenderRequest(final String servicePath, AbstractRenderRequestParams params) {
    	super(servicePath);
    	this.params = params;
    }

    public AbstractRenderRequest(final String servicePath, final Environment environment, AbstractRenderRequestParams params) {
    	super(servicePath, environment);
    	this.params = params;
    }
    
    public AbstractRenderRequestParams getParams()
    {
    	return params;
    }
    
    //The following fields are part of the Docmosis Web Services API but have not 
    //been implemented in this SDK.
    
    /*
	 * If set to true, the streamed result will be base64 encoded.
	 * Note this only applies if the request includes (or implies) a “stream” result.
	 */
    //private String streamResultInResponse;
    
    /**
     * The name of the template to use. Template must have been uploaded previously 
     * with the template upload request or via the web console.
     * 
     * @param templateName the template to use for the render
     * @return this request for method chaining
     */
    public T templateName(String templateName) {
    	params.setTemplateName(templateName);
        return getThis();
    }

    /**
     * Set the name to give the rendered document. If no format is specified ({@link #outputFormat}), 
     * the format of the resulting document is derived from the extension of this name. 
     * For example "resume1.pdf" implies a PDF format document. 
     * 
     * The name may be supplied without an extension (eg "resume1") and the outputFormat parameter 
     * will specify the format(s) to return.  In this case, the output name is really only used to name
     * the files inside a zip file result.
     * 
     * @param outputName the name of the result
     * @return this request for method chaining
     */
    public T outputName(String outputName) {
    	params.setOutputName(outputName);
        return getThis();
    }
    
    /**
     * Set the format(s) of the rendered document. ; delimited. 
     * Multiple formats imply a zip file and ouputName will have .zip appended as required. 
     * Files inside zip will be named using outputName and will have the format-specific extension 
     * appended as required. Valid options are pdf, doc, odt, rtf, html, txt.
     * 
     * @param outputFormat the output format specifier
     * @return this request for method chaining
     */
    public T outputFormat(String outputFormat) {
    	params.setOutputFormat(outputFormat);
        return getThis();
    }

    /**
     * Optionally choose to zip the result when a single output document is produced. The zip archive 
     * will contain a document in the specified format with a name based on 
     * outputName + outputFormat. The resulting zip file name will be the outputName with the .zip 
     * extension appended as required. This option is ignored if more than one outputFormat is 
     * specified.
     * Defaults to false.
     * 
     * @param compressSingleFormat set to true to compress
     * @return this request for method chaining
     */
    public T compressSingleFormat(boolean compressSingleFormat) {
    	params.setCompressSingleFormat(compressSingleFormat);
        return getThis();
    }

    /**
     * Specify where to send the resulting document(s). If no specification is given, 
     * "stream" is assumed and the result will be streamed back to the requester, 
     * otherwise the ; delimited list of destinations will receive the result.
     * Valid options are "stream", "mailto", "storage", "s3".
     * 
     * <pre>
     * Examples:
     *    stream  (stream back the result)
     *    mailto:me@abc.com  (email the result)
     *    stream;mailto:me@abc.com  (stream and email the result)
     *    stream:pdf;mailto:me@abc.com:doc  (stream the result in PDF format and email in DOC format)
     *    s3:my.amazon.bucket,mydocument.pdf  (store the result in the given bucket and key)
     * </pre>
     *  
     * @param storeTo the list of destinations.
     * @return this request for method chaining
     */
    public T storeTo(String storeTo) {
    	params.setStoreTo(storeTo);
        return getThis();
    }

    /**
     * Set the billingKey string.  This is simply a user-defined token string
     * that can be later reported against.  {@link #sourceId} for an alternative
     * equivalent which is linked to available statistics.
     *  
     * @param billingKey the key to apply with some association with billing.
     * @return this request for method chaining
     */
    public T billingKey(String billingKey) {
    	params.setBillingKey(billingKey);
        return getThis();
    }

    /**
     * Document production can run in development and production respectively. If set 
     * to true this operation will work in "dev" mode, meaning that if something is 
     * incorrect in the template, data or instructions Docmosis will do it's best to 
     * produce a document. Such a document may contain errors such as missing images 
     * and data, and wherever possible, Docmosis will highlight problems to indicate 
     * the failure. In production mode errors in document rendering will result in a 
     * failure result only and no document will be produced. The production mode is 
     * to ensure that a bad document is never produced/delivered to a recipient. The 
     * default mode is production (that is, dev mode is off).
     * 
     * @param devMode specify the dev/prod mode
     * @return this request for method chaining
     */
    public T devMode(boolean devMode) {
    	params.setDevMode(devMode);
        return getThis();
    }

    /**
     * Any string you would like to use to identify this job. This string will be returned in responses
     * which can be useful when many documents are being rendered in parallel.
     * 
     * @param requestId a token to identify this job.
     * @return this request for method chaining
     */
    public T requestId(String requestId) {
    	params.setRequestId(requestId);
        return getThis();
    }

    /**
     * Set the subject when sending email.
     * 
     * @param mailSubject the subject text
     * @return this request for method chaining
     */
    public T mailSubject(String mailSubject) {
    	params.setMailSubject(mailSubject);
        return getThis();
    }

    /**
     * Set the email body in HTML.  This will deliver a html-content 
     * message.
     * 
     * @param mailBodyHtml the html version of the email.
     * @return this request for method chaining
     */
    public T mailBodyHtml(String mailBodyHtml) {
    	params.setMailBodyHtml(mailBodyHtml);
        return getThis();
    }
    
    /**
     * Set the email body in plain text.
     * 
     * @param mailBodyText the plain text message body.
     * @return this request for method chaining
     */
    public T mailBodyText(String mailBodyText) {
    	params.setMailBodyText(mailBodyText);
        return getThis();
    }

    /**
     * If this is set to true, any email attachments will be attached 
     * as individual files rather than as a single zip (when multiple formats are being used).
     * Defaults to false.
     * 
     * @param mailNoZipAttachments set to true to attach results as separate 
     * files when mutltiple files are being created.
     * @return this request for method chaining
     */
    public T mailNoZipAttachments(boolean mailNoZipAttachments) {
    	params.setMailNoZipAttachments(mailNoZipAttachments);
        return getThis();
    }
    
    /**
     * Any ID you would like to associate with this render. This could be a 
     * device id, a project code or any meaningful piece of data you associate 
     * with this render. 
     * This value can be reported later with associated monthly counts. Limited to 150 characters.
     * 
     * @param sourceId a token to identify the render for statistics purposes.
     * @return this request for method chaining
     */
    public T sourceId(String sourceId) {
    	params.setSourceId(sourceId);
        return getThis();
    }

    /**
     * If set to true, your data will be parsed looking for html-like mark-up. The following mark-up is supported:
     * <ul>
     * <li>
     * Bold eg "this is <b>bold</b>"
     * </li><li>
     * Italics eg "this is <i>italics</i>"
     * </li><li>
     * Underline eg "this is <i>underline</i>"
     * </li><li>
     * Cell Colouring eg "&lt;bgcolor="#ff0000"/&gt;This cell is now red.
     * </li></ul>
     * The bgcolor tag must be at the beginning of your field data and the template field must 
     * be inside a table-cell to take effect. 
     * More information is available in the Docmosis Developer Guide.
     * Defaults to false.
     * 
     * @param stylesInText true to enable this style of processing.
     * @return this request for method chaining
     */
    public T stylesInText(boolean stylesInText) {
    	params.setStylesInText(stylesInText);
        return getThis();
    }

	/**
	 * If specified, this parameter will set the password for PDF and DOC files 
	 * created by the render. The password is used when opening the document. 
	 * Use with care as setting the password means the recipient must know the 
	 * password to read the document.
	 * <b>Note</b>: pdfArchiveMode will disable any password setting for PDF documents.
	 * 
	 * @param passwordProtect the open-password for the output documents
	 * @return this request for method chaining
	 */
	public T passwordProtect(String passwordProtect)
	{
		params.setPasswordProtect(passwordProtect);
		return getThis();
	}

	/**
	 * Create pdf documents in PDF-A mode for long term storage. Note this setting 
	 * disables certain PDF features such as password protection and external hyperlinks.
	 * Defaults to false.
	 * 
	 * @param pdfArchiveMode true will enable pdf archive mode.
	 * @return this request for method chaining
	 */
	public T pdfArchiveMode(boolean pdfArchiveMode)
	{
		params.setPdfArchiveMode(pdfArchiveMode);
		return getThis();
	}

	/**
	 * If specified, PDF documents will have the specified text added as a watermark across the document.
	 * 
	 * @param pdfWatermark the text to apply as a watermark
	 * @return this request for method chaining
	 */
	public T pdfWatermark(String pdfWatermark)
	{
		params.setPdfWatermark(pdfWatermark);
		return getThis();
	}

	/**
	 * If specified, the PDF documents will have extra information inserted to 
	 * assist with low-vision readability tools. For example, alt-text for images 
	 * in becomes "readable" by reader programs.
	 * Defaults to false.
	 * 
	 * @param pdfTagged true to enable pdf-tagging
	 * @return this request for method chaining
	 */
	public T pdfTagged(boolean pdfTagged)
	{
		params.setPdfTagged(pdfTagged);
		return getThis();
	}
	
    /**
     * If true, unknown parameters in the request are allowed and ignored. 
     * By default the render service will return an error if a parameter is 
     * specified that is not expected.
     * Defaults to false.
     * 
     * @param ignoreUnknownParams true to indicate ignore unknown parameters.
     * @return this request for method chaining
     */
    public T ignoreUnknownParams(boolean ignoreUnknownParams) {
    	params.setIgnoreUnknownParams(ignoreUnknownParams);
        return getThis();
    }

    /**
     * A semi-colon delimited list of tags to record against this render. 
     * The tags can be later queried (using the getRenderTags end point) 
     * to retrieve stats such as page-counts and document-counts related to 
     * the tags.
     * 
     * @param tags "list;of;tags;".
     * @return this request for method chaining
     */
    public T tags(String tags) {
    	params.setTags(tags);
        return getThis();
    }
}
