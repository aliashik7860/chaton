package com.appdevsolutions.chat.web.exception;

public class ChatOnBusinessException extends Throwable {

	private static final long serialVersionUID = -7724323681341674936L;
	private final String code;
	private final String message;
	public ChatOnBusinessException(String code) {
		this(code, null);
	}
	public ChatOnBusinessException(String code,Throwable cause) {
		super(null==cause? new Throwable("Cause not reported") : cause);
		this.code=code;
		this.message=ExceptionCodeHolder.getMessage(code);
	}

	public String getCode() {
		return code;
	}
	@Override
	public String getMessage() {
		return message;
	}
}