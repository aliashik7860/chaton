package com.appdevsolutions.chat.dao.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class RoleRepositoryImpl implements RoleRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Autowired
	PrivilegeRepository privilegeRepository;
	
	private void deleteByIdCommon(String id){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Role.deleteById");
		query.setParameter("id",id );
		entityManager.getTransaction().begin();
		query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	@Override
	public void deleteById(String id) throws EntityNotFoundException {
		final Role role=selectById(id);
		privilegeRepository.deleteByRole(role);
		deleteByIdCommon(role.getId());
	}

	@Override
	public void deleteByName(String name) throws EntityNotFoundException {
		final List<Role> roles=selectByName(name);
		for(Role role:roles){
			privilegeRepository.deleteByRole(role);
			deleteByIdCommon(role.getId());
		}
	}

	@Override
	public void deleteByUser(User user) throws EntityNotFoundException {
		final List<Role> roles=selectByUser(user);
		for(Role role:roles){
			privilegeRepository.deleteByRole(role);
			deleteByIdCommon(role.getId());
		}
	}

	@Override
	public List<Role> selectAll() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Role> query=entityManager.createNamedQuery("Role.findAll",Role.class);
		List<Role> roles;
		try{
			roles= query.getResultList();
			entityManager.close();
			return roles;
		}catch(NoResultException noResultException){
			entityManager.close();
			roles=new ArrayList<Role>();
			return roles;
		}
	}

	@Override
	public Role selectById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Role> typedQuery=entityManager.createNamedQuery("Role.findById",Role.class);
		typedQuery.setParameter("id", id);
		try{
			final Role role= typedQuery.getSingleResult();
			entityManager.close();
			return role;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("Role Entity not found with id : "+id);
		}
	}

	@Override
	public List<Role> selectByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Role> typedQuery=entityManager.createNamedQuery("Role.findByName",Role.class);
		typedQuery.setParameter("name", name);
		final List<Role> roles= typedQuery.getResultList();
		entityManager.close();
		if(roles.isEmpty()){
			throw new EntityNotFoundException("Role Entity not found with name : "+name);
		}
		return roles;
	}

	@Override
	public List<Role> selectByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Role> typedQuery=entityManager.createNamedQuery("Role.findByUser",Role.class);
		typedQuery.setParameter("user", user);
		try{
			final List<Role> roles= typedQuery.getResultList();
			entityManager.close();
			return roles;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("Role Entity not found with userId : "+user.getId());
		}
	}

	@Override
	public void persist(Role role) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(role);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	@Override
	public void updateNameById(String id, String oldName, String newName) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Role.updateNameById");
		query.setParameter("id",id );
		query.setParameter("oldName", oldName);
		query.setParameter("newName", newName);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Privilege Entity not found with id : "+id);
		}
	}
	
	@Override
	public boolean isEntityExist(String roleName, User user) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		int result=((Long)entityManager.createNamedQuery("Role.isEntityExist").setParameter("name", roleName).setParameter("user", user).getSingleResult()).intValue();
		if(result>0){
			return true;
		}
		return false;
	}
}