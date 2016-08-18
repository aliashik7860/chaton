package com.appdevsolutions.chat.common.model;

public final class ErrorCodeModel {
	private final String code;
	private final String message;
	public ErrorCodeModel(final String code,final String message) {
		this.code=code;
		this.message=message;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	
}
