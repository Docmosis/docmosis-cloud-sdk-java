package com.docmosis.sdk.request.param;

public class BooleanParamType extends AbstractParamType {
	private Boolean value;

	public BooleanParamType(Boolean value) 
	{
		this.value = value;
	}
	
	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean booleanValue() {
		return value;
	}
	
	@Override
	public String stringValue() {
		return value == null ? null : String.valueOf(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}	

}
