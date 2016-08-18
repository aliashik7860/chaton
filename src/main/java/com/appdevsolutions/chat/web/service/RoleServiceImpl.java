package com.appdevsolutions.chat.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.Privilege;
import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.PrivilegeRepository;
import com.appdevsolutions.chat.dao.repository.RoleRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.RoleAlreadyExistException;
import com.appdevsolutions.chat.web.exception.RoleNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.PrivilegeModel;
import com.appdevsolutions.chat.web.model.RoleModel;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Override
	public void removeById(String id) throws RoleNotFoundException {
		try{
			roleRepository.deleteById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.ROLE_ID, id);
		}
	}

	@Override
	public void removeByName(String name) throws RoleNotFoundException {
		try {
			final List<Role> roles=roleRepository.selectByName(name);
			for(final Role role:roles){
				for(final Privilege privilege:role.getPrivileges()){
					privilegeRepository.deleteById(privilege.getId());
				}
				roleRepository.deleteByUser(role.getUser());
			}
		} catch (EntityNotFoundException e) {
			throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.ROLE_NAME, name);
		}
	}

	@Override
	public void removeByUserId(String userId) throws RoleNotFoundException,UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			try {
				final List<Role> roles=roleRepository.selectByUser(user);
				for(final Role role:roles){
					privilegeRepository.deleteByRole(role);
					roleRepository.deleteByUser(user);
				}
			} catch (EntityNotFoundException e) {
				throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.ROLE_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1019", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public Set<RoleModel> getAll() {
		final List<Role> roles=roleRepository.selectAll();
		final Set<RoleModel> roleModels=new HashSet<RoleModel>();
		for(Role role:roles){
			final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
			for(Privilege privilege:role.getPrivileges()){
				final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), role.getId(), role.getCreateTimestamp());
				privilegeModels.add(privilegeModel);
			}
			final RoleModel roleModel=new RoleModel(role.getId(), role.getName(), role.getUser().getId(), privilegeModels, role.getCreateTimestamp());
			roleModels.add(roleModel);
		}
		return roleModels;
	}

	@Override
	public RoleModel getById(String id) throws RoleNotFoundException {
		try {
			final Role role=roleRepository.selectById(id);
			final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
			for(Privilege privilege:role.getPrivileges()){
				PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), role.getId(), privilege.getCreateTimestamp());
				privilegeModels.add(privilegeModel);
			}
			final RoleModel roleModel=new RoleModel(role.getId(), role.getName(), role.getUser().getId(), privilegeModels,role.getCreateTimestamp());
			return roleModel;
		} catch (EntityNotFoundException e) {
			throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.ROLE_ID, id);
		}
	}

	@Override
	public Set<RoleModel> getByName(String name) throws RoleNotFoundException {
		List<Role> roles;
		try {
			roles = roleRepository.selectByName(name);
			final Set<RoleModel> roleModels=new HashSet<RoleModel>();
			for(Role role:roles){
				final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
				for(Privilege privilege:role.getPrivileges()){
					final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), role.getId(), role.getCreateTimestamp());
					privilegeModels.add(privilegeModel);
				}
				final RoleModel roleModel=new RoleModel(role.getId(), role.getName(), role.getUser().getId(), privilegeModels, role.getCreateTimestamp());
				roleModels.add(roleModel);
			}
			return roleModels;
		} catch (EntityNotFoundException e) {
			throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.ROLE_NAME, name);
		}
	}

	@Override
	public Set<RoleModel> getByUserId(String userId) throws RoleNotFoundException,UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			try {
				final List<Role>	roles = roleRepository.selectByUser(user);
				final Set<RoleModel> roleModels=new HashSet<RoleModel>();
				for(Role role:roles){
					final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
					for(Privilege privilege:role.getPrivileges()){
						final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), role.getId(), role.getCreateTimestamp());
						privilegeModels.add(privilegeModel);
					}
					final RoleModel roleModel=new RoleModel(role.getId(), role.getName(), role.getUser().getId(), privilegeModels, role.getCreateTimestamp());
					roleModels.add(roleModel);
				}
				return roleModels;
			} catch (EntityNotFoundException e) {
				throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1019", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public void save(RoleModel roleModel) throws UserNotFoundException,RoleAlreadyExistException {
		try {
			final User user=userRepository.selectById(roleModel.getUserId());
			if(!roleRepository.isEntityExist(roleModel.getName(), user)){
				final Role role=new Role();
				role.setName(roleModel.getName());
				role.setUser(user);
				role.setCreateTimestamp(roleModel.getCreateTimestamp());
				roleRepository.persist(role);
			}else{
				throw new RoleAlreadyExistException("GE_1002",ChatOnWebConstants.USER_ID,roleModel.getUserId());
			}
		} catch (EntityNotFoundException e) {
			throw new UserNotFoundException("GE_1019",ChatOnWebConstants.USER_ID,roleModel.getUserId());
		}
	}

	@Override
	public void updateName(String roleId,String oldRoleName,String newRoleName) throws RoleNotFoundException,RoleAlreadyExistException {
		final RoleModel roleModel=getById(roleId);
		try{
			final User user=userRepository.selectById(roleModel.getUserId());
			try{
				if(!roleRepository.isEntityExist(roleModel.getName(), user)){
					roleRepository.updateNameById(roleId,oldRoleName,newRoleName);
				}else{
					throw new RoleAlreadyExistException("GE_1002", ChatOnWebConstants.USER_ID, roleModel.getUserId());
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new RoleNotFoundException("GE_1005", ChatOnWebConstants.OLD_ROLE_NAME, oldRoleName);
			}
		}catch(EntityNotFoundException entityNotFoundException){
		}	
	}
}