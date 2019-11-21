package com.docmosis.sdk.request.param;

public class IntegerParamType extends AbstractParamType {
	private Integer value;

	public IntegerParamType(Integer value) 
	{
		this.value = value;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public Integer integerValue() {
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
