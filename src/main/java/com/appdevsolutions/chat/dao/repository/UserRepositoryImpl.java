package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class UserRepositoryImpl implements UserRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	ProfilePhotoRepository profilePhotoRepository;
	@Override
	public void deleteById(String userId) throws EntityNotFoundException {
		//final User user=selectById(userId);
		//roleRepository.deleteByUserId(userId);
		//final ProfilePhoto profilePhoto=profilePhotoRepository.selectByUserId(userId);
		//profilePhotoRepository.deleteById(user.getProfilePhoto().getId());
		//final ProfilePhoto profilePhoto=user.getProfilePhoto();
		//user.setProfilePhoto(null);
		//profilePhoto.setUser(null);
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("User.deleteById");
		query.setParameter("id", userId);
		entityManager.getTransaction().begin();
		//entityManager.merge(user);
		//entityManager.remove(entityManager.merge(profilePhoto));
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("User Entity not found with userId : "+userId);
		}
	}

	@Override
	public void deleteByUsername(String username) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("User.deleteByUsername");
		query.setParameter("username", username);
		try{
			entityManager.getTransaction().begin();
			query.executeUpdate();
			entityManager.getTransaction().commit();
			entityManager.close();
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("User Entity not found with username : "+username);
		}
	}

	@Override
	public User selectById(String userId) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<User> typedQuery=entityManager.createNamedQuery("User.findById",User.class);
		typedQuery.setParameter("id", userId);
		try{
			final User user=typedQuery.getSingleResult();
			entityManager.close();
			return user;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("User Entity could not be found with userId : "+userId);
		}
		
	}
	
	@Override
	public List<User> selectByIds(List<String> userIds ) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<User> typedQuery=entityManager.createNamedQuery("User.findByIds",User.class);
		typedQuery.setParameter("ids", userIds);
		return typedQuery.getResultList();
	}

	@Override
	public User selectByUsername(String username) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<User> typedQuery=entityManager.createNamedQuery("User.findByUsername",User.class);
		typedQuery.setParameter("username", username);
		try{
			final User user=typedQuery.getSingleResult();
			entityManager.close();
			return user;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("User Entity could not be found with userId : "+username);
		}
	}

	@Override
	public List<User> selectAll() throws EntityNotFoundException {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		TypedQuery<User> typedQuery=entityManager.createNamedQuery("User.findAll",User.class);
		return typedQuery.getResultList();
	}
	
	

	@Override
	public boolean isUserExistById(String userId) {
		try{
			selectById(userId);
			return true;
		}catch(EntityNotFoundException entityNotFoundException){
			return false;
		}
	}

	@Override
	public boolean isUserExistByUsername(String username) {
		try{
			selectById(username);
			return true;
		}catch(EntityNotFoundException entityNotFoundException){
			return false;
		}
	}

	@Override
	public void persist(User user) {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		EntityTransaction transaction=entityManager.getTransaction();
		transaction.begin();
		entityManager.persist(user);
		transaction.commit();
		entityManager.close();
	}

	@Override
	public User getUserByUsernameAndPassword(String username, String password) throws EntityNotFoundException {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		TypedQuery<User> query=entityManager.createNamedQuery("User.findByUsernameAndPassword",User.class);
		User user=query.getSingleResult();
		if(user==null){
			throw new EntityNotFoundException("UserEntity not found with username : "+username+" and password : "+password);
		}else{
			return user;
		}
	}

	@Override
	public void update(User user) throws EntityNotFoundException {
		EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(user);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void updateLastLoginByUserId(String userId, LocalDateTime now) throws EntityNotFoundException {
		
	}

	@Override
	public void updateLastLoginByUsername(String username, LocalDateTime now) throws EntityNotFoundException {
		
	}
}