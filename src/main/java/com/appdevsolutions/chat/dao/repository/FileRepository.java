package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.common.model.FileType;
import com.appdevsolutions.chat.dao.entity.File;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface FileRepository{
	public void persist(File file);
	public List<File> selectAllIn();
	public File selectInById(String id) throws EntityNotFoundException;
	public File selectInByReceiverWithName(User receiver,String name) throws EntityNotFoundException;
	public List<File> selectInByReceiver(User receiver);
	public List<File> selectInByName(String name);
	public List<File> selectInByReceiverWithText(User receiver,String text);
	public List<File> selectInByReceiverWithTime(User receiver,LocalDateTime createTimestamp);
	public void updateInById(String id)throws EntityNotFoundException;
	public void updateInByName(String name)throws EntityNotFoundException;
	public void updateInByReceiver(User receiver)throws EntityNotFoundException;
	public void updateInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public void deleteInById(String id) throws EntityNotFoundException;
	public void deleteInByName(String name)throws EntityNotFoundException;
	public void deleteInByReceiver(User receiver)throws EntityNotFoundException;
	public void deleteInByReceiverWithName(User receiver,String name)throws EntityNotFoundException;
	public List<File> selectAllOut();
	public File selectOutById(String id) throws EntityNotFoundException;
	public File selectOutBySenderWithName(User sender,String name) throws EntityNotFoundException;
	public List<File> selectOutBySender(User sender);
	public List<File> selectOutByName(String name);
	public List<File> selectOutBySenderWithText(User sender,String text);
	public List<File> selectOutBySenderWithTime(User sender,LocalDateTime createTimestamp);
	public void updateOutById(String id) throws EntityNotFoundException;
	public void updateOutByName(String name)throws EntityNotFoundException;
	public void updateOutBySender(User sender)throws EntityNotFoundException;
	public void updateOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public void deleteOutById(String id) throws EntityNotFoundException;
	public void deleteOutByName(String name)throws EntityNotFoundException;
	public void deleteOutBySender(User sender)throws EntityNotFoundException;
	public void deleteOutBySenderWithName(User sender,String name)throws EntityNotFoundException;
	public List<File> selectAllInWithContentType(FileType contentType);
	public List<File> selectInByReceiverWithContentType(User receiver,FileType contentType);
	public void updateInByReceiverWithContentType(User receiver,FileType contentType) throws EntityNotFoundException;
	public void deleteInByReceiverWithContentType(User receiver,FileType contentType) throws EntityNotFoundException;
	public List<File> selectAllOutWithContentType(FileType contentType);
	public List<File> selectOutBySenderWithContentType(User sender,FileType contentType);
	public void updateOutBySenderWithContentType(User sender,FileType contentType) throws EntityNotFoundException;
	public void deleteOutBySenderWithContentType(User sender,FileType contentType) throws EntityNotFoundException;
}
