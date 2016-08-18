package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;

public class PrivilegeModel {
	private final String id;
	private final String name;
	private final String roleId;
	private final LocalDateTime createTimestamp;
	public PrivilegeModel(String id,String name,String roleId,LocalDateTime createTimestamp) {
		this.id=id;
		this.name=name;
		this.roleId=roleId;
		this.createTimestamp=createTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getRoleId() {
		return roleId;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
}
