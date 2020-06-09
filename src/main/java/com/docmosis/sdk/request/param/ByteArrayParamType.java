package com.docmosis.sdk.request.param;

public class ByteArrayParamType extends AbstractParamType {
	private byte[] value;

	public ByteArrayParamType(byte[] file)
	{
		this.value = file;
	}
	
	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public byte[] byteArrayValue() {
		return value;
	}	
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}	
	
}
