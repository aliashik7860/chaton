package com.appdevsolutions.chat.web.service;

import java.util.Set;

import com.appdevsolutions.chat.web.exception.RoleAlreadyExistException;
import com.appdevsolutions.chat.web.exception.RoleNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.RoleModel;

public interface RoleService {
	public void removeById(String roleId) throws RoleNotFoundException;
	public void removeByName(String roleName) throws RoleNotFoundException;
	public void removeByUserId(String userId) throws RoleNotFoundException,UserNotFoundException;
	public Set<RoleModel> getAll();
	public RoleModel getById(String roleId) throws RoleNotFoundException;
	public Set<RoleModel> getByName(String roleName) throws RoleNotFoundException;
	public Set<RoleModel> getByUserId(String userId) throws RoleNotFoundException,UserNotFoundException;
	public void save(RoleModel role) throws UserNotFoundException,RoleAlreadyExistException;
	public void updateName(String id,String oldRoleName, String newRoleName) throws RoleNotFoundException,RoleAlreadyExistException;
}

