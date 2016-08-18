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

import com.appdevsolutions.chat.dao.entity.EmailTemplate;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class EmailTemplateRepositoryImpl implements EmailTemplateRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public void persist(EmailTemplate emailTemplate) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(emailTemplate);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	@Override
	public List<EmailTemplate> findAll() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailTemplate> typedQuery=entityManager.createNamedQuery("EmailTemplate.findAll",EmailTemplate.class);
		List<EmailTemplate> emailTemplates;
		if((emailTemplates=typedQuery.getResultList())!=null){
			return emailTemplates;
		}else{
			emailTemplates=new ArrayList<EmailTemplate>();
			return emailTemplates;
		}
		
	}

	@Override
	public EmailTemplate findById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailTemplate> typedQuery=entityManager.createNamedQuery("EmailTemplate.findById",EmailTemplate.class);
		typedQuery.setParameter("id", id);
		try{
			final EmailTemplate emailTemplate= typedQuery.getSingleResult();
			entityManager.close();
			return emailTemplate;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public EmailTemplate findByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailTemplate> typedQuery=entityManager.createNamedQuery("EmailTemplate.findByName",EmailTemplate.class);
		typedQuery.setParameter("name", name);
		try{
			final EmailTemplate emailTemplate= typedQuery.getSingleResult();
			entityManager.close();
			return emailTemplate;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public EmailTemplate findBySubject(String subject) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailTemplate> typedQuery=entityManager.createNamedQuery("EmailTemplate.findBySubject",EmailTemplate.class);
		typedQuery.setParameter("subject", subject);
		try{
			final EmailTemplate emailTemplate= typedQuery.getSingleResult();
			entityManager.close();
			return emailTemplate;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateEmailFromById(String id, String emailFrom) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateFromById");
		query.setParameter("id",id );
		query.setParameter("emailFrom", emailFrom);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateEmailFromByName(String name, String emailFrom) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateFromByName");
		query.setParameter("name",name );
		query.setParameter("emailFrom", emailFrom);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateEmailFromBySubject(String subject, String emailFrom) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateFromBySubject");
		query.setParameter("subject",subject);
		query.setParameter("emailFrom", emailFrom);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateName(String oldName, String newName) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateName");
		query.setParameter("oldName",oldName);
		query.setParameter("newName", newName);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateSubject(String oldSubject, String newSubject) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateSubject");
		query.setParameter("oldSubject",oldSubject);
		query.setParameter("newSubject", newSubject);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateBodyById(String id, String body) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateBodyById");
		query.setParameter("id",id);
		query.setParameter("body", body);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateBodyBySubject(String subject, String body) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateBodyBySubject");
		query.setParameter("subject",subject);
		query.setParameter("body", body);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void updateBodyByName(String name, String body) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.updateBodyByName");
		query.setParameter("name",name);
		query.setParameter("body", body);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void deleteById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.deleteById");
		query.setParameter("id",id );
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void deleteBySubject(String subject) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.deleteBySubject");
		query.setParameter("subject",subject);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void deleteByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailTemplate.deleteByName");
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}
}