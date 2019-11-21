package com.docmosis.sdk.request.param;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RequestParameters {

	private Map<String, ParamType> params = new LinkedHashMap<String, ParamType>();
	private String[] requiredParams;

	public RequestParameters(String[] requiredParams) {
		this.requiredParams = requiredParams;
	}
	public RequestParameters(RequestParameters requestParameters) {
		List<String> keys = requestParameters.getKeys();
		for (String k: keys) {
			setParam(k, requestParameters.getParam(k));
		}
		this.requiredParams = requestParameters.getRequiredParams();
	}
	public List<String> getKeys()
	{
		return new ArrayList<String>(params.keySet());
	}
	
	/**
     * Get list of param names that are required for the request
     * @return required params
     */
    public String[] getRequiredParams() {
        return requiredParams;
    }

    /**
     * Extends the object with the parameters and keys from requestParameters.
     * Note: If any existing parameters have the same key as requestParameters they will be overwritten.
     * @param requestParameters the object to extend from.
     */
    public void extend(RequestParameters requestParameters) {
    	//Extend required keys
    	if (requestParameters.getRequiredParams() == null) {}
    	else if (requiredParams == null) {
    		requiredParams = requestParameters.getRequiredParams();
    	}
    	else { //Join keys
    		if (requiredParams.length > 0 && requestParameters.getRequiredParams().length > 0){
    			String[] bothArrays = Arrays.copyOf(requiredParams, requiredParams.length + requestParameters.getRequiredParams().length);
    			System.arraycopy(requestParameters.getRequiredParams(), 0, bothArrays, requiredParams.length, requestParameters.getRequiredParams().length);
    			requiredParams = bothArrays;
    		}
    	}
    	//Add all params
    	List<String> keys = requestParameters.getKeys();
		for (String k: keys) {
			setParam(k, requestParameters.getParam(k));
		}
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
	
	public void setParam(String name, List<String> value) {
		if (params.containsKey(name)) {
			List<String> al = ((StringListParamType) params.get(name)).getValue();
			al.addAll(value);
			params.put(name, new StringListParamType(al));
		}
		else {
			params.put(name, new StringListParamType(value));
		}
	}
	
	/**
	 * Method for adding a String to a String List Param type
	 * @param name key
	 * @param value to add to String List
	 */
	public void addParam(String name, String value) {
		if (params.containsKey(name)) {
			List<String> lst = ((StringListParamType) params.get(name)).getValue();
			lst.add(value);
			params.put(name, new StringListParamType(lst));
		}
		else {
			params.put(name, new StringListParamType(value));
		}
	}
	
	public List<String> getStringListParam(String name) {
		final ParamType val = params.get(name);		
		return val == null ? null :val.stringListValue();
	}
	
	public void setParam(String name, Integer value) {
		params.put(name, new IntegerParamType(value));
	}
	
	public Integer getIntegerParam(String name) {
		final ParamType val = params.get(name);
		return val == null ? null :val.integerValue();
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
