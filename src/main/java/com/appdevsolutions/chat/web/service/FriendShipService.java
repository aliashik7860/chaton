package com.appdevsolutions.chat.web.service;

import java.util.Set;

import com.appdevsolutions.chat.web.exception.FriendShipAlreadyExistException;
import com.appdevsolutions.chat.web.exception.FriendShipNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.FriendShipModel;

public interface FriendShipService {
	public void createFriendShip(FriendShipModel friendShipModel)throws UserNotFoundException,FriendShipAlreadyExistException;
	public FriendShipModel getById(String id)throws FriendShipNotFoundException;
	public Set<FriendShipModel> getAllUnFriends();
	public Set<FriendShipModel> getAllFriends();
	public Set<FriendShipModel> getAllSentRequestsByUserId(String userId)throws UserNotFoundException;
	public Set<FriendShipModel> getAllReceivedRequestsByUserId(String userId)throws UserNotFoundException;
	public Set<FriendShipModel> getAllFriendsByUserId(String userId)throws UserNotFoundException;
	public Set<FriendShipModel> getAllBlockedFriends();
	public Set<FriendShipModel> getAllBlockedFriendsBySenderId(String senderId)throws UserNotFoundException;
	public Set<FriendShipModel> getAllBlockedFriendsByReceiverId(String receiverId)throws UserNotFoundException;
	public Set<FriendShipModel> getAll();
	public void removeById(String id)throws FriendShipNotFoundException;
	public void removeAllSentRequestsByUserId(String userId)throws UserNotFoundException;
	public void removeAllReceivedRequestsByUserId(String userId)throws UserNotFoundException;
	public void makeFriendById(String id)throws FriendShipNotFoundException,FriendShipAlreadyExistException;
	public void makeFriendByUserId(String userId)throws UserNotFoundException,FriendShipNotFoundException;	
	public void makeUnFriendById(String id)throws FriendShipNotFoundException;
}