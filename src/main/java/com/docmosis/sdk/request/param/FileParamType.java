package com.docmosis.sdk.request.param;

import java.io.File;

public class FileParamType extends AbstractParamType {
	private File value;

	public FileParamType(File file)
	{
		this.value = file;
	}
	
	public File getValue() {
		return value;
	}

	public void setValue(File value) {
		this.value = value;
	}

	@Override
	public File fileValue() {
		return value;
	}	
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}	
	
}
