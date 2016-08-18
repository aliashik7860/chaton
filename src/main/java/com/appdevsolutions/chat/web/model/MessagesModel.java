package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;
import java.util.List;

public final class MessagesModel{
	private final String id;
	private final String senderId;
	private final List<String> receiverIds;
	private final String message;
	private final LocalDateTime createTimestamp;
	public MessagesModel(final String id,final String senderId,final List<String> receiverIds,final String message,final LocalDateTime createTimestamp) {
		this.id=id;
		this.senderId=senderId;
		this.receiverIds=receiverIds;
		this.message=message;
		this.createTimestamp=createTimestamp;
	}
	public String getId() {
		return id;
	}
	
	public String getSenderId() {
		return senderId;
	}
	public List<String> getReceiverIds() {
		return receiverIds;
	}
	public String getMessage() {
		return message;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
}