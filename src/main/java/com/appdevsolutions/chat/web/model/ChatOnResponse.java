package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class ChatOnResponse <T>{
	private final LocalDateTime timestamp;
	private final String url;
	private final String statusCode;
	private final HttpStatus statusMessage;
	private final T response;
	private final ResponseStatus responseStatus;
	public ChatOnResponse(LocalDateTime timestamp,String url,String statusCode,HttpStatus statusMessage,ResponseStatus responseStatus,T response) {
		this.timestamp=timestamp;
		this.url=url;
		this.statusCode=statusCode;
		this.statusMessage=statusMessage;
		this.responseStatus=responseStatus;
		this.response=response;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public String getUrl() {
		return url;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public HttpStatus getStatusMessage() {
		return statusMessage;
	}
	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}
	public T getResponse() {
		return response;
	}
}