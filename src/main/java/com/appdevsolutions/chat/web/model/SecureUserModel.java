package com.appdevsolutions.chat.web.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appdevsolutions.chat.dao.entity.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

public final class SecureUserModel implements UserDetails{
	private static final long serialVersionUID = 1L;
	private final String id;
	private final String username;
	private final String password;
	private final String name;
	private final LocalDateTime lastLogin;
	private final String mobileNumber;
	private final Address address;
	private final boolean accountNonExpired;
	private final boolean accountNonLocked;
	private final boolean credentialsNonExpired;
	private final boolean enabled;
	private final Collection<? extends GrantedAuthority> authorities;
	public SecureUserModel(final String id,final String username,final String password,final String name,final LocalDateTime lastLogin,final String mobileNumber,final Address address, final boolean accountNonExpired,final boolean accountNonLocked,final boolean crediatialsNonExpired, final boolean enabled,final Collection<? extends GrantedAuthority> authorities){
		this.id=id;
		this.username=username;
		this.password=password;
		this.name=name;
		this.lastLogin=lastLogin;
		this.mobileNumber=mobileNumber;
		this.address=address;
		this.accountNonExpired=accountNonExpired;
		this.accountNonLocked=accountNonLocked;
		this.credentialsNonExpired=crediatialsNonExpired;
		this.enabled=enabled;
		this.authorities=authorities;
	}
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getName() {
		return name;
	}
	public LocalDateTime getLastLogin() {
		return lastLogin;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public Address getAddress() {
		return address;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SecureUserModel)) {
			return false;
		}
		SecureUserModel user=(SecureUserModel)obj;
		if(id.equals(user.id)) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserModel [id=");
		builder.append(id);
		builder.append(", username=");
		builder.append(username);
		builder.append(", password=");
		builder.append(password);
		builder.append(", name=");
		builder.append(name);
		builder.append(", lastLogin=");
		builder.append(lastLogin);
		builder.append(", mobileNumber=");
		builder.append(mobileNumber);
		builder.append(", address=");
		builder.append(address);
		builder.append(", accountNonExpired=");
		builder.append(accountNonExpired);
		builder.append(", accountNonLocked=");
		builder.append(accountNonLocked);
		builder.append(", credentialsNonExpired=");
		builder.append(credentialsNonExpired);
		builder.append(", enabled=");
		builder.append(enabled);
		builder.append(", authorities=");
		builder.append(authorities);
		builder.append("]");
		return builder.toString();
	}
	
}