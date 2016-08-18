package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public final class OutMessageModel{
	private final String id;
	private final String userId;
	private final String username;
	private final String receiverId;
	private final String receiverUsername;
	private final String message;
	private LocalDateTime createTimestamp=LocalDateTime.now();
	
	@JsonCreator
	public OutMessageModel(final String id,@JsonProperty(value="userId")final String userId,@JsonProperty("username") final String username,@JsonProperty(value="receiverId")final String receiverId,@JsonProperty(value="receiverUsername")final String receiverUsername,@JsonProperty(value="message")final String message,final LocalDateTime createTimestamp) {
		this.id=id;
		this.userId=userId;
		this.username=username;
		this.receiverId=receiverId;
		this.receiverUsername=receiverUsername;
		this.message=message;
		this.createTimestamp=createTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getUsername() {
		return username;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public String getReceiverUsername() {
		return receiverUsername;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutboxModel [id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", receiverId=");
		builder.append(receiverId);
		builder.append(", message=");
		builder.append(message);
		builder.append(", createTimestamp=");
		builder.append(createTimestamp);
		builder.append("]");
		return builder.toString();
	}
	
}