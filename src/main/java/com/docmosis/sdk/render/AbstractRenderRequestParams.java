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

import com.docmosis.sdk.request.param.RequestParameters;

/**
 * Abstract class containing the common parameters for a render and renderForm request.
 */
public abstract class AbstractRenderRequestParams extends RequestParameters {

    
    private static final String TEMPLATE_NAME             = "templateName";
    private static final String OUTPUT_NAME               = "outputName";
    private static final String OUTPUT_FORMAT             = "outputFormat";
    private static final String COMPRESS_SINGLE_FORMAT    = "compressSingleFormat";
    private static final String STORE_TO                  = "storeTo";
    private static final String BILLING_KEY               = "billingKey";
    private static final String DEV_MODE                  = "devMode";
    private static final String REQUEST_ID                = "requestId";
    private static final String MAIL_SUBJECT              = "mailSubject";
    private static final String MAIL_BODY_HTML            = "mailBodyHtml";
    private static final String MAIL_BODY_TEXT            = "mailBodyText";
    private static final String MAIL_NO_ZIP_ATTACHMENTS   = "mailNoZipAttachments";
    private static final String SOURCE_ID                 = "sourceId";
    private static final String STYLES_IN_TEXT            = "stylesInText";
    private static final String PASSWORD_PROTECT          = "passwordProtect";
    private static final String PDF_ARCHIVE_MODE          = "pdfArchiveMode";
    private static final String PDF_WATERMARK             = "pdfWatermark";
    private static final String PDF_TAGGED                = "pdfTagged";
    private static final String IGNORE_UNKNOWN_PARAMETERS = "ignoreUnknownParams";
    private static final String TAGS                      = "tags";
    
    private static final String[] REQUIRED_PARAMS         = {TEMPLATE_NAME};

	public AbstractRenderRequestParams() {
		super(REQUIRED_PARAMS);
	}
    
    //The following fields are part of the Docmosis Web Services API but have not 
    //been implemented in this SDK.
    
    /*
	 * If set to true, the streamed result will be base64 encoded.
	 * Note this only applies if the request includes (or implies) a “stream” result.
	 */
    //private String streamResultInResponse;


    public String getTemplateName() {
        return getStringParam(TEMPLATE_NAME);
    }

    /**
     * The name of the template to use. Template must have been uploaded previously 
     * with the template upload request or via the web console.
     * 
     * @param templateName the template to use for the render
     */
    public void setTemplateName(String templateName) {
        super.setParam(TEMPLATE_NAME, templateName);
    }

    public String getOutputName() {
        return getStringParam(OUTPUT_NAME);
    }

    /**
     * Set the name to give the rendered document. If no format is specified (@see #outputFormat), 
     * the format of the resulting document is derived from the extension of this name. 
     * For example "resume1.pdf" implies a PDF format document. 
     * 
     * The name may be supplied without an extension (eg "resume1") and the outputFormat parameter 
     * will specify the format(s) to return.  In this case, the output name is really only used to name
     * the files inside a zip file result.
     * 
     * @param outputName the name of the result
     */
    public void setOutputName(String outputName) {
        setParam(OUTPUT_NAME, outputName);
    }
    

    public String getOutputFormat() {
        return getStringParam(OUTPUT_FORMAT);
    }

    /**
     * Set the format(s) of the rendered document. ; delimited. 
     * Multiple formats imply a zip file and ouputName will have .zip appended as required. 
     * Files inside zip will be named using outputName and will have the format-specific extension 
     * appended as required. Valid options are pdf, doc, odt, rtf, html, txt.
     * 
     * @param outputFormat the output format specifier
     */
    public void setOutputFormat(String outputFormat) {
    	setParam(OUTPUT_FORMAT, outputFormat);
    }
    
    public Boolean getCompressSingleFormat() {
        return getBooleanParam(COMPRESS_SINGLE_FORMAT);
    }

    /**
     * Optionally choose to zip the result when a single output document is produced. The zip archive 
     * will contain a document in the specified format with a name based on 
     * outputName + outputFormat. The resulting zip file name will be the outputName with the .zip 
     * extension appended as required. This option is ignored if more than one outputFormat is 
     * specified.
     * Defaults to false.
     * 
     * @param compressSingleFormat set true to compress 
     */
    public void setCompressSingleFormat(Boolean compressSingleFormat) {
    	setParam(COMPRESS_SINGLE_FORMAT, compressSingleFormat);
    }
    
    public String getStoreTo() {
        return getStringParam(STORE_TO);
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
     */
    public void setStoreTo(String storeTo) {
    	setParam(STORE_TO, storeTo);
    }
    
    public String getBillingKey() {
        return getStringParam(BILLING_KEY);
    }

    /**
     * Set the billingKey string.  This is simply a user-defined token string
     * that can be later reported against.  @see sourceId for an alternative
     * equivalent which is linked to available statistics.
     *  
     * @param billingKey the key to apply with some association with billing.
     */
    public void setBillingKey(String billingKey) {
    	setParam(BILLING_KEY, billingKey);
    }
    
    public Boolean getDevMode() {
        return getBooleanParam(DEV_MODE);
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
     */
    public void setDevMode(Boolean devMode) {
    	setParam(DEV_MODE, devMode);
    }

    public String getRequestId() {
        return getStringParam(REQUEST_ID);
    }

    /**
     * Any string you would like to use to identify this job. This string will be returned in responses
     * which can be useful when many documents are being rendered in parallel.
     * 
     * @param requestId a token to identify this job.
     */
    public void setRequestId(String requestId) {
    	setParam(REQUEST_ID, requestId);
    }

    public String getMailSubject() {
        return getStringParam(MAIL_SUBJECT);
    }

    /**
     * Set the subject when sending email.
     * 
     * @param mailSubject the subject text
     */
    public void setMailSubject(String mailSubject) {
    	setParam(MAIL_SUBJECT, mailSubject);
    }

    public String getMailBodyHtml() {
        return getStringParam(MAIL_BODY_HTML);
    }

    /**
     * Set the email body in HTML.  This will deliver a html-content 
     * message.
     * 
     * @param mailBodyHtml the html version of the email.
     */
    public void setMailBodyHtml(String mailBodyHtml) {
    	setParam(MAIL_BODY_HTML, mailBodyHtml);
    }

    public String getMailBodyText() {
        return getStringParam(MAIL_BODY_TEXT);
    }

    /**
     * Set the email body in plain text.
     * 
     * @param mailBodyText the plain text message body.
     */
    public void setMailBodyText(String mailBodyText) {
    	setParam(MAIL_BODY_TEXT, mailBodyText);
    }

    public Boolean getMailNoZipAttachments() {
        return getBooleanParam(MAIL_NO_ZIP_ATTACHMENTS);
    }

    /**
     * If this is set to true, any email attachments will be attached 
     * as individual files rather than as a single zip (when multiple formats are being used).
     * Defaults to false.
     * 
     * @param mailNoZipAttachments set to true to attach results as separate 
     * files when mutltiple files are being created.
     */
    public void setMailNoZipAttachments(Boolean mailNoZipAttachments) {
    	setParam(MAIL_NO_ZIP_ATTACHMENTS, mailNoZipAttachments);
    }
    
    public String getSourceId() {
        return getStringParam(SOURCE_ID);
    }

    /**
     * Any ID you would like to associate with this render. This could be a 
     * device id, a project code or any meaningful piece of data you associate 
     * with this render. 
     * This value can be reported later with associated monthly counts. Limited to 150 characters.
     * 
     * @param sourceId a token to identify the render for statistics purposes.
     */
    public void setSourceId(String sourceId) {
    	setParam(SOURCE_ID, sourceId);
    }

    public Boolean getStylesInText() {
        return getBooleanParam(STYLES_IN_TEXT);
    }

    /**
     * If set to true, your data will be parsed looking for html-like mark-up. The following mark-up is supported:
     * <ul>
     * <li>
     * Bold eg "this is &lt;b&gt;bold&lt;/b&gt;"
     * </li><li>
     * Italics eg "this is &lt;i&gt;italics&lt;/i&gt;"
     * </li><li>
     * Underline eg "this is &lt;i&gt;underline&lt;/i&gt;"
     * </li><li>
     * Cell Colouring eg "&lt;bgcolor="#ff0000"/&gt;This cell is now red.
     * </li></ul>
     * The bgcolor tag must be at the beginning of your field data and the template field must 
     * be inside a table-cell to take effect. 
     * More information is available in the Docmosis Developer Guide.
     * Defaults to false.
     * 
     * @param stylesInText true to enable this style of processing.
     */
    public void setStylesInText(Boolean stylesInText) {
    	setParam(STYLES_IN_TEXT, stylesInText);
    }

	public String getPasswordProtect()
	{
        return getStringParam(PASSWORD_PROTECT);
	}

	/**
	 * If specified, this parameter will set the password for PDF and DOC files 
	 * created by the render. The password is used when opening the document. 
	 * Use with care as setting the password means the recipient must know the 
	 * password to read the document. 
	 * <b>Note</b>: pdfArchiveMode will disable any password setting for PDF documents.
	 * 
	 * @param passwordProtect the open-password for the output documents
	 */
	public void setPasswordProtect(String passwordProtect)
	{
		setParam(PASSWORD_PROTECT, passwordProtect);
	}

	public Boolean getPdfArchiveMode()
	{
        return getBooleanParam(PDF_ARCHIVE_MODE);
	}

	/**
	 * Create pdf documents in PDF-A mode for long term storage. Note this setting 
	 * disables certain PDF features such as password protection and external hyperlinks.
	 * Defaults to false.
	 * 
	 * @param pdfArchiveMode true will enable pdf archive mode.
	 */
	public void setPdfArchiveMode(Boolean pdfArchiveMode)
	{
		setParam(PDF_ARCHIVE_MODE, pdfArchiveMode);
	}

	public String getPdfWatermark()
	{
        return getStringParam(PDF_WATERMARK);
	}

	/**
	 * If specified, PDF documents will have the specified text added as a watermark across the document.
	 * 
	 * @param pdfWatermark the text to apply as a watermark
	 */
	public void setPdfWatermark(String pdfWatermark)
	{
		setParam(PDF_WATERMARK, pdfWatermark);
	}

	public Boolean getPdfTagged()
	{
        return getBooleanParam(PDF_TAGGED);
	}

	/**
	 * If specified, the PDF documents will have extra information inserted to 
	 * assist with low-vision readability tools. For example, alt-text for images 
	 * in becomes "readable" by reader programs.
	 * Defaults to false.
	 * 
	 * @param pdfTagged true to enable pdf-tagging
	 */
	public void setPdfTagged(Boolean pdfTagged)
	{
		setParam(PDF_TAGGED, pdfTagged);
	}
	
	public Boolean getIgnoreUnknownParams() {
        return getBooleanParam(IGNORE_UNKNOWN_PARAMETERS);
    }

    /**
     * If true, unknown parameters in the request are allowed and ignored. 
     * By default the render service will return an error if a parameter is 
     * specified that is not expected.
     * Defaults to false.
     * 
     * @param ignoreUnknownParams true to indicate ignore unknown parameters.
     */
    public void setIgnoreUnknownParams(Boolean ignoreUnknownParams) {
    	setParam(IGNORE_UNKNOWN_PARAMETERS, ignoreUnknownParams);
    }
	
    public String getTags() {
        return getStringParam(TAGS);
    }

    /**
     * A semi-colon delimited list of tags to record against this render. 
     * The tags can be later queried (using the getRenderTags end point) 
     * to retrieve stats such as page-counts and document-counts related to 
     * the tags.
     * 
     * @param tags "list;of;tags;".
     */
    public void setTags(String tags) {
    	setParam(TAGS, tags);
    }
}
