package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.common.model.AudioType;
import com.appdevsolutions.chat.dao.entity.Audio;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface AudioRepository{
	public void persist(Audio audio);
	public List<Audio> selectAllIn();
	public Audio selectInById(String id) throws EntityNotFoundException;
	public Audio selectInByReceiverWithName(User receiver,String name) throws EntityNotFoundException;
	public List<Audio> selectInByReceiver(User receiver);
	public List<Audio> selectInByName(String name);
	public List<Audio> selectInByReceiverWithText(User receiver,String text);
	public List<Audio> selectInByReceiverWithTime(User receiver,LocalDateTime createTimestamp);
	public void updateInById(String id)throws EntityNotFoundException;
	public void updateInByName(String name)throws EntityNotFoundException;
	public void updateInByReceiver(User receiver)throws EntityNotFoundException;
	public void updateInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public void deleteInById(String id) throws EntityNotFoundException;
	public void deleteInByName(String name)throws EntityNotFoundException;
	public void deleteInByReceiver(User receiver)throws EntityNotFoundException;
	public void deleteInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public List<Audio> selectAllOut();
	public Audio selectOutById(String id) throws EntityNotFoundException;
	public Audio selectOutBySenderWithName(User sender,String name) throws EntityNotFoundException;
	public List<Audio> selectOutBySender(User sender);
	public List<Audio> selectOutByName(String name);
	public List<Audio> selectOutBySenderWithText(User sender,String text);
	public List<Audio> selectOutBySenderWithTime(User sender,LocalDateTime createTimestamp);
	public void updateOutById(String id) throws EntityNotFoundException;
	public void updateOutByName(String name)throws EntityNotFoundException;
	public void updateOutBySender(User sender)throws EntityNotFoundException;
	public void updateOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public void deleteOutById(String id) throws EntityNotFoundException;
	public void deleteOutByName(String name)throws EntityNotFoundException;
	public void deleteOutBySender(User sender)throws EntityNotFoundException;
	public void deleteOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public List<Audio> selectAllInWithContentType(AudioType contentType);
	public List<Audio> selectInByReceiverWithContentType(User receiver,AudioType contentType);
	public void updateInByReceiverWithContentType(User receiver,AudioType contentType) throws EntityNotFoundException;
	public void deleteInByReceiverWithContentType(User receiver,AudioType contentType) throws EntityNotFoundException;
	public List<Audio> selectAllOutWithContentType(AudioType contentType);
	public List<Audio> selectOutBySenderWithContentType(User sender,AudioType contentType);
	public void updateOutBySenderWithContentType(User sender,AudioType contentType) throws EntityNotFoundException;
	public void deleteOutBySenderWithContentType(User sender,AudioType contentType) throws EntityNotFoundException;
}