package com.docmosis.sdk.request.param;

import java.util.List;

public class StringListParamType extends AbstractParamType {
	
	private List<String> value;
	
	public StringListParamType(List<String> value)
	{
		this.value = value;
	}

	public List<String> getValue() {
		return value;
	}

	public void setValue(List<String> value) {
		this.value = value;
	}
	
	@Override
	public List<String> stringListValue() {
		return value;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		String prefix = "";
		if (value != null) {
			for (String s: value) {
				sb.append(prefix);
				prefix = "; ";
				sb.append(s);
			}
		}
		return sb.toString();
	}	
}
