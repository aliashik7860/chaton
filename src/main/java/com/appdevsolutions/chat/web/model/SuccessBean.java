package com.appdevsolutions.chat.web.model;


public final class SuccessBean {
	private final String timestamp;
	private final String url;
	private final String messageDescription;
	public SuccessBean(final String timestamp,final String url,final String messageDescription) {
		this.timestamp=timestamp;
		this.url=url;
		this.messageDescription=messageDescription;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getUrl() {
		return url;
	}
	public String getMessageDescription() {
		return messageDescription;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SuccessBean [timestamp=");
		builder.append(timestamp);
		builder.append(", url=");
		builder.append(url);
		builder.append(", messageDescription=");
		builder.append(messageDescription);
		builder.append("]");
		return builder.toString();
	}
	
}
