package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

public class EmailTemplateModel {
	private final String id;
	private final String name;
	private final String subject;
	private final String from;
	private final String body;
	private final LocalDateTime createTimestamp;
	private final LocalDateTime updateTimestamp;
	public EmailTemplateModel(final String id,final String name,final String subject,final String from,final String body,final LocalDateTime createTimestamp, final LocalDateTime updateTimestamp) {
		this.id=id;
		this.name=name;
		this.subject=subject;
		this.from=from;
		this.body=body;
		this.createTimestamp=createTimestamp;
		this.updateTimestamp=updateTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSubject() {
		return subject;
	}
	public String getFrom() {
		return from;
	}
	public String getBody() {
		return body;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public LocalDateTime getUpdateTimestamp() {
		return updateTimestamp;
	}
}