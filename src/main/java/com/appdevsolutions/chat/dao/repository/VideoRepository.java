package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.common.model.VideoType;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.entity.Video;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface VideoRepository{
	public void persist(Video video);
	public List<Video> selectAllIn();
	public Video selectInById(String id) throws EntityNotFoundException;
	public Video selectInByReceiverWithName(User receiver,String name) throws EntityNotFoundException;
	public List<Video> selectInByReceiver(User receiver);
	public List<Video> selectInByName(String name);
	public List<Video> selectInByReceiverWithText(User receiver,String text);
	public List<Video> selectInByReceiverWithTime(User receiver,LocalDateTime createTimestamp);
	public void updateInById(String id)throws EntityNotFoundException;
	public void updateInByName(String name)throws EntityNotFoundException;
	public void updateInByReceiver(User receiver)throws EntityNotFoundException;
	public void updateInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public void deleteInById(String id) throws EntityNotFoundException;
	public void deleteInByName(String name)throws EntityNotFoundException;
	public void deleteInByReceiver(User receiver)throws EntityNotFoundException;
	public void deleteInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public List<Video> selectAllOut();
	public Video selectOutById(String id) throws EntityNotFoundException;
	public Video selectOutBySenderWithName(User sender,String name) throws EntityNotFoundException;
	public List<Video> selectOutBySender(User sender);
	public List<Video> selectOutByName(String name);
	public List<Video> selectOutBySenderWithText(User sender,String text);
	public List<Video> selectOutBySenderWithTime(User sender,LocalDateTime createTimestamp);
	public void updateOutById(String id) throws EntityNotFoundException;
	public void updateOutByName(String name)throws EntityNotFoundException;
	public void updateOutBySender(User sender)throws EntityNotFoundException;
	public void updateOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public void deleteOutById(String id) throws EntityNotFoundException;
	public void deleteOutByName(String name)throws EntityNotFoundException;
	public void deleteOutBySender(User sender)throws EntityNotFoundException;
	public void deleteOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public List<Video> selectAllInWithContentType(VideoType contentType);
	public List<Video> selectInByReceiverWithContentType(User receiver,VideoType contentType);
	public void updateInByReceiverWithContentType(User receiver,VideoType contentType) throws EntityNotFoundException;
	public void deleteInByReceiverWithContentType(User receiver,VideoType contentType) throws EntityNotFoundException;
	public List<Video> selectAllOutWithContentType(VideoType contentType);
	public List<Video> selectOutBySenderWithContentType(User sender,VideoType contentType);
	public void updateOutBySenderWithContentType(User sender,VideoType contentType) throws EntityNotFoundException;
	public void deleteOutBySenderWithContentType(User sender,VideoType contentType) throws EntityNotFoundException;
	
}
