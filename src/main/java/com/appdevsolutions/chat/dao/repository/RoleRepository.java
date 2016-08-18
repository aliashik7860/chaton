package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface RoleRepository {
	public void deleteById(String id) throws EntityNotFoundException;
	public void deleteByName(String name) throws EntityNotFoundException;
	public void deleteByUser(User user) throws EntityNotFoundException;
	public List<Role> selectAll();
	public Role selectById(String id) throws EntityNotFoundException;
	public List<Role> selectByName(String name) throws EntityNotFoundException;
	public List<Role> selectByUser(User user) throws EntityNotFoundException;
	public void persist(Role role);
	public void updateNameById(String id, String oldName, String newName)throws EntityNotFoundException;
	public boolean isEntityExist(String roleName,User user);
}
