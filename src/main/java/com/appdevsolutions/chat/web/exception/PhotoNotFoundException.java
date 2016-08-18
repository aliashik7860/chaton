package com.appdevsolutions.chat.web.exception;

public class PhotoNotFoundException extends ChatOnBusinessException {
	
	private static final long serialVersionUID = 1L;
	private final String rejectType;
	private final String rejectValue;

	public PhotoNotFoundException(String code,String rejectType,String rejectValue) {
		super(code);
		this.rejectType=rejectType;
		this.rejectValue=rejectValue;
	}
	public String getRejectType() {
		return rejectType;
	}
	public String getRejectValue() {
		return rejectValue;
	}
}
