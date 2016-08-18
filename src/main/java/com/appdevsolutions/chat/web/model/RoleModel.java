package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;
import java.util.Set;

public class RoleModel {
	private final String id;
	private final String name;
	private final String userId;
	private final Set<PrivilegeModel> privilegeModels;
	private final LocalDateTime createTimestamp;
	public RoleModel(String id,String name,String userId,Set<PrivilegeModel> privilegeModels,LocalDateTime createTimestamp) {
		this.id=id;
		this.name=name;
		this.userId=userId;
		this.privilegeModels=privilegeModels;
		this.createTimestamp=createTimestamp;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getUserId() {
		return userId;
	}
	public Set<PrivilegeModel> getPrivilegeModels() {
		return privilegeModels;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createTimestamp == null) ? 0 : createTimestamp.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((privilegeModels == null) ? 0 : privilegeModels.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleModel other = (RoleModel) obj;
		if (createTimestamp == null) {
			if (other.createTimestamp != null)
				return false;
		} else if (!createTimestamp.equals(other.createTimestamp))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (privilegeModels == null) {
			if (other.privilegeModels != null)
				return false;
		} else if (!privilegeModels.equals(other.privilegeModels))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RoleModel [id=" + id + ", name=" + name + ", userId=" + userId + ", privilegeModels=" + privilegeModels
				+ ", createTimestamp=" + createTimestamp + "]";
	}
	
}
