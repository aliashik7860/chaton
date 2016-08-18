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

import com.appdevsolutions.chat.dao.entity.BlockState;
import com.appdevsolutions.chat.dao.entity.FriendShip;
import com.appdevsolutions.chat.dao.entity.FriendShipState;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

@Repository
public class FriendShipRepositoryImpl implements FriendShipRepository{

	private static final Logger LOGGER=LoggerFactory.getLogger(FriendShipRepositoryImpl.class);
	
	@Autowired 
	EntityManagerFactory entityManagerFactory;
	
	@Override
	public void persist(FriendShip friendShip) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(friendShip);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public FriendShip selectById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findById",FriendShip.class);
		typedQuery.setParameter("id", id);
		try{
			return typedQuery.getSingleResult();
		}catch(NoResultException noResultException){
			throw new EntityNotFoundException("FriendShip Entity not found Exception");
		}
	}
	
	@Override
	public List<FriendShip> selectAll() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAll",FriendShip.class);
		return typedQuery.getResultList();
	}

	@Override
	public List<FriendShip> selectAllUnFriends() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllUnFriends",FriendShip.class);
		typedQuery.setParameter("friendShipState", FriendShipState.UNFRIEND);
		return typedQuery.getResultList();
	}

	@Override
	public List<FriendShip> selectAllFriends() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllFriends",FriendShip.class);
		typedQuery.setParameter("friendShipState", FriendShipState.FRIEND);
		typedQuery.setParameter("blockState", BlockState.UNBLOCKED);
		return typedQuery.getResultList();
	}

	@Override
	public List<FriendShip> selectAllSentRequestsByUser(User user) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllSentRequestsByUser",FriendShip.class);
		typedQuery.setParameter("sender", user);
		typedQuery.setParameter("friendShipState", FriendShipState.UNFRIEND);
		return typedQuery.getResultList();
	}

	@Override
	public List<FriendShip> selectAllReceivedRequestsByUser(User user){
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllReceivedRequestsByUser",FriendShip.class);
		typedQuery.setParameter("receiver", user);
		typedQuery.setParameter("friendShipState", FriendShipState.UNFRIEND);
		return typedQuery.getResultList();
	}

	@Override
	public List<FriendShip> selectAllFriendsByUser(User user) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllFriendsByUser",FriendShip.class);
		typedQuery.setParameter("sender", user);
		typedQuery.setParameter("receiver", user);
		typedQuery.setParameter("friendShipState", FriendShipState.FRIEND);
		typedQuery.setParameter("blockState", BlockState.UNBLOCKED);
		return typedQuery.getResultList();
	}

	@Override
	public void deleteById(String id) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("FriendShip.deleteById");
		query.setParameter("id", id);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("FriendShip Entity not found with id : "+id);
		}
	}

	@Override
	public void deleteAllSentRequestsByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("FriendShip.deleteAllSentRequestByUser");
		query.setParameter("sender", user);
		query.setParameter("friendShipState", FriendShipState.UNFRIEND);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("FriendShip Entity not found with userId : "+user.getId());
		}
	}

	@Override
	public void deleteAllReceivedRequestsByUser(User user) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("FriendShip.deleteAllReceiverRequestByUser");
		query.setParameter("receiver", user);
		query.setParameter("friendShipState", FriendShipState.UNFRIEND);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("FriendShip Entity not found with userId : "+user.getId());
		}
	}

	@Override
	public void updateFriendById(String id,FriendShipState friendShipState) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("FriendShip.updateFriendById");
		query.setParameter("id", id);
		query.setParameter("friendShipState", friendShipState);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		LOGGER.info("updating friendShip with id : "+id+" ,friendShipState : "+friendShipState+" and status : "+status);
		if(status==0){
			throw new EntityNotFoundException("FriendShip Entity not found with id : "+id);
		}
	}

	@Override
	public void updateFriendByUser(User user,FriendShipState friendShipState) throws EntityNotFoundException {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final Query query=entityManager.createNamedQuery("FriendShip.updateFriendByUser");
		query.setParameter("receiver", user);
		query.setParameter("friendShipState", friendShipState);
		entityManager.getTransaction().begin();
		int status=query.executeUpdate();
		entityManager.getTransaction().commit();
		entityManager.close();
		if(status==0){
			throw new EntityNotFoundException("FriendShip Entity not found with userId : "+user.getId());
		}
	}

	@Override
	public List<FriendShip> selectAllBlockedFriends() {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllBlockedFriends",FriendShip.class);
		typedQuery.setParameter("friendShipState", FriendShipState.FRIEND);
		typedQuery.setParameter("blockState", BlockState.BLOCKED);
		return typedQuery.getResultList();
	}

	@Override
	public List<FriendShip> selectAllBlockedFriendsBySender(User sender) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllBlockedFriendsBySender",FriendShip.class);
		typedQuery.setParameter("sender", sender);
		typedQuery.setParameter("friendShipState", FriendShipState.FRIEND);
		typedQuery.setParameter("blockState", BlockState.BLOCKED);
		return typedQuery.getResultList();
	}
	@Override
	public List<FriendShip> selectAllBlockedFriendsByReceiver(User receiver) {
		final EntityManager entityManager=entityManagerFactory.createEntityManager();
		final TypedQuery<FriendShip> typedQuery=entityManager.createNamedQuery("FriendShip.findAllBlockedFriendsByReceiver",FriendShip.class);
		typedQuery.setParameter("receiver", receiver);
		typedQuery.setParameter("friendShipState", FriendShipState.FRIEND);
		typedQuery.setParameter("blockState", BlockState.BLOCKED);
		return typedQuery.getResultList();
	}
}