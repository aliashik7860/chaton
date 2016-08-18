package com.appdevsolutions.chat.web.exception;

public class PrivilegeAlreadyExistException extends ChatOnBusinessException {
	private static final long serialVersionUID = 1L;
	private final String rejectType;
	private final String rejectValue;
	public PrivilegeAlreadyExistException(String message,String rejectType,String rejectValue) {
		super(message);
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
