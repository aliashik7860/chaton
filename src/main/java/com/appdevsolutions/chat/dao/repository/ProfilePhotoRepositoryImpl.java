package com.appdevsolutions.chat.dao.repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appdevsolutions.chat.dao.entity.ProfilePhoto;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class ProfilePhotoRepositoryImpl implements ProfilePhotoRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public void save(ProfilePhoto profilePhoto) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(profilePhoto);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void update(ProfilePhoto profilePhoto) {
		
	}

	@Override
	public void deleteById(String profilePhotoId) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("ProfilePhoto.deleteById");
		query.setParameter("id", profilePhotoId);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		if(status==0){
			throw new EntityNotFoundException("ProfilePhoto Entity not found with id : "+profilePhotoId);
		}
	}

	@Override
	public void deleteByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("ProfilePhoto.deleteByUser");
		query.setParameter("user", user);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		if(status==0){
			throw new EntityNotFoundException("ProfilePhoto Entity not found with userId : "+user.getId());
		}
	}

	@Override
	public ProfilePhoto selectById(String profilePhotoId) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<ProfilePhoto> query=entityManager.createNamedQuery("ProfilePhoto.findById",ProfilePhoto.class);
		query.setParameter("id", profilePhotoId);
		try{
			final ProfilePhoto profilePhoto=query.getSingleResult();
			entityManager.close();
			return profilePhoto;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("ProfilePhoto Entity not found with id : "+profilePhotoId);
		}
	}

	@Override
	public ProfilePhoto selectByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<ProfilePhoto> query=entityManager.createNamedQuery("ProfilePhoto.findByUser",ProfilePhoto.class);
		query.setParameter("user", user);
		try{
			final ProfilePhoto profilePhoto=query.getSingleResult();
			entityManager.close();
			return profilePhoto;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("ProfilePhoto Entity not found with userId : "+user.getId());
		}
	}
}