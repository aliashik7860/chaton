package com.appdevsolutions.chat.web.exception;

public class AccessDeniedException extends ChatOnBusinessException{

	private static final long serialVersionUID = 1L;
	
	public AccessDeniedException(String code) {
		super(code);
	}

}
