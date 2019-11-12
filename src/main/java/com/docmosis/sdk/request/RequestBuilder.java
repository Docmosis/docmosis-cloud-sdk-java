package com.docmosis.sdk.request;

import java.io.File;
import java.util.List;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.docmosis.sdk.handlers.DocmosisException;
import com.docmosis.sdk.request.param.FileParamType;
import com.docmosis.sdk.request.param.ParamType;
import com.docmosis.sdk.request.param.RequestParameters;
import com.docmosis.sdk.request.param.StringListParamType;

/**
 * Class to facilitate building a request.
 *
 */
public class RequestBuilder {
	
	private static final ContentType TEXT_CONTENT_TYPE = ContentType.create(ContentType.TEXT_PLAIN.getMimeType(), Charsets.UTF_8);

	/**
	 * Builds a multipart/form-data request.
	 * 
	 * @param accessKey Docmosis accessKey to combine into the request
	 * @param params list of parameters to combine into the request
	 * @return http entity object
	 * @throws DocmosisException if adding a File param that can't be read
	 */
	public static HttpEntity buildMultiPartRequest(String accessKey, RequestParameters params) throws DocmosisException {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();

		testRequiredParams(params);
		
		//Add access key first
		if (accessKey != null) {
			addField(builder, "accessKey", accessKey);
		}
		
		//Add all parameters
		if (params != null) {
	        for(String paramName : params.getKeys()) {
	    		ParamType value = params.getParam(paramName);
	    		addField(builder, paramName, value);
	        }
        }

        return builder.build();
	}
	
	/**
	 * Builds a multipart/form-data request with only an accesskey.
	 * 
	 * @param accessKey Docmosis accessKey to combine into the request
	 * @return http entity object
	 */
	public static HttpEntity buildMultiPartRequest(String accessKey) {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		
		//Add access key
		if (accessKey != null) {
			addField(builder, "accessKey", accessKey);
		}

        return builder.build();
	}
	
	private static void addField(MultipartEntityBuilder builder, String key, ParamType param) throws DocmosisException {
		if (param != null) {
			if (param instanceof FileParamType) {
				addField(builder, key, ((FileParamType) param).getValue());
			}
			else if (param instanceof StringListParamType) {
				addField(builder, key, ((StringListParamType) param).getValue());
			}
			else {
				addField(builder, key, param.stringValue());
			}
		}
	}

	private static void addField(MultipartEntityBuilder builder, String key, String param) {
		builder.addTextBody(key, param, TEXT_CONTENT_TYPE);
	}

	private static void addField(MultipartEntityBuilder builder, String key, List<String> param) {
		for (String s : param) {
			builder.addTextBody(key, s, TEXT_CONTENT_TYPE);
		}
	}

	private static void addField(MultipartEntityBuilder builder, String key, File param) throws DocmosisException {
		if (param.canRead()) {
			builder.addBinaryBody(key, param, ContentType.APPLICATION_OCTET_STREAM, param.getName());
		} else {
			throw new DocmosisException("cannot read " + key + ": [" + param + "]");
		}
	}

	/**
	 * Checks that all required parameters exist within params object.
	 * Throws Exception if any are missing.
	 * @param params request parameters
	 * @throws DocmosisException if any required request parameters are missing
	 */
	public static void testRequiredParams(RequestParameters params) throws DocmosisException {
		StringBuilder sb = new StringBuilder();
		for (String s : params.getRequiredParams()) {
			if (!params.getKeys().contains(s)) {
				if (sb.length() == 0) {
					sb.append(s);
				}
				else {
					sb.append(", ").append(s);
				}
				
			}
		}
		if (sb.length() != 0) {
			throw new DocmosisException("Missing required parameters: " + sb.toString());
		}
	}
}
