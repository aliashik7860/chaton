package com.appdevsolutions.chat.common.model;

import java.time.LocalDateTime;

public final class ProfilePhotoModel {
	private final String id;
	private final String userId;
	private final String name;
	private final ImageType contentType;
	private final long size;
	private final byte[] bytes;
	private final LocalDateTime createTimestamp;
	public ProfilePhotoModel(final String id,final String userId,final String name,final ImageType contentType, final long size,final byte[] bytes, final LocalDateTime createTimestamp) {
		this.id=id;
		this.userId=userId;
		this.name=name;
		this.contentType=contentType;
		this.size=size;
		this.bytes=bytes;
		this.createTimestamp=createTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	public ImageType getContentType() {
		return contentType;
	}
	public long getSize() {
		return size;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
}
