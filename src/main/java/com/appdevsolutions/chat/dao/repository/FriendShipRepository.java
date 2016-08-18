package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import com.appdevsolutions.chat.dao.entity.FriendShip;
import com.appdevsolutions.chat.dao.entity.FriendShipState;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface FriendShipRepository {
	public void persist(FriendShip friendShip);
	public FriendShip selectById(String id)throws EntityNotFoundException;
	public List<FriendShip> selectAllUnFriends();
	public List<FriendShip> selectAllFriends();
	public List<FriendShip> selectAllBlockedFriends();
	public List<FriendShip> selectAllBlockedFriendsBySender(User sender);
	public List<FriendShip> selectAllBlockedFriendsByReceiver(User receiver);
	public List<FriendShip> selectAllSentRequestsByUser(User user);
	public List<FriendShip> selectAllReceivedRequestsByUser(User user);
	public List<FriendShip> selectAllFriendsByUser(User user);
	public List<FriendShip> selectAll();
	public void deleteById(String id) throws EntityNotFoundException;
	public void deleteAllSentRequestsByUser(User user) throws EntityNotFoundException;
	public void deleteAllReceivedRequestsByUser(User user) throws EntityNotFoundException;
	public void updateFriendById(String id,FriendShipState friendShipState)throws EntityNotFoundException;
	public void updateFriendByUser(User user,FriendShipState friendShipState)throws EntityNotFoundException;
}