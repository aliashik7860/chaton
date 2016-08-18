package com.appdevsolutions.chat.dao.repository;

import java.util.List;

import com.appdevsolutions.chat.dao.entity.FriendShip;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface FriendRepository {
	public void deleteById(String id) throws EntityNotFoundException;
	public void deleteBySender(User sender) throws EntityNotFoundException;
	public void deleteByReceiver(User receiver) throws EntityNotFoundException;
	public List<FriendShip> selectAll() throws EntityNotFoundException;
	public FriendShip selectById(String id) throws EntityNotFoundException;
	public List<FriendShip> selectAllFriendRelation(User senderOrReceiver) throws EntityNotFoundException;
	public void createFriend(FriendShip friend);
	//public void updateFriend(FriendShip friend);
}