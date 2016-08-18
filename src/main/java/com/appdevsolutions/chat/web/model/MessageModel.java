package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

public final class MessageModel{
	private final String id;
	private final String senderId;
	private final String receiverId;
	private final String message;
	private final LocalDateTime createTimestamp;
	public MessageModel(final String id,final String senderId,final String receiverId,final String message,final LocalDateTime createTimestamp) {
		this.id=id;
		this.senderId=senderId;
		this.receiverId=receiverId;
		this.message=message;
		this.createTimestamp=createTimestamp;
	}
	public String getId() {
		return id;
	}
	
	public String getSenderId() {
		return senderId;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
}