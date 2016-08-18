package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import com.appdevsolutions.chat.dao.entity.Privilege;
import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface PrivilegeRepository {
	public void deleteById(String id) throws EntityNotFoundException;
	public void deleteByName(String name) throws EntityNotFoundException;
	public void deleteByRole(Role role) throws EntityNotFoundException;
	public List<Privilege> selectAll();
	public Privilege selectById(String id) throws EntityNotFoundException;
	public List<Privilege> selectByName(String name) throws EntityNotFoundException;
	public List<Privilege> selectByRole(Role role) throws EntityNotFoundException;
	public void persist(Privilege privilege);
	public void updateNameById(String id,String oldName, String newName) throws EntityNotFoundException;
	public boolean isEntityExist(String privilegeName,Role role);
}
