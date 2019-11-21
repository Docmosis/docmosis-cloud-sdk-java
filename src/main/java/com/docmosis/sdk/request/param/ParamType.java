package com.docmosis.sdk.request.param;

import java.io.File;
import java.util.List;

public interface ParamType {
	public String stringValue();
	public Boolean booleanValue();
	public File fileValue();
	public List<String> stringListValue();
	public Integer integerValue();
}
