package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.common.model.ImageType;
import com.appdevsolutions.chat.dao.entity.Photo;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface PhotoRepository{
	public void persist(Photo photo);
	public List<Photo> selectAllIn();
	public Photo selectInById(String id) throws EntityNotFoundException;
	public Photo selectInByReceiverWithName(User receiver,String name) throws EntityNotFoundException;
	public List<Photo> selectInByReceiver(User receiver);
	public List<Photo> selectInByName(String name);
	public List<Photo> selectInByReceiverWithText(User receiver,String text);
	public List<Photo> selectInByReceiverWithTime(User receiver,LocalDateTime createTimestamp);
	public void updateInById(String id)throws EntityNotFoundException;
	public void updateInByName(String name)throws EntityNotFoundException;
	public void updateInByReceiver(User receiver)throws EntityNotFoundException;
	public void updateInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public void deleteInById(String id) throws EntityNotFoundException;
	public void deleteInByName(String name)throws EntityNotFoundException;
	public void deleteInByReceiver(User receiver)throws EntityNotFoundException;
	public void deleteInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public List<Photo> selectAllOut();
	public Photo selectOutById(String id) throws EntityNotFoundException;
	public Photo selectOutBySenderWithName(User sender,String name) throws EntityNotFoundException;
	public List<Photo> selectOutBySender(User sender);
	public List<Photo> selectOutByName(String name);
	public List<Photo> selectOutBySenderWithText(User sender,String text);
	public List<Photo> selectOutBySenderWithTime(User sender,LocalDateTime createTimestamp);
	public void updateOutById(String id) throws EntityNotFoundException;
	public void updateOutByName(String name)throws EntityNotFoundException;
	public void updateOutBySender(User sender)throws EntityNotFoundException;
	public void updateOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public void deleteOutById(String id) throws EntityNotFoundException;
	public void deleteOutByName(String name)throws EntityNotFoundException;
	public void deleteOutBySender(User sender)throws EntityNotFoundException;
	public void deleteOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public List<Photo> selectAllInWithContentType(ImageType contentType);
	public List<Photo> selectInByReceiverWithContentType(User receiver,ImageType contentType);
	public void updateInByReceiverWithContentType(User receiver,ImageType contentType) throws EntityNotFoundException;
	public void deleteInByReceiverWithContentType(User receiver,ImageType contentType) throws EntityNotFoundException;
	public List<Photo> selectAllOutWithContentType(ImageType contentType);
	public List<Photo> selectOutBySenderWithContentType(User sender,ImageType contentType);
	public void updateOutBySenderWithContentType(User sender,ImageType contentType) throws EntityNotFoundException;
	public void deleteOutBySenderWithContentType(User sender,ImageType contentType) throws EntityNotFoundException;
}
