package com.appdevsolutions.chat.web.service;

import java.util.Set;

import com.appdevsolutions.chat.web.exception.PrivilegeAlreadyExistException;
import com.appdevsolutions.chat.web.exception.PrivilegeNotFoundException;
import com.appdevsolutions.chat.web.exception.RoleNotFoundException;
import com.appdevsolutions.chat.web.model.PrivilegeModel;

public interface PrivilegeService {
	public void removeById(String id) throws PrivilegeNotFoundException;
	public void removeByName(String name) throws PrivilegeNotFoundException;
	public void removeByRoleId(String roleId) throws PrivilegeNotFoundException,RoleNotFoundException;
	public Set<PrivilegeModel> getAll();
	public PrivilegeModel getById(String privilegeId) throws PrivilegeNotFoundException;
	public Set<PrivilegeModel> getByName(String name) throws PrivilegeNotFoundException;
	public Set<PrivilegeModel> getByRoleId(String roleId) throws PrivilegeNotFoundException,RoleNotFoundException;
	public void save(PrivilegeModel privilege) throws RoleNotFoundException,PrivilegeAlreadyExistException;
	public void updateName(String id,String oldPrivilegeName,String newPrivilegeName) throws PrivilegeNotFoundException,PrivilegeAlreadyExistException;
}
