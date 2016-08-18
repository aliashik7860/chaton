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

import com.appdevsolutions.chat.dao.entity.Message;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class MessageRepositoryImpl implements MessageRepository{

	private static final Logger LOGGER=LoggerFactory.getLogger(MessageRepositoryImpl.class);
	
	@Autowired
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public void persist(Message message) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(message);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public List<Message> selectAllIn() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findAllIn",Message.class);
		final List<Message> messages= typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	@Override
	public Message selectInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findInById",Message.class);
		typedQuery.setParameter("id", id);
		try{
			final Message message= typedQuery.getSingleResult();
			entityManager.close();
			return message;
		}catch(NoResultException noResultException){
			throw new EntityNotFoundException("Message Entity not found with id : "+id);
		}
				
	}

	@Override
	public List<Message> selectOutBySender(User sender){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findOutBySender",Message.class);
		typedQuery.setParameter("sender", sender);
		final List<Message> messages= typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	@Override
	public List<Message> selectOutBySenderWithText(User sender, String text) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findOutBySenderWithText",Message.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("message", "%"+text+"%");
		final List<Message> messages=typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	@Override
	public List<Message> selectOutBySenderWithTime(User sender, LocalDateTime createTimestamp) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findOutBySenderWithTime",Message.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Message> messages=typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	private void updateInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.updateInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Message Entity not found with id : "+id);
		}
	}

	private void updateOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.updateOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Message Entity not found with senderId : "+sender.getId());
		}
	}

	@Override
	public void deleteInById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.deleteInById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			LOGGER.info("deleting message with id : "+id+" with status : "+status);
			updateInById(id);
		}
	}

	@Override
	public void deleteOutBySender(User sender) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.deleteOutBySender");
		query.setParameter("sender", sender);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			updateOutBySender(sender);
		}
	}

	@Override
	public List<Message> selectAllOut() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findAllOut",Message.class);
		final List<Message> messages= typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	@Override
	public Message selectOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findOutById",Message.class);
		typedQuery.setParameter("id", id);
		try{
			final Message message=typedQuery.getSingleResult();
			entityManager.close();
			return message;
		}catch(NoResultException noResultException){
			entityManager.close();
			throw new EntityNotFoundException("Message Entity not found with id : "+id);
		}
	}

	@Override
	public List<Message> selectInByReceiver(User receiver){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findInByReceiver",Message.class);
		typedQuery.setParameter("receiver", receiver);
		final List<Message> messages= typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	@Override
	public List<Message> selectInByReceiverWithText(User receiver, String text){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findInByReceiverWithText",Message.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("message", "%"+text+"%");
		final List<Message> messages= typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	@Override
	public List<Message> selectInByReceiverWithTime(User receiver, LocalDateTime createTimestamp){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<Message> typedQuery=entityManager.createNamedQuery("Message.findInByReceiverWithTime",Message.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("createTimestamp", createTimestamp);
		final List<Message> messages= typedQuery.getResultList();
		entityManager.close();
		return messages;
	}

	private void updateOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.updateOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Message Entity not found with id : "+id);
		}
	}

	private void updateInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.updateInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("Message Entity not found with receiverId : "+receiver.getId());
		}
	}

	@Override
	public void deleteOutById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.deleteOutById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			updateOutById(id);
		}
	}

	@Override
	public void deleteInByReceiver(User receiver) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("Message.deleteInByReceiver");
		query.setParameter("receiver", receiver);
		entityManager.getTransaction().begin();
		final int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			updateInByReceiver(receiver);
		}
	}
	
}