package com.appdevsolutions.chat.web.model;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadBean {
	private final String userId;
	private final String receiverId;
	private final MultipartFile multipartFile;
	public FileUploadBean(final String userId, final String receiverId, final MultipartFile multipartFile) {
		this.userId=userId;
		this.receiverId=receiverId;
		this.multipartFile=multipartFile;
	}
	public String getUserId() {
		return userId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FileUploadBean [userId=");
		builder.append(userId);
		builder.append(", receiverId=");
		builder.append(receiverId);
		builder.append(", multipartFile=");
		builder.append(multipartFile);
		builder.append("]");
		return builder.toString();
	}
	public String getReceiverId() {
		return receiverId;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
}
