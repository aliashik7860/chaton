package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.Set;

import com.appdevsolutions.chat.common.model.FileModel;
import com.appdevsolutions.chat.common.model.FileType;
import com.appdevsolutions.chat.web.exception.FileNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;

public interface FileService{
	public void save(FileModel fileModel)throws UserNotFoundException;
	public Set<FileModel> getAllIn();
	public Set<FileModel> getAllInWithContentType(FileType contentType);
	public FileModel getInById(String id)throws FileNotFoundException;
	public Set<FileModel> getInByName(String name);
	public Set<FileModel> getInByReceiverId(String receiverId)throws UserNotFoundException;
	public FileModel getInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,FileNotFoundException;
	public Set<FileModel> getInByReceiverIdWithText(String receiverId, String text)throws UserNotFoundException;
	public Set<FileModel> getInByReceiverIdWithTime(String receiverId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public Set<FileModel> getInByReceiverIdWithContentType(String receiverId,FileType contentType)throws UserNotFoundException;
	public void deleteInById(String id)throws FileNotFoundException;
	public void deleteInByName(String name)throws FileNotFoundException;
	public void deleteInByReceiverId(String receiverId)throws UserNotFoundException,FileNotFoundException;
	public void deleteInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,FileNotFoundException;
	public void deleteInByReceiverIdWithContentType(String receiverId,FileType contentType)throws UserNotFoundException,FileNotFoundException;
	public Set<FileModel> getAllOut();
	public Set<FileModel> getAllOutWithContentType(FileType contentType);
	public FileModel getOutById(String id)throws FileNotFoundException;
	public Set<FileModel> getOutByName(String name);
	public Set<FileModel> getOutBySenderId(String senderId)throws UserNotFoundException;
	public FileModel getOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,FileNotFoundException;
	public Set<FileModel> getOutBySenderIdWithText(String senderId, String text)throws UserNotFoundException;
	public Set<FileModel> getOutBySenderIdWithTime(String senderId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public Set<FileModel> getOutBySenderIdWithContentType(String senderId,FileType contentType)throws UserNotFoundException;
	public void deleteOutById(String id)throws FileNotFoundException;
	public void deleteOutByName(String name)throws FileNotFoundException;
	public void deleteOutBySenderId(String senderId)throws UserNotFoundException,FileNotFoundException;
	public void deleteOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,FileNotFoundException;
	public void deleteOutBySenderIdWithContentType(String senderId,FileType contentType)throws UserNotFoundException,FileNotFoundException;
}