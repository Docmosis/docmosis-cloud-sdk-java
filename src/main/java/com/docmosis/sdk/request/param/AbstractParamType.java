package com.docmosis.sdk.request.param;

import java.io.File;

public class AbstractParamType implements ParamType {

	@Override
	public String stringValue() {
		throw new IllegalStateException("Not implemented");
	}

	@Override
	public Boolean booleanValue() {
		throw new IllegalStateException("Not implemented");
	}

	@Override
	public File fileValue() {
		throw new IllegalStateException("Not implemented");
	}

}
