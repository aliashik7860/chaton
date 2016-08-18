package com.appdevsolutions.chat.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import com.appdevsolutions.chat.dao.entity.Address;
import com.appdevsolutions.chat.dao.entity.Name;

public final class UserModel{
	private final String id;
	private final String username;
	private final Name name;
	private final String password;
	private final LocalDate dateOfBirth;
	private final LocalDateTime lastLogin;
	private final LocalDateTime createTimestamp;
	private final String mobileNumber;
	private final Address address;
	private final boolean live;
	private final Set<RoleModel> roleModels;
	public UserModel(final String id,final String username,final Name name, String password, LocalDate dateOfBirth, final LocalDateTime lastLogin,final LocalDateTime createTimestamp, final String mobileNumber,final Address address,final boolean live, Set<RoleModel> roleModels){
		this.id=id;
		this.username=username;
		this.name=name;
		this.password=password;
		this.dateOfBirth=dateOfBirth;
		this.lastLogin=lastLogin;
		this.createTimestamp=createTimestamp;
		this.mobileNumber=mobileNumber;
		this.address=address;
		this.live=live;
		this.roleModels=roleModels;
	}
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public Name getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public LocalDateTime getCreateTimestamp() {
		return createTimestamp;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public Address getAddress() {
		return address;
	}
	
	public boolean isLive() {
		return live;
	}
	
	public Set<RoleModel> getRoleModels() {
		return roleModels;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (live ? 1231 : 1237);
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		result = prime * result + ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roleModels == null) ? 0 : roleModels.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		UserModel other = (UserModel) obj;
		if (live != other.live)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roleModels == null) {
			if (other.roleModels != null)
				return false;
		} else if (!roleModels.equals(other.roleModels))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserModel [id=" + id + ", username=" + username + ", name=" + name + ", lastLogin=" + lastLogin
				+ ", mobileNumber=" + mobileNumber + ", address=" + address + ", live=" + live + ", roleModels="
				+ roleModels + "]";
	}
	
	
	
	
}