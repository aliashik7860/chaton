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

import com.appdevsolutions.chat.dao.entity.Privilege;
import com.appdevsolutions.chat.dao.entity.Role;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class PrivilegeRepositoryImpl implements PrivilegeRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public void deleteById(String id) throws EntityNotFoundException{
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Privilege.deleteById");
		query.setParameter("id",id );
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1006");
		}
	}

	@Override
	public void deleteByName(String name) throws EntityNotFoundException{
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Privilege.deleteByName");
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1006");
		}
	}

	@Override
	public List<Privilege> selectAll() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Privilege> query=entityManager.createNamedQuery("Privilege.findAll",Privilege.class);
		List<Privilege> privileges;
		try{
			privileges= query.getResultList();
			entityManager.close();
			return privileges;
		}catch(NoResultException noResultException){
			entityManager.close();
			privileges=new ArrayList<Privilege>();
			return privileges;
		}
	}

	@Override
	public Privilege selectById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Privilege> typedQuery=entityManager.createNamedQuery("Privilege.findById",Privilege.class);
		typedQuery.setParameter("id", id);
		try{
			final Privilege privilege= typedQuery.getSingleResult();
			entityManager.close();
			return privilege;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1006");
		}
	}

	@Override
	public List<Privilege> selectByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Privilege> typedQuery=entityManager.createNamedQuery("Privilege.findByName",Privilege.class);
		typedQuery.setParameter("name", name);
		try{
			final List<Privilege> privileges= typedQuery.getResultList();
			entityManager.close();
			return privileges;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1006");
		}
	}

	@Override
	public void persist(Privilege privilege) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(privilege);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void deleteByRole(Role role) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Privilege.deleteByRole");
		query.setParameter("role", role);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1006");
		}
	}

	@Override
	public List<Privilege> selectByRole(Role role) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Privilege> typedQuery=entityManager.createNamedQuery("Privilege.findByRole",Privilege.class);
		typedQuery.setParameter("role", role);
		try{
			final List<Privilege> privileges= typedQuery.getResultList();
			entityManager.close();
			return privileges;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1006");
		}
	}
	
	@Override
	public void updateNameById(String id, String oldName, String newName) throws EntityNotFoundException{
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Privilege.updateNameById");
		query.setParameter("id",id );
		query.setParameter("oldName", oldName);
		query.setParameter("newName", newName);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1006");
		}
	}
	@Override
	public boolean isEntityExist(String privilegeName, Role role) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		int result=((Long)entityManager.createNamedQuery("Privilege.isEntityExist").setParameter("name", privilegeName).setParameter("role", role).getSingleResult()).intValue();
		if(result>0){
			return true;
		}
		return false;
	}

}