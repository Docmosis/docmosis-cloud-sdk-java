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
package com.docmosis.sdk.response;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * This class encapsulates a response to Docmosis cloud request.
 * 
 * Typically you would use this response to check for success, then decide what action to take.
 */
public class DocmosisCloudResponse
{
	protected int status;
	protected String shortMsg;
	protected String longMsg;
	protected String serverId;
	protected int tries;
	protected PreviousFailureInformation previousFailureInformation;

	protected DocmosisCloudResponse() {
		super();
	}

	protected DocmosisCloudResponse(DocmosisCloudResponse other) {
		super();
		this.status = other.status;
		this.shortMsg = other.shortMsg;
		this.longMsg = other.longMsg;
		this.serverId = other.serverId;
		this.tries = other.tries;
		this.previousFailureInformation = other.previousFailureInformation;
	}

	/**
	 * Determine if convert request succeeded.
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
	 * by calling the {@link #getShortMsg()} and {@link #getLongMsg()}.
	 * 
	 * @return the http status code (200 means successful)
	 */
	public int getStatus()
	{
		return status;
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

	/**
	 * Get details of the previous failure.  This is only available when
	 * getTries() returns &gt; 1
	 * 
	 * @return null if no information is available.
	 */
	public PreviousFailureInformation getPreviousFailureInformation()
	{
		return previousFailureInformation;
	}

	/**
	 * Get the id of the server used to execute the request.
	 * 
	 * @return null if unknown.
	 */
	public String getServerId()
	{
		return serverId;
	}
	
	/**
	 * Returns the specified Object as a Json object.
	 * @param input
	 * @return json object
	 */
	private JsonObject getAsJson(Object input)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").disableHtmlEscaping().create();
		String str = gson.toJson(input, input.getClass());
		JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
		return jsonObject;
	}

	/**
	 * Returns the specified Object as a formated Json string.
	 * @param input
	 * @return formatted string of json object
	 */
	private String getAsJsonPretty(Object input)
	{
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").disableHtmlEscaping().setPrettyPrinting().create();
		String str = gson.toJson(input, input.getClass());
		return str;
	}

	protected JsonObject getAsJson()
	{
		return getAsJson(this);
	}
	
	/**
	 * Returns this response as a formated Json string.
	 * @return formatted string of json object
	 */
	protected String getAsJsonPretty()
	{
		return getAsJsonPretty(this);
	}
	
	/**
	 * Returns this response as a formated xml string.
	 * @param xml unformatted xml string
	 * @return formatted string of xml object
	 */
	protected String getAsXMLPretty(String xml)
	{
		return getAsXMLPretty(xml, 4);
	}

	/**
	 * Returns object as a formated xml string.
	 * @param document xml document object
	 * @return formatted string of xml object
	 */
	protected String getAsXMLPretty(Document document)
	{
		return getAsXMLPretty(document, 4);
	}

	protected String getAsXMLPretty(String xml, int indent)
	{
		try {
	        // Turn xml string into a document
	        Document document = DocumentBuilderFactory.newInstance()
	                .newDocumentBuilder()
	                .parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
	        
	        // Remove whitespaces outside tags
	        document.normalize();
	        XPath xPath = XPathFactory.newInstance().newXPath();
	        NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",
	                                                      document,
	                                                      XPathConstants.NODESET);

	        for (int i = 0; i < nodeList.getLength(); ++i) {
	            Node node = nodeList.item(i);
	            node.getParentNode().removeChild(node);
	        }

	        return getAsXMLPretty(document, indent);
		} catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	protected String getAsXMLPretty(Document document, int indent)
	{
		try {
	        // Setup pretty print options
	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        transformerFactory.setAttribute("indent-number", indent);
	        Transformer transformer = transformerFactory.newTransformer();
	        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

	        // Return pretty print xml string
	        StringWriter stringWriter = new StringWriter();
	        transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
	        return stringWriter.toString();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	/**
	 * When the request is retried automatically due to a failure (eg communications), the details 
	 * of the (last) failure are held in an instance of this class.
	 */
	public static class PreviousFailureInformation
	{
		private final int status;
		private final String shortMsg;
		private final String longMsg;
		private final String serverId;

		public PreviousFailureInformation(int status, String shortMsg,
				String longMsg, String serverId)
		{
			this.status = status;
			this.shortMsg = shortMsg;
			this.longMsg = longMsg;
			this.serverId = serverId;
		}

		public int getStatus()
		{
			return status;
		}

		public String getShortMsg()
		{
			return shortMsg;
		}

		public String getLongMsg()
		{
			return longMsg;
		}

		public String getServerId()
		{
			return serverId;
		}

		@Override
		public String toString()
		{
			return "PreviousFailureInformation [status=" + status
					+ ", shortMsg=" + shortMsg + ", longMsg=" + longMsg
					+ ", serverId=" + serverId + "]";
		}
	}
		
}
