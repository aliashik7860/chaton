package com.appdevsolutions.chat.web.model;

public class JsonValidationResponse {
	private final String required;
	private final String message;
	public JsonValidationResponse(String required,String message) {
		this.required=required;
		this.message=message;
	}
	public String getRequired() {
		return required;
	}
	public String getMessage() {
		return message;
	}
}
