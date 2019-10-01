package com.docmosis.sdk.request.param;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestParameters {

	private Map<String, ParamType> params = new LinkedHashMap<String, ParamType>();

	public List<String> getKeys()
	{
		return new ArrayList<String>(params.keySet());
	}
	
	public void setParam(String name, ParamType type) {
		params.put(name, type);
	}

	public ParamType getParam(String name) {
		return params.get(name);
	}

	public String getStringParam(String name) {
		final ParamType val = params.get(name);		
		return val == null ? null :val.stringValue();
	}

	public void setParam(String name, String value) {
		params.put(name, new StringParamType(value));
	}

	public Boolean getBooleanParam(String name) {
		final ParamType val = params.get(name);
		return val == null ? null :val.booleanValue();
	}

	public void setParam(String name, Boolean value) {
		params.put(name, new BooleanParamType(value));
	}

	public File getFileParam(String name) {
		final ParamType val = params.get(name);
		return val == null ? null :val.fileValue();
	}

	public void setParam(String name, File value) {
		params.put(name, new FileParamType(value));
	}

	@Override
	public String toString() {
		return toString(null);
	}
	
	public String toString(String[] excludeKeys) {
		StringBuilder sb = new StringBuilder();
		
		for(String key : params.keySet()) {
			boolean exclude = false;
			if (excludeKeys != null) {
				for(String ek : excludeKeys) {
					if (ek != null && ek.equals(key)) {
						exclude = true;
						break;
					}
				}
			}
			
			if (!exclude) {
				if (sb.length() > 0) {
					sb.append(", ");
				}
				final ParamType val = params.get(key);
				sb.append(key)
				  .append('=')
				  .append(val == null ? null : val.toString());
			}
		}
		
		
		return sb.toString();
	}

}
