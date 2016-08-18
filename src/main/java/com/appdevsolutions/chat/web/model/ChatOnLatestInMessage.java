package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

public class ChatOnLatestInMessage {
	private final String userId;
	private final String username;
	private final LocalDateTime timestamp;
	public ChatOnLatestInMessage(String userId,String username,LocalDateTime timestamp) {
		this.userId=userId;
		this.username=username;
		this.timestamp=timestamp;
	}
	public String getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
}