package com.docmosis.sdk.request.param;

import java.io.InputStream;

public class InputStreamParamType extends AbstractParamType {
	private InputStream value;

	public InputStreamParamType(InputStream file)
	{
		this.value = file;
	}
	
	public InputStream getValue() {
		return value;
	}

	public void setValue(InputStream value) {
		this.value = value;
	}

	@Override
	public InputStream inputStreamValue() {
		return value;
	}	
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}	
	
}
