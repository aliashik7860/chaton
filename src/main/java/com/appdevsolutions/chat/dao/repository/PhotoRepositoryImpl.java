package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
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

import com.appdevsolutions.chat.common.model.ImageType;
import com.appdevsolutions.chat.dao.entity.Photo;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class PhotoRepositoryImpl implements PhotoRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(PhotoRepositoryImpl.class);
	
	@Override
	public void persist(Photo photo) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(photo);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<Photo> selectAllIn() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findAllIn",Photo.class);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public Photo selectInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInById",Photo.class);
		typedQuery.setParameter("id", id);
		try{
			final Photo photo= typedQuery.getSingleResult();
			entityManager.close();
			return photo;
		}catch(NoResultException noResultException){
			throw new EntityNotFoundException("Photo Entity not found with id : "+id);
		}
	}

	@Override
	public List<Photo> selectInByReceiver(User receiver) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInByReceiver",Photo.class);
		typedQuery.setParameter("receiver", receiver);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public List<Photo> selectInByReceiverWithText(User receiver, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInByReceiverWithText",Photo.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public List<Photo> selectInByReceiverWithTime(User receiver, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInByReceiverWithTime",Photo.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public void deleteInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			LOGGER.info("deleting message with id : "+id+" with status : "+status);
			throw new EntityNotFoundException("Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with receiverId : "+receiver.getId());
		}
	}

	@Override
	public List<Photo> selectAllOut() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findAllOut",Photo.class);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public Photo selectOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutById",Photo.class);
		typedQuery.setParameter("id", id);
		try{
			final Photo message=typedQuery.getSingleResult();
			entityManager.close();
			return message;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("Photo Entity not found with id : "+id);
		}
	}

	@Override
	public List<Photo> selectOutBySender(User sender) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutBySender",Photo.class);
		typedQuery.setParameter("sender", sender);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public List<Photo> selectOutBySenderWithText(User sender, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutBySenderWithText",Photo.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<Photo> photos=typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public List<Photo> selectOutBySenderWithTime(User sender, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutBySenderWithTime",Photo.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Photo> photos=typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public void deleteOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with senderId : "+sender.getId());
		}
	}
	
	@Override
	public void updateInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with id : "+id);
		}
	}

	@Override
	public void updateOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with senderId : "+sender.getId());
		}
	}
	
	@Override
	public void updateOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with id : "+id);
		}
	}

	@Override
	public void updateInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateInByReceiverWithContentType(User receiver,ImageType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateInByReceiverWithContentType");
		query.setParameter("receiver", receiver);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithContentType(User sender,ImageType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateOutBySenderWithContentType");
		query.setParameter("sender", sender);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with receiverId : "+sender.getId());
		}
	}
	
	@Override
	public void updateInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateInByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with name : "+name);
		}
	}
	
	@Override
	public void updateOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateOutByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with name : "+name);
		}
	}
	
	@Override
	public void updateInByReceiverWithName(User receiver,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateInByReceiverWithName");
		query.setParameter("receiver", receiver);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithName(User sender,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.updateOutBySenderWithName");
		query.setParameter("sender",sender);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Photo Entity not found with receiverId : "+sender.getId());
		}
	}

	@Override
	public List<Photo> selectAllInWithContentType(ImageType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findAllInWithContentType",Photo.class);
		typedQuery.setParameter("contentType", contentType);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public List<Photo> selectInByReceiverWithContentType(User receiver, ImageType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInByReceiverWithContentType",Photo.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("contentType",contentType);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public void deleteInByReceiverWithContentType(User receiver, ImageType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteInByReceiverWithContentType");
		query.setParameter("receiver", receiver);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with receiverId : "+receiver.getId()+" contentType : "+contentType);
		}
	}

	@Override
	public List<Photo> selectAllOutWithContentType(ImageType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findAllOutWithContentType",Photo.class);
		typedQuery.setParameter("contentType", contentType);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public List<Photo> selectOutBySenderWithContentType(User sender, ImageType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutBySenderWithContentType",Photo.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("contentType",contentType);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public void deleteOutBySenderWithContentType(User sender, ImageType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteOutBySenderWithContentType");
		query.setParameter("sender", sender);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with senderId : "+sender.getId()+" contentType : "+contentType);
		}
	}

	@Override
	public Photo selectInByReceiverWithName(User receiver, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInByReceiverWithName",Photo.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", name);
		final Photo photo= typedQuery.getSingleResult();
		entityManager.close();
		return photo;
	}

	@Override
	public List<Photo> selectInByName(String name){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findInByName",Photo.class);
		typedQuery.setParameter("name", name);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public void deleteInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteInByName");
		query.setParameter("id", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with name : "+name);
		}
	}

	@Override
	public void deleteInByReceiverWithName(User receiver, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteInByReceiverWithName");
		query.setParameter("receiver", receiver);
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with receiverId : "+receiver.getId()+" name : "+name);
		}
	}

	@Override
	public Photo selectOutBySenderWithName(User sender, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutBySenderWithName",Photo.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", name);
		final Photo photo= typedQuery.getSingleResult();
		entityManager.close();
		return photo;
	}

	@Override
	public List<Photo> selectOutByName(String name) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Photo> typedQuery=entityManager.createNamedQuery("Photo.findOutByName",Photo.class);
		typedQuery.setParameter("name", name);
		final List<Photo> photos= typedQuery.getResultList();
		entityManager.close();
		return photos;
	}

	@Override
	public void deleteOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteOutByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with name : "+name);
		}
	}

	@Override
	public void deleteOutBySenderWithName(User sender, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Photo.deleteOutBySenderWithName");
		query.setParameter("sender",sender);
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Entity not found with senderId : "+sender.getId()+" name : "+name);
		}
	}
	
}
