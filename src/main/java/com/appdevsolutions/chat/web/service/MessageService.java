package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.web.exception.MessageNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.MessageModel;

public interface MessageService{
	public void save(MessageModel messageModel)throws UserNotFoundException;
	public List<MessageModel> getAllIn();
	public MessageModel getInById(String id)throws MessageNotFoundException;
	public List<MessageModel> getInByReceiverId(String receiverId)throws UserNotFoundException;
	public List<MessageModel> getInByReceiverIdWithText(String receiverId, String text)throws UserNotFoundException;
	public List<MessageModel> getInByReceiverIdWithTime(String receiverId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public void deleteInById(String id)throws MessageNotFoundException;
	public void deleteInByReceiverId(String receiverId)throws UserNotFoundException,MessageNotFoundException;
	public List<MessageModel> getAllOut();
	public MessageModel getOutById(String id)throws MessageNotFoundException;
	public List<MessageModel> getOutBySenderId(String senderId)throws UserNotFoundException;
	public List<MessageModel> getOutBySenderIdWithText(String senderId, String text)throws UserNotFoundException;
	public List<MessageModel> getOutBySenderIdWithTime(String senderId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public void deleteOutById(String id)throws MessageNotFoundException;
	public void deleteOutBySenderId(String senderId)throws UserNotFoundException,MessageNotFoundException;	
}
