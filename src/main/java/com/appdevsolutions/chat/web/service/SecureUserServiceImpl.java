package com.appdevsolutions.chat.web.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.entity.UserStatus;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.model.SecureUserModel;

@Service("secureUserService")
public class SecureUserServiceImpl implements UserDetailsService {

	private static final Logger LOGGER=LoggerFactory.getLogger(SecureUserServiceImpl.class);
	
	@Autowired 
	UserRepository userDaoService;
	
	@Override
	public UserDetails loadUserByUsername(final String username)throws UsernameNotFoundException {
		try{
	    	final User user=userDaoService.selectByUsername(username);
    		LOGGER.info("creating custom user for spring security");
    		final boolean enabled=user.getStatus().equals(UserStatus.ACTIVE);
    		final boolean accountNonExpired=user.getStatus().equals(UserStatus.ACTIVE);
    		final boolean credentialsNonExpired=user.getStatus().equals(UserStatus.ACTIVE);
    		final boolean accountNonLocked=user.getStatus().equals(UserStatus.ACTIVE);
    		final Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
    		for(final Role role:user.getRoles()) {
    			authorities.add(new SimpleGrantedAuthority(role.getName()));
    		}
    		//final org.springframework.security.core.userdetails.User secureUser=new org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    		final UserDetails secureUser=new SecureUserModel(user.getId(), user.getUsername(),user.getPassword(), user.getName().toString(), user.getLastLoginTimestamp(), user.getMobileNumber(), user.getAddress(),accountNonExpired , accountNonLocked, credentialsNonExpired, enabled, authorities);
    		return secureUser;
		}catch(EntityNotFoundException userNotFoundException){
			LOGGER.error("User Not Found Error : ",userNotFoundException);
			final UsernameNotFoundException usernameNotFoundException=new UsernameNotFoundException(username);
    		LOGGER.error("User Not Found with username : "+username,usernameNotFoundException);
    		throw usernameNotFoundException; 
		}
	}

}
