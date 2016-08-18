package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.dao.entity.Message;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface MessageRepository {
	public void persist(Message message);
	public List<Message> selectAllIn();
	public Message selectInById(String id) throws EntityNotFoundException;
	public List<Message> selectInByReceiver(User receiver);
	public List<Message> selectInByReceiverWithText(User receiver,String text);
	public List<Message> selectInByReceiverWithTime(User receiver,LocalDateTime createTimestamp);
	public void deleteInById(String id) throws EntityNotFoundException;
	public void deleteInByReceiver(User receiver)throws EntityNotFoundException;
	public List<Message> selectAllOut();
	public Message selectOutById(String id) throws EntityNotFoundException;
	public List<Message> selectOutBySender(User sender);
	public List<Message> selectOutBySenderWithText(User sender,String text);
	public List<Message> selectOutBySenderWithTime(User sender,LocalDateTime createTimestamp);
	public void deleteOutById(String id) throws EntityNotFoundException;
	public void deleteOutBySender(User sender)throws EntityNotFoundException;
}