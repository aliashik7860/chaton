package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.Set;

import com.appdevsolutions.chat.common.model.ImageType;
import com.appdevsolutions.chat.common.model.PhotoModel;
import com.appdevsolutions.chat.web.exception.PhotoNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;

public interface PhotoService{
	public void save(PhotoModel photoModel)throws UserNotFoundException;
	public Set<PhotoModel> getAllIn();
	public Set<PhotoModel> getAllInWithContentType(ImageType contentType);
	public PhotoModel getInById(String id)throws PhotoNotFoundException;
	public Set<PhotoModel> getInByName(String name);
	public Set<PhotoModel> getInByReceiverId(String receiverId)throws UserNotFoundException;
	public PhotoModel getInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,PhotoNotFoundException;
	public Set<PhotoModel> getInByReceiverIdWithText(String receiverId, String text)throws UserNotFoundException;
	public Set<PhotoModel> getInByReceiverIdWithTime(String receiverId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public Set<PhotoModel> getInByReceiverIdWithContentType(String receiverId,ImageType contentType)throws UserNotFoundException;
	public void deleteInById(String id)throws PhotoNotFoundException;
	public void deleteInByName(String name)throws PhotoNotFoundException;
	public void deleteInByReceiverId(String receiverId)throws UserNotFoundException,PhotoNotFoundException;
	public void deleteInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,PhotoNotFoundException;
	public void deleteInByReceiverIdWithContentType(String receiverId,ImageType contentType)throws UserNotFoundException,PhotoNotFoundException;
	public Set<PhotoModel> getAllOut();
	public Set<PhotoModel> getAllOutWithContentType(ImageType contentType);
	public PhotoModel getOutById(String id)throws PhotoNotFoundException;
	public Set<PhotoModel> getOutByName(String name);
	public Set<PhotoModel> getOutBySenderId(String senderId)throws UserNotFoundException;
	public PhotoModel getOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,PhotoNotFoundException;
	public Set<PhotoModel> getOutBySenderIdWithText(String senderId, String text)throws UserNotFoundException;
	public Set<PhotoModel> getOutBySenderIdWithTime(String senderId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public Set<PhotoModel> getOutBySenderIdWithContentType(String senderId,ImageType contentType)throws UserNotFoundException;
	public void deleteOutById(String id)throws PhotoNotFoundException;
	public void deleteOutByName(String name)throws PhotoNotFoundException;
	public void deleteOutBySenderId(String senderId)throws UserNotFoundException,PhotoNotFoundException;
	public void deleteOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,PhotoNotFoundException;
	public void deleteOutBySenderIdWithContentType(String senderId,ImageType contentType)throws UserNotFoundException,PhotoNotFoundException;
}