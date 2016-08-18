package com.appdevsolutions.chat.web.rest.validation.exception;

import com.appdevsolutions.chat.web.exception.ChatOnBusinessException;

public class ChatOnJsonParseException extends ChatOnBusinessException{

	private static final long serialVersionUID = 1L;
	private final String required;
	public ChatOnJsonParseException(String code,String required) {
		super(code);
		this.required=required;
	}
	public String getRequired() {
		return required;
	}
}
