package com.appdevsolutions.chat.web.exception;

public class MessageNotFoundException extends ChatOnBusinessException{

	private static final long serialVersionUID = 1L;
	private final String rejectType;
	private final String rejectValue;
	public MessageNotFoundException(String code,String rejectType,String rejectValue) {
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