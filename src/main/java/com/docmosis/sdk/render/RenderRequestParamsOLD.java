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

/**
 * This object holds the instructions and data for the render request.
 * 
 * See the Web Services Developer guide at @see <a href="https://www.docmosis.com/support">https://www.docmosis.com/support</a>
 * for details about the settings for the Render request.  The properties set in this class 
 * are parameters for the Render request.
 * 
 * Typically, you would use the Renderer class to get an instance of this class, then
 * set the specifics you require using method chaining:
 * 
 * <pre>
 *   RenderResponse response = Renderer
 *								.render()
 *								.templateName(TemplateName)
 *								.outputName(outputFileName)
 *								.sendTo(outputFileOrStream)
 *								.data(dataString)
 *								.execute();
 *   if (response.hasSucceeded()) {
 *		//File rendered and saved to outputFileOrStream
 *	 }
 *   ...
 * </pre>
 */
public class RenderRequestParamsOLD {

    
    private String templateName;
    private boolean isSystemTemplate = false;
    private String outputName;
    private String outputFormat;
    private boolean compressSingleFormat = false;
    private String storeTo;
    private String billingKey;
    private boolean devMode = false;
    private String data;
    private String requestId;
    private String mailSubject;
    private String mailBodyHtml;
    private String mailBodyText;
    private boolean mailNoZipAttachments = false;
    private String sourceId;
    private boolean stylesInText = false;
    private String passwordProtect;
    private boolean pdfArchiveMode = false;
    private String pdfWatermark;
    private boolean pdfTagged = false;
    private boolean ignoreUnknownParams = false;
    private String tags;

    
    
    //The following fields are part of the Docmosis Web Services API but have not 
    //been implemented in this SDK.
    
    /*
	 * If set to "y","yes" or "true", the streamed result will be base64 encoded.
	 * Note this only applies if the request includes (or implies) a “stream” result.
	 */
    //private String streamResultInResponse;

    

    public String getTemplateName() {
        return templateName;
    }

    /**
     * The name of the template to use. Template must have been uploaded previously 
     * with the template upload request or via the web console.
     * 
     * @param templateName the template to use for the render
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    /**
     * The name of the template to use. Template must have been uploaded previously 
     * with the template upload request or via the web console.
     * 
     * @param templateName the template to use for the render
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD templateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public boolean getIsSystemTemplate() {
        return isSystemTemplate;
    }

    /**
     * Indicate the template to use is a "system" template, meaning it is not 
     * one of the developer's templates.  Set this if advised by Docmosis support.
     * Defaults to false.
     * 
     * @param isSystemTemplate true to indicate a system template.
     */
    public void setIsSystemTemplate(boolean isSystemTemplate) {
        this.isSystemTemplate = isSystemTemplate;
    }
    
    /**
     * Indicate the template to use is a "system" template, meaning it is not 
     * one of the developer's templates.  Set this if advised by Docmosis support.
     * Defaults to false.
     * 
     * @param isSystemTemplate true to indicate a system template.
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD isSystemTemplate(boolean isSystemTemplate) {
        this.isSystemTemplate = isSystemTemplate;
        return this;
    }

    public String getOutputName() {
        return outputName;
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
        this.outputName = outputName;
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
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD outputName(String outputName) {
        this.outputName = outputName;
        return this;
    }

    public String getOutputFormat() {
        return outputFormat;
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
        this.outputFormat = outputFormat;
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
    public RenderRequestParamsOLD outputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
        return this;
    }

    public boolean getCompressSingleFormat() {
        return compressSingleFormat;
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
    public void setCompressSingleFormat(boolean compressSingleFormat) {
        this.compressSingleFormat = compressSingleFormat;
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
    public RenderRequestParamsOLD compressSingleFormat(boolean compressSingleFormat) {
        this.compressSingleFormat = compressSingleFormat;
        return this;
    }

    public String getStoreTo() {
        return storeTo;
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
        this.storeTo = storeTo;
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
    public RenderRequestParamsOLD storeTo(String storeTo) {
        this.storeTo = storeTo;
        return this;
    }

    public String getBillingKey() {
        return billingKey;
    }

    /**
     * Set the billingKey string.  This is simply a user-defined token string
     * that can be later reported against.  @see sourceId for an alternative
     * equivalent which is linked to available statistics.
     *  
     * @param billingKey the key to apply with some association with billing.
     */
    public void setBillingKey(String billingKey) {
        this.billingKey = billingKey;
    }
    
    /**
     * Set the billingKey string.  This is simply a user-defined token string
     * that can be later reported against.  @see sourceId for an alternative
     * equivalent which is linked to available statistics.
     *  
     * @param billingKey the key to apply with some association with billing.
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD billingKey(String billingKey) {
        this.billingKey = billingKey;
        return this;
    }

    public boolean getDevMode() {
        return devMode;
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
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
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
    public RenderRequestParamsOLD devMode(boolean devMode) {
        this.devMode = devMode;
        return this;
    }

    public String getData() {
        return data;
    }

    /**
     * Set the data for the render. The format of the string is either JSON 
     * and the structure of your data should match the template you are using.
     * 
     * @param data JSON or XML data.
     */
    public void setData(String data) 
    {
        this.data = data;
    }
    
    /**
     * Set the data for the render. The format of the string is either JSON 
     * and the structure of your data should match the template you are using.
     * 
     * @param data JSON or XML data.
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD data(String data) 
    {
        this.data = data;
        return this;
    }

    public String getRequestId() {
        return requestId;
    }

    /**
     * Any string you would like to use to identify this job. This string will be returned in responses
     * which can be useful when many documents are being rendered in parallel.
     * 
     * @param requestId a token to identify this job.
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
    
    /**
     * Any string you would like to use to identify this job. This string will be returned in responses
     * which can be useful when many documents are being rendered in parallel.
     * 
     * @param requestId a token to identify this job.
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    /**
     * Set the subject when sending email.
     * 
     * @param mailSubject the subject text
     */
    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }
    
    /**
     * Set the subject when sending email.
     * 
     * @param mailSubject the subject text
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD mailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
        return this;
    }

    public String getMailBodyHtml() {
        return mailBodyHtml;
    }

    /**
     * Set the email body in HTML.  This will deliver a html-content 
     * message.
     * 
     * @param mailBodyHtml the html version of the email.
     */
    public void setMailBodyHtml(String mailBodyHtml) {
        this.mailBodyHtml = mailBodyHtml;
    }
    
    /**
     * Set the email body in HTML.  This will deliver a html-content 
     * message.
     * 
     * @param mailBodyHtml the html version of the email.
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD mailBodyHtml(String mailBodyHtml) {
        this.mailBodyHtml = mailBodyHtml;
        return this;
    }

    public String getMailBodyText() {
        return mailBodyText;
    }

    /**
     * Set the email body in plain text.
     * 
     * @param mailBodyText the plain text message body.
     */
    public void setMailBodyText(String mailBodyText) {
        this.mailBodyText = mailBodyText;
    }

    public boolean getMailNoZipAttachments() {
        return mailNoZipAttachments;
    }

    /**
     * If this is set to true, any email attachments will be attached 
     * as individual files rather than as a single zip (when multiple formats are being used).
     * Defaults to false.
     * 
     * @param mailNoZipAttachments set to true to attach results as separate 
     * files when mutltiple files are being created.
     */
    public void setMailNoZipAttachments(boolean mailNoZipAttachments) {
        this.mailNoZipAttachments = mailNoZipAttachments;
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
    public RenderRequestParamsOLD mailNoZipAttachments(boolean mailNoZipAttachments) {
    	this.mailNoZipAttachments = mailNoZipAttachments;
        return this;
    }

    public String getSourceId() {
        return sourceId;
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
        this.sourceId = sourceId;
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
    public RenderRequestParamsOLD sourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public boolean getStylesInText() {
        return stylesInText;
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
     */
    public void setStylesInText(boolean stylesInText) {
        this.stylesInText = stylesInText;
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
    public RenderRequestParamsOLD stylesInText(boolean stylesInText) {
        this.stylesInText = stylesInText;
        return this;
    }

	public String getPasswordProtect()
	{
		return passwordProtect;
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
		this.passwordProtect = passwordProtect;
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
	public RenderRequestParamsOLD passwordProtect(String passwordProtect)
	{
		this.passwordProtect = passwordProtect;
		return this;
	}

	public boolean getPdfArchiveMode()
	{
		return pdfArchiveMode;
	}

	/**
	 * Create pdf documents in PDF-A mode for long term storage. Note this setting 
	 * disables certain PDF features such as password protection and external hyperlinks.
	 * Defaults to false.
	 * 
	 * @param pdfArchiveMode true will enable pdf archive mode.
	 */
	public void setPdfArchiveMode(boolean pdfArchiveMode)
	{
		this.pdfArchiveMode = pdfArchiveMode;
	}
	
	/**
	 * Create pdf documents in PDF-A mode for long term storage. Note this setting 
	 * disables certain PDF features such as password protection and external hyperlinks.
	 * Defaults to false.
	 * 
	 * @param pdfArchiveMode true will enable pdf archive mode.
	 * @return this request for method chaining
	 */
	public RenderRequestParamsOLD pdfArchiveMode(boolean pdfArchiveMode)
	{
		this.pdfArchiveMode = pdfArchiveMode;
		return this;
	}

	public String getPdfWatermark()
	{
		return pdfWatermark;
	}

	/**
	 * If specified, PDF documents will have the specified text added as a watermark across the document.
	 * 
	 * @param pdfWatermark the text to apply as a watermark
	 */
	public void setPdfWatermark(String pdfWatermark)
	{
		this.pdfWatermark = pdfWatermark;
	}
	
	/**
	 * If specified, PDF documents will have the specified text added as a watermark across the document.
	 * 
	 * @param pdfWatermark the text to apply as a watermark
	 * @return this request for method chaining
	 */
	public RenderRequestParamsOLD pdfWatermark(String pdfWatermark)
	{
		this.pdfWatermark = pdfWatermark;
		return this;
	}

	public boolean getPdfTagged()
	{
		return pdfTagged;
	}

	/**
	 * If specified, the PDF documents will have extra information inserted to 
	 * assist with low-vision readability tools. For example, alt-text for images 
	 * in becomes "readable" by reader programs.
	 * Defaults to false.
	 * 
	 * @param pdfTagged true to enable pdf-tagging
	 */
	public void setPdfTagged(boolean pdfTagged)
	{
		this.pdfTagged = pdfTagged;
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
	public RenderRequestParamsOLD pdfTagged(boolean pdfTagged)
	{
		this.pdfTagged = pdfTagged;
		return this;
	}
	

	
	
	public boolean getIgnoreUnknownParams() {
        return ignoreUnknownParams;
    }

    /**
     * If true, unknown parameters in the request are allowed and ignored. 
     * By default the render service will return an error if a parameter is 
     * specified that is not expected.
     * Defaults to false.
     * 
     * @param ignoreUnknownParams true to indicate a system template.
     */
    public void setIgnoreUnknownParams(boolean ignoreUnknownParams) {
        this.ignoreUnknownParams = ignoreUnknownParams;
    }
    
    /**
     * If true, unknown parameters in the request are allowed and ignored. 
     * By default the render service will return an error if a parameter is 
     * specified that is not expected.
     * Defaults to false.
     * 
     * @param ignoreUnknownParams true to indicate a ignore unknown parameters.
     * @return this request for method chaining
     */
    public RenderRequestParamsOLD ignoreUnknownParams(boolean ignoreUnknownParams) {
        this.ignoreUnknownParams = ignoreUnknownParams;
        return this;
    }
	
	
    public String getTags() {
        return tags;
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
        this.tags = tags;
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
    public RenderRequestParamsOLD tags(String tags) {
        this.tags = tags;
        return this;
    }


	@Override
	public String toString()
	{
		return toString(false);
	}
    
	public String toString(boolean includeData)
	{
		return "RenderRequestBase [templateName=" + templateName + ", isSystemTemplate="
				+ isSystemTemplate + ", outputName=" + outputName
				+ ", outputFormat=" + outputFormat + ", compressSingleFormat="
				+ compressSingleFormat + ", storeTo=" + storeTo
				+ ", billingKey=" + billingKey + ", devMode=" + devMode
				+ ", requestId=" + requestId
				+ ", mailSubject=" + mailSubject + ", mailBodyHtml="
				+ mailBodyHtml + ", mailBodyText=" + mailBodyText
				+ ", mailNoZipAttachments=" + mailNoZipAttachments
				+ ", sourceId=" + sourceId + ", stylesInText=" + stylesInText
				+ ", passwordProtect=" + passwordProtect
				+ ", pdfArchiveMode=" + pdfArchiveMode + ", pdfWatermark="
				+ pdfWatermark + ", pdfTagged=" + pdfTagged + ", " + super.toString() 
				+ (includeData ? ", data=" + data : "") 
				+ "]";
	}
}
