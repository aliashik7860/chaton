package com.appdevsolutions.chat.web.model;

public class Response {
	private final String rejectedType;
	private final String rejectedValue;
	private final String code;
	private final String message;
	public Response(final String rejectedType, final String rejectedValue,final String code,final String message) {
		this.rejectedType=rejectedType;
		this.rejectedValue=rejectedValue;
		this.code=code;
		this.message=message;
	}
	public String getRejectedType() {
		return rejectedType;
	}
	public String getRejectedValue() {
		return rejectedValue;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("FailureBean [timestamp=");
		builder.append(", rejectedType=");
		builder.append(rejectedType);
		builder.append(", rejectedValue=");
		builder.append(rejectedValue);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}
}