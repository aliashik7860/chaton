package com.appdevsolutions.chat.web.exception;

public class UserUpdatePasswordException extends ChatOnBusinessException {
	private static final long serialVersionUID = 1L;
	private final String rejectedType;
	private final String rejectedValue;
	public UserUpdatePasswordException(String message,String rejectedType, String rejectedValue) {
		super(message);
		this.rejectedType=rejectedType;
		this.rejectedValue=rejectedValue;
	}
	public String getRejectedType() {
		return rejectedType;
	}
	public String getRejectedValue() {
		return rejectedValue;
	}
}
