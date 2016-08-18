package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appdevsolutions.chat.dao.entity.EmailNotificationTemplate;
import com.appdevsolutions.chat.dao.entity.EmailTemplate;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class EmailNotificationTemplateRepositoryImpl implements EmailNotificationTemplateRepository{

	private static final Logger LOGGER=LoggerFactory.getLogger(EmailNotificationTemplateRepositoryImpl.class);
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailTemplateRepository emailTemplateRepository;
	
	@Override
	public void persist(EmailNotificationTemplate emailNotificationTemplate) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(emailNotificationTemplate);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	@Override
	public boolean checkUnique(EmailTemplate emailTemplate, User user) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailNotificationTemplate> typedQuery=entityManager.createNamedQuery("EmailNotificationTemplate.checkUnique",EmailNotificationTemplate.class);
		typedQuery.setParameter("emailTemplate", emailTemplate);
		typedQuery.setParameter("user", user);
		try{
			typedQuery.getSingleResult();
			return false;
		}catch(NoResultException exception){
			return true;
		}
	}

	@Override
	public EmailNotificationTemplate findById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailNotificationTemplate> typedQuery=entityManager.createNamedQuery("EmailNotificationTemplate.findById",EmailNotificationTemplate.class);
		typedQuery.setParameter("id", id);
		try{
			final EmailNotificationTemplate emailNotificationTemplate= typedQuery.getSingleResult();
			entityManager.close();
			return emailNotificationTemplate;
		}catch(NoResultException noResultException){
			LOGGER.error("EmailNotificationTemplate not found ");
			entityManager.close();
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public List<EmailNotificationTemplate> findByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailNotificationTemplate> typedQuery=entityManager.createNamedQuery("EmailNotificationTemplate.findByUser",EmailNotificationTemplate.class);
		typedQuery.setParameter("user", user);
		try{
			final List<EmailNotificationTemplate> emailNotificationTemplates= typedQuery.getResultList();
			entityManager.close();
			return emailNotificationTemplates;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("GE_1010");
		}
	}


	@Override
	public void deleteById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.deleteById");
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
	public void deleteByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.deleteByUser");
		query.setParameter("user",user);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}


	@Override
	public boolean isActive(String id) throws EntityNotFoundException {
		return findById(id).isActive();
	}

	@Override
	public void setActiveById(String id, boolean state) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.setActiveById");
		query.setParameter("id",id);
		query.setParameter("active", state);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void setActiveByUser(User user, boolean state) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.setActiveByUser");
		query.setParameter("user",user);
		query.setParameter("active", state);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}


	@Override
	public List<EmailNotificationTemplate> findByEmailTemplate(EmailTemplate emailTemplate)
		throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailNotificationTemplate> typedQuery=entityManager.createNamedQuery("EmailNotificationTemplate.findByEmailTemplate",EmailNotificationTemplate.class);
		typedQuery.setParameter("emailTemplate", emailTemplate);
		final List<EmailNotificationTemplate> emailNotificationTemplates= typedQuery.getResultList();
		entityManager.close();
		return emailNotificationTemplates;
	}


	@Override
	public void deleteByEmailTemplate(EmailTemplate emailTemplate) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.deleteByEmailTemplate");
		query.setParameter("emailTemplate",emailTemplate);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void deleteByEmailTemplateWithUser(EmailTemplate emailTemplate, User user)
			throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.deleteByEmailTemplateWithUser");
		query.setParameter("emailTemplate", emailTemplate);
		query.setParameter("user",user);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public List<EmailNotificationTemplate> findByEmailTemplateWithUser(EmailTemplate emailTemplate, User user)
			throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailNotificationTemplate> typedQuery=entityManager.createNamedQuery("EmailNotificationTemplate.findByEmailTemplateWithUser",EmailNotificationTemplate.class);
		typedQuery.setParameter("emailTemplate", emailTemplate);
		typedQuery.setParameter("user", user);
		final List<EmailNotificationTemplate> emailNotificationTemplates= typedQuery.getResultList();
		entityManager.close();
		return emailNotificationTemplates;
		
	}

	@Override
	public List<EmailNotificationTemplate> findAll() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<EmailNotificationTemplate> typedQuery=entityManager.createNamedQuery("EmailNotificationTemplate.findAll",EmailNotificationTemplate.class);
		final List<EmailNotificationTemplate> emailNotificationTemplates= typedQuery.getResultList();
		entityManager.close();
		return emailNotificationTemplates;
	}

	@Override
	public void setActiveByEmailTemplate(EmailTemplate emailTemplate, boolean active) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.setActiveByEmailTemplate");
		query.setParameter("emailTemplate",emailTemplate);
		query.setParameter("active", active);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}

	@Override
	public void setActiveByEmailTemplateWithUser(EmailTemplate emailTemplate, User user, boolean active)
			throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("EmailNotificationTemplate.setActiveByEmailTemplateWithUser");
		query.setParameter("emailTemplate", emailTemplate);
		query.setParameter("user",user);
		query.setParameter("active", active);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("GE_1010");
		}
	}
}
