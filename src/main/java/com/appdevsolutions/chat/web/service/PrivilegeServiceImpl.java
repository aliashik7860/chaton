package com.appdevsolutions.chat.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.Privilege;
import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.PrivilegeRepository;
import com.appdevsolutions.chat.dao.repository.RoleRepository;
import com.appdevsolutions.chat.web.exception.PrivilegeAlreadyExistException;
import com.appdevsolutions.chat.web.exception.PrivilegeNotFoundException;
import com.appdevsolutions.chat.web.exception.RoleNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.PrivilegeModel;

@Service("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	PrivilegeRepository privilegeRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public void removeById(String id) throws PrivilegeNotFoundException {
		try{
			privilegeRepository.deleteById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PrivilegeNotFoundException("GE_1006", ChatOnWebConstants.PRIVILEGE_ID, id);
		}
	}

	@Override
	public void removeByName(String name) throws PrivilegeNotFoundException {
		try{
			privilegeRepository.deleteByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PrivilegeNotFoundException("GE_1006", ChatOnWebConstants.PRIVILEGE_NAME, name);
		}
	}

	@Override
	public void removeByRoleId(String roleId) throws PrivilegeNotFoundException,RoleNotFoundException {
		try{
			final Role role=roleRepository.selectById(roleId);
			try{
				privilegeRepository.deleteByRole(role);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new PrivilegeNotFoundException("GE_1006",ChatOnWebConstants.ROLE_ID, roleId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new RoleNotFoundException("GE_1020", ChatOnWebConstants.ROLE_ID, roleId);
		}
	}

	@Override
	public Set<PrivilegeModel> getAll(){
		final List<Privilege> privileges=privilegeRepository.selectAll();
		final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
		for(final Privilege privilege:privileges){
			final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), privilege.getRole().getId(), privilege.getCreateTimestamp());
			privilegeModels.add(privilegeModel);
		}
		return privilegeModels;
	}

	@Override
	public PrivilegeModel getById(String id) throws PrivilegeNotFoundException {
		try{
			Privilege privilege=privilegeRepository.selectById(id);
			final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), privilege.getRole().getId(), privilege.getCreateTimestamp());
			return privilegeModel;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PrivilegeNotFoundException("GE_1006", ChatOnWebConstants.PRIVILEGE_ID, id);
		}
	}

	@Override
	public Set<PrivilegeModel> getByName(String name) throws PrivilegeNotFoundException {
		try{
			final List<Privilege> privileges=privilegeRepository.selectByName(name);
			final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
			for(Privilege privilege:privileges){
				final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), privilege.getRole().getId(), privilege.getCreateTimestamp());
				privilegeModels.add(privilegeModel);
			}
			return privilegeModels;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PrivilegeNotFoundException("GE_1006",ChatOnWebConstants.PRIVILEGE_NAME, name);
		}
	}

	@Override
	public Set<PrivilegeModel> getByRoleId(String roleId) throws PrivilegeNotFoundException,RoleNotFoundException {
		try{
			final Role role=roleRepository.selectById(roleId);
			try{
				final List<Privilege> privileges=privilegeRepository.selectByRole(role);
				final Set<PrivilegeModel> privilegeModels=new HashSet<PrivilegeModel>();
				for(Privilege privilege:privileges){
					final PrivilegeModel privilegeModel=new PrivilegeModel(privilege.getId(), privilege.getName(), privilege.getRole().getId(), privilege.getCreateTimestamp());
					privilegeModels.add(privilegeModel);
				}
				return privilegeModels;
			}catch(EntityNotFoundException entityNotFoundException){
				throw new PrivilegeNotFoundException("GE_1006", ChatOnWebConstants.ROLE_ID, roleId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new RoleNotFoundException("GE_1020", ChatOnWebConstants.ROLE_ID, roleId);
		}
	}

	@Override
	public void save(PrivilegeModel privilegeModel) throws RoleNotFoundException,PrivilegeAlreadyExistException {
		try {
			final Role role=roleRepository.selectById(privilegeModel.getRoleId());
			if(!roleRepository.isEntityExist(privilegeModel.getName(), role.getUser())){
				final Privilege privilege=new Privilege();
				privilege.setName(privilegeModel.getName());
				privilege.setRole(role);
				privilege.setCreateTimestamp(privilegeModel.getCreateTimestamp());
				privilegeRepository.persist(privilege);
			}else{
				throw new PrivilegeAlreadyExistException("GE_1008",ChatOnWebConstants.ROLE_ID,privilegeModel.getRoleId());
			}
		} catch (EntityNotFoundException e) {
			throw new RoleNotFoundException("GE_1020",ChatOnWebConstants.ROLE_ID,privilegeModel.getRoleId());
		}
	}

	@Override
	public void updateName(String privilegeId,String oldPrivilegeName, String newPrivilegeName) throws PrivilegeNotFoundException, PrivilegeAlreadyExistException{
		final PrivilegeModel privilegeModel=getById(privilegeId);
		try{
			final Role role=roleRepository.selectById(privilegeModel.getRoleId());
			if(!privilegeRepository.isEntityExist(newPrivilegeName, role)){
				privilegeRepository.updateNameById(privilegeId, oldPrivilegeName, newPrivilegeName);
			}else{
				throw new PrivilegeAlreadyExistException("GE_1007", ChatOnWebConstants.ROLE_ID, privilegeModel.getRoleId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PrivilegeNotFoundException("GE_1006", ChatOnWebConstants.OLD_PRIVILEGE_NAME, oldPrivilegeName);
		}
	}

}
