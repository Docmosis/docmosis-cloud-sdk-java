package com.docmosis.sdk.request.param;

public class StringParamType extends AbstractParamType {
	
	private String value;
	
	public StringParamType(String value)
	{
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String stringValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}	
	
	
}
