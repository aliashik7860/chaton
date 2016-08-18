package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.entity.BlockState;
import com.appdevsolutions.chat.dao.entity.FriendShip;
import com.appdevsolutions.chat.dao.entity.FriendShipState;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.FriendShipRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.FriendShipAlreadyExistException;
import com.appdevsolutions.chat.web.exception.FriendShipNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.FriendShipModel;

@Service
public class FriendShipServiceImpl implements FriendShipService
{
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FriendShipRepository friendShipRepository;
	
	

	@Override
	public void createFriendShip(FriendShipModel friendShipModel) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(friendShipModel.getSenderId());
			try{
				final User receiver=userRepository.selectById(friendShipModel.getReceiverId());
				FriendShip friendShip=new FriendShip();
				friendShip.setSender(sender);
				friendShip.setReceiver(receiver);
				friendShip.setComment(friendShipModel.getComment());
				friendShip.setFriendShipState(FriendShipState.UNFRIEND);
				friendShip.setBlockState(BlockState.UNBLOCKED);
				friendShip.setCreateTimestamp(LocalDateTime.now());
				friendShip.setUpdateTimestamp(LocalDateTime.now());
				friendShipRepository.persist(friendShip);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, friendShipModel.getReceiverId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, friendShipModel.getSenderId());
		}
	}

	@Override
	public FriendShipModel getById(String id) throws FriendShipNotFoundException {
		try{
			final FriendShip friendShip=friendShipRepository.selectById(id);
			final FriendShipModel friendShipModel=new FriendShipModel(friendShip.getId(),friendShip.getSender().getId(), friendShip.getReceiver().getId(), friendShip.getComment(), friendShip.getFriendShipState(), friendShip.getBlockState(), friendShip.getCreateTimestamp(), friendShip.getUpdateTimestamp());
			return friendShipModel;
		}catch(EntityNotFoundException entityNotFoundException){
			throw new FriendShipNotFoundException("GE_1017", ChatOnWebConstants.FRIEND_SHIP_ID, id);
		}
	}
	
	@Override
	public Set<FriendShipModel> getAll() {
		return getFriendShipModels(friendShipRepository.selectAll());
	}
	
	private Set<FriendShipModel> getFriendShipModels(List<FriendShip> friendShips){
		final Set<FriendShipModel> friendShipModels=new HashSet<FriendShipModel>(friendShips.size());
		for(FriendShip friendShip:friendShips){
			final FriendShipModel friendShipModel=new FriendShipModel(friendShip.getId(), friendShip.getSender().getId(), friendShip.getReceiver().getId(), friendShip.getComment(), friendShip.getFriendShipState(), friendShip.getBlockState(), friendShip.getCreateTimestamp(), friendShip.getUpdateTimestamp());
			friendShipModels.add(friendShipModel);
		}
		return friendShipModels;
	}

	@Override
	public Set<FriendShipModel> getAllUnFriends() {
		return getFriendShipModels(friendShipRepository.selectAllUnFriends());
	}

	@Override
	public Set<FriendShipModel> getAllFriends() {
		return getFriendShipModels(friendShipRepository.selectAllFriends());
	}

	@Override
	public Set<FriendShipModel> getAllSentRequestsByUserId(String userId) throws UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			return getFriendShipModels(friendShipRepository.selectAllSentRequestsByUser(user));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public Set<FriendShipModel> getAllReceivedRequestsByUserId(String userId) throws UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			return getFriendShipModels(friendShipRepository.selectAllReceivedRequestsByUser(user));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public Set<FriendShipModel> getAllFriendsByUserId(String userId) throws UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			return getFriendShipModels(friendShipRepository.selectAllFriendsByUser(user));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public void removeById(String id) throws FriendShipNotFoundException {
		final FriendShipModel friendShipModel=getById(id);
		if(friendShipModel.getFriendShipState().equals(FriendShipState.UNFRIEND)){
			try{
				friendShipRepository.deleteById(id);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new FriendShipNotFoundException("GE_1017", ChatOnWebConstants.FRIEND_SHIP_ID, id);
			}
		}else{
			throw new FriendShipNotFoundException("GE_1018", ChatOnWebConstants.FRIEND_SHIP_ID, id);
		}
	}

	@Override
	public void removeAllSentRequestsByUserId(String userId) throws UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			friendShipRepository.deleteAllSentRequestsByUser(user);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public void removeAllReceivedRequestsByUserId(String userId) throws UserNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			friendShipRepository.deleteAllReceivedRequestsByUser(user);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public void makeFriendById(String id) throws FriendShipNotFoundException, FriendShipAlreadyExistException {
		try{
			friendShipRepository.updateFriendById(id,FriendShipState.FRIEND);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new FriendShipNotFoundException("GE_1017", ChatOnWebConstants.FRIEND_SHIP_ID, id);
		}
	}

	@Override
	public void makeFriendByUserId(String userId) throws UserNotFoundException, FriendShipNotFoundException {
		try{
			final User user=userRepository.selectById(userId);
			try{
				friendShipRepository.updateFriendByUser(user,FriendShipState.FRIEND);
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1017", ChatOnWebConstants.USER_ID, userId);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}
	}

	@Override
	public void makeUnFriendById(String id) throws FriendShipNotFoundException {
		try{
			friendShipRepository.updateFriendById(id,FriendShipState.UNFRIEND);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new FriendShipNotFoundException("GE_1017", ChatOnWebConstants.FRIEND_SHIP_ID, id);
		}
	}

	@Override
	public Set<FriendShipModel> getAllBlockedFriends() {
		return getFriendShipModels(friendShipRepository.selectAllBlockedFriends());
	}

	@Override
	public Set<FriendShipModel> getAllBlockedFriendsBySenderId(String senderId) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getFriendShipModels(friendShipRepository.selectAllBlockedFriendsBySender(sender));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<FriendShipModel> getAllBlockedFriendsByReceiverId(String receiverId) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getFriendShipModels(friendShipRepository.selectAllBlockedFriendsByReceiver(receiver));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}
}