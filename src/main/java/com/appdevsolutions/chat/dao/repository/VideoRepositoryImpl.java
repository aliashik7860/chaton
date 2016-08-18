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

import com.appdevsolutions.chat.common.model.VideoType;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.entity.Video;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class VideoRepositoryImpl implements VideoRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(VideoRepositoryImpl.class);
	
	@Override
	public void persist(Video video) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(video);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<Video> selectAllIn() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findAllIn",Video.class);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public Video selectInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInById",Video.class);
		typedQuery.setParameter("id", id);
		try{
			final Video video= typedQuery.getSingleResult();
			entityManager.close();
			return video;
		}catch(NoResultException noResultException){
			throw new EntityNotFoundException("Video Entity not found with id : "+id);
		}
	}

	@Override
	public List<Video> selectInByReceiver(User receiver) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInByReceiver",Video.class);
		typedQuery.setParameter("receiver", receiver);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public List<Video> selectInByReceiverWithText(User receiver, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInByReceiverWithText",Video.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public List<Video> selectInByReceiverWithTime(User receiver, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInByReceiverWithTime",Video.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public void deleteInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			LOGGER.info("deleting video with id : "+id+" with status : "+status);
			throw new EntityNotFoundException("Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteInByReceiver");
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
	public List<Video> selectAllOut() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findAllOut",Video.class);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public Video selectOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutById",Video.class);
		typedQuery.setParameter("id", id);
		try{
			final Video video=typedQuery.getSingleResult();
			entityManager.close();
			return video;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("Video Entity not found with id : "+id);
		}
	}

	@Override
	public List<Video> selectOutBySender(User sender) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutBySender",Video.class);
		typedQuery.setParameter("sender", sender);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public List<Video> selectOutBySenderWithText(User sender, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutBySenderWithText",Video.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<Video> videos=typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public List<Video> selectOutBySenderWithTime(User sender, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutBySenderWithTime",Video.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Video> videos=typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public void deleteOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteOutById");
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
		final Query query=entityManager.createNamedQuery("Video.deleteOutBySender");
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
		final Query query=entityManager.createNamedQuery("Video.updateInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with id : "+id);
		}
	}

	@Override
	public void updateOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with senderId : "+sender.getId());
		}
	}
	
	@Override
	public void updateOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with id : "+id);
		}
	}

	@Override
	public void updateInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateInByReceiverWithContentType(User receiver,VideoType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateInByReceiverWithContentType");
		query.setParameter("receiver", receiver);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithContentType(User sender,VideoType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateOutBySenderWithContentType");
		query.setParameter("sender", sender);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with receiverId : "+sender.getId());
		}
	}
	
	@Override
	public void updateInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateInByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with name : "+name);
		}
	}
	@Override
	public void updateOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateOutByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with name : "+name);
		}
	}
	
	@Override
	public void updateInByReceiverWithName(User receiver,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateInByReceiverWithName");
		query.setParameter("receiver", receiver);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithName(User sender,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.updateOutBySenderWithName");
		query.setParameter("sender",sender);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Video Entity not found with receiverId : "+sender.getId());
		}
	}

	@Override
	public List<Video> selectAllInWithContentType(VideoType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findAllInWithContentType",Video.class);
		typedQuery.setParameter("contentType", contentType);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public List<Video> selectInByReceiverWithContentType(User receiver, VideoType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInByReceiverWithContentType",Video.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("contentType",contentType);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public void deleteInByReceiverWithContentType(User receiver, VideoType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteInByReceiverWithContentType");
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
	public List<Video> selectAllOutWithContentType(VideoType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findAllOutWithContentType",Video.class);
		typedQuery.setParameter("contentType", contentType);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public List<Video> selectOutBySenderWithContentType(User sender, VideoType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutBySenderWithContentType",Video.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("contentType",contentType);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public void deleteOutBySenderWithContentType(User sender, VideoType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteOutBySenderWithContentType");
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
	public Video selectInByReceiverWithName(User receiver, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInByReceiverWithName",Video.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", name);
		final Video video= typedQuery.getSingleResult();
		entityManager.close();
		return video;
	}

	@Override
	public List<Video> selectInByName(String name) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findInByName",Video.class);
		typedQuery.setParameter("name", name);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public void deleteInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteInByName");
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
		final Query query=entityManager.createNamedQuery("Video.deleteInByReceiverWithName");
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
	public Video selectOutBySenderWithName(User sender, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutBySenderWithName",Video.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", name);
		final Video video= typedQuery.getSingleResult();
		entityManager.close();
		return video;
	}

	@Override
	public List<Video> selectOutByName(String name) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Video> typedQuery=entityManager.createNamedQuery("Video.findOutByName",Video.class);
		typedQuery.setParameter("name", name);
		final List<Video> videos= typedQuery.getResultList();
		entityManager.close();
		return videos;
	}

	@Override
	public void deleteOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Video.deleteOutByName");
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
		final Query query=entityManager.createNamedQuery("Video.deleteOutBySenderWithName");
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