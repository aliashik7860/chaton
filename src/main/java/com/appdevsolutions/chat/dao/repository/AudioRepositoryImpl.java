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

import com.appdevsolutions.chat.common.model.AudioType;
import com.appdevsolutions.chat.dao.entity.Audio;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class AudioRepositoryImpl implements AudioRepository{

	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AudioRepositoryImpl.class);
	
	@Override
	public void persist(Audio audio) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(audio);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<Audio> selectAllIn() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findAllIn",Audio.class);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public Audio selectInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInById",Audio.class);
		typedQuery.setParameter("id", id);
		try{
			final Audio audio= typedQuery.getSingleResult();
			entityManager.close();
			return audio;
		}catch(NoResultException noResultException){
			throw new EntityNotFoundException("Audio Entity not found with id : "+id);
		}
	}

	@Override
	public List<Audio> selectInByReceiver(User receiver) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInByReceiver",Audio.class);
		typedQuery.setParameter("receiver", receiver);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public List<Audio> selectInByReceiverWithText(User receiver, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInByReceiverWithText",Audio.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public List<Audio> selectInByReceiverWithTime(User receiver, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInByReceiverWithTime",Audio.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public void deleteInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			LOGGER.info("deleting audio with id : "+id+" with status : "+status);
			throw new EntityNotFoundException("Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteInByReceiver");
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
	public List<Audio> selectAllOut() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findAllOut",Audio.class);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public Audio selectOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutById",Audio.class);
		typedQuery.setParameter("id", id);
		try{
			final Audio audio=typedQuery.getSingleResult();
			entityManager.close();
			return audio;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("Audio Entity not found with id : "+id);
		}
	}

	@Override
	public List<Audio> selectOutBySender(User sender) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutBySender",Audio.class);
		typedQuery.setParameter("sender", sender);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public List<Audio> selectOutBySenderWithText(User sender, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutBySenderWithText",Audio.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", "%"+text+"%");
		final List<Audio> audios=typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public List<Audio> selectOutBySenderWithTime(User sender, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutBySenderWithTime",Audio.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Audio> audios=typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public void deleteOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteOutById");
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
		final Query query=entityManager.createNamedQuery("Audio.deleteOutBySender");
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
		final Query query=entityManager.createNamedQuery("Audio.updateInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with id : "+id);
		}
	}
	
	@Override
	public void updateInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateInByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with name : "+name);
		}
	}
	
	@Override
	public void updateOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateOutByName");
		query.setParameter("name", name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with name : "+name);
		}
	}
	
	@Override
	public void updateOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with senderId : "+sender.getId());
		}
	}
	
	@Override
	public void updateOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with id : "+id);
		}
	}

	@Override
	public void updateInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateInByReceiverWithName(User receiver,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateInByReceiverWithName");
		query.setParameter("receiver", receiver);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithName(User sender,String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateOutBySenderWithName");
		query.setParameter("sender",sender);
		query.setParameter("name",name);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with receiverId : "+sender.getId());
		}
	}
	
	@Override
	public void updateInByReceiverWithContentType(User receiver,AudioType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateInByReceiverWithContentType");
		query.setParameter("receiver", receiver);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with receiverId : "+receiver.getId());
		}
	}
	
	@Override
	public void updateOutBySenderWithContentType(User sender,AudioType contentType) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.updateOutBySenderWithContentType");
		query.setParameter("sender", sender);
		query.setParameter("contentType", contentType);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Audio Entity not found with receiverId : "+sender.getId());
		}
	}

	@Override
	public List<Audio> selectAllInWithContentType(AudioType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findAllInWithContentType",Audio.class);
		typedQuery.setParameter("contentType", contentType);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public List<Audio> selectInByReceiverWithContentType(User receiver, AudioType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInByReceiverWithContentType",Audio.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("contentType",contentType);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public void deleteInByReceiverWithContentType(User receiver, AudioType contentType)throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteInByReceiverWithContentType");
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
	public List<Audio> selectAllOutWithContentType(AudioType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findAllOutWithContentType",Audio.class);
		typedQuery.setParameter("contentType", contentType);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public List<Audio> selectOutBySenderWithContentType(User sender, AudioType contentType) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutBySenderWithContentType",Audio.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("contentType",contentType);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public void deleteOutBySenderWithContentType(User sender, AudioType contentType) throws EntityNotFoundException{
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteOutBySenderWithContentType");
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
	public Audio selectInByReceiverWithName(User receiver, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInByReceiverWithName",Audio.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("name", name);
		final Audio audio= typedQuery.getSingleResult();
		entityManager.close();
		return audio;
	}

	@Override
	public List<Audio> selectInByName(String name) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findInByName",Audio.class);
		typedQuery.setParameter("name", name);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public void deleteInByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteInByName");
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
		final Query query=entityManager.createNamedQuery("Audio.deleteInByReceiverWithName");
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
	public Audio selectOutBySenderWithName(User sender, String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutBySenderWithName",Audio.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("name", name);
		final Audio audio= typedQuery.getSingleResult();
		entityManager.close();
		return audio;
	}

	@Override
	public List<Audio> selectOutByName(String name) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Audio> typedQuery=entityManager.createNamedQuery("Audio.findOutByName",Audio.class);
		typedQuery.setParameter("name", name);
		final List<Audio> audios= typedQuery.getResultList();
		entityManager.close();
		return audios;
	}

	@Override
	public void deleteOutByName(String name) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Audio.deleteOutByName");
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
		final Query query=entityManager.createNamedQuery("Audio.deleteOutBySenderWithName");
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