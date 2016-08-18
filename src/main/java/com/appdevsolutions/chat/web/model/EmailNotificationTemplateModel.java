package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

public class EmailNotificationTemplateModel 
{
	private final String id;
	private final String userId;
	private final EmailTemplateModel emailTemplateModel;
	private final boolean active;
	private final LocalDateTime createTimestamp;
	private final LocalDateTime updateTimestamp;
	public EmailNotificationTemplateModel(String id,String userId,EmailTemplateModel emailTemplateModel,boolean active,LocalDateTime createTimestamp,LocalDateTime updateTimestamp) {
		this.id=id;
		this.userId=userId;
		this.emailTemplateModel=emailTemplateModel;
		this.active=active;
		this.createTimestamp=createTimestamp;
		this.updateTimestamp=updateTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public EmailTemplateModel getEmailTemplateModel() {
		return emailTemplateModel;
	}
	public boolean isActive() {
		return active;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}
}