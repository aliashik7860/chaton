package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Set;

import com.appdevsolutions.chat.common.model.AudioModel;
import com.appdevsolutions.chat.common.model.AudioType;
import com.appdevsolutions.chat.web.exception.AudioNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;

public interface AudioService{
	public void save(AudioModel audioModel)throws UserNotFoundException;
	public Set<AudioModel> getAllIn(Comparator<AudioModel> comparator);
	public Set<AudioModel> getAllInWithContentType(AudioType contentType,Comparator<AudioModel> comparator);
	public AudioModel getInById(String id)throws AudioNotFoundException;
	public Set<AudioModel> getInByName(String name,Comparator<AudioModel> comparator);
	public Set<AudioModel> getInByReceiverId(String receiverId,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public AudioModel getInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,AudioNotFoundException;
	public Set<AudioModel> getInByReceiverIdWithText(String receiverId, String text,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public Set<AudioModel> getInByReceiverIdWithTime(String receiverId,LocalDateTime createTimestamp,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public Set<AudioModel> getInByReceiverIdWithContentType(String receiverId,AudioType contentType,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public void deleteInById(String id)throws AudioNotFoundException;
	public void deleteInByName(String name)throws AudioNotFoundException;
	public void deleteInByReceiverId(String receiverId)throws UserNotFoundException,AudioNotFoundException;
	public void deleteInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,AudioNotFoundException;
	public void deleteInByReceiverIdWithContentType(String receiverId,AudioType contentType)throws UserNotFoundException,AudioNotFoundException;
	public Set<AudioModel> getAllOut(Comparator<AudioModel> comparator);
	public Set<AudioModel> getAllOutWithContentType(AudioType contentType,Comparator<AudioModel> comparator);
	public AudioModel getOutById(String id)throws AudioNotFoundException;
	public Set<AudioModel> getOutByName(String name,Comparator<AudioModel> comparator);
	public Set<AudioModel> getOutBySenderId(String senderId,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public AudioModel getOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,AudioNotFoundException;
	public Set<AudioModel> getOutBySenderIdWithText(String senderId, String text,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public Set<AudioModel> getOutBySenderIdWithTime(String senderId,LocalDateTime createTimestamp,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public Set<AudioModel> getOutBySenderIdWithContentType(String senderId,AudioType contentType,Comparator<AudioModel> comparator)throws UserNotFoundException;
	public void deleteOutById(String id)throws AudioNotFoundException;
	public void deleteOutByName(String name)throws AudioNotFoundException;
	public void deleteOutBySenderId(String senderId)throws UserNotFoundException,AudioNotFoundException;
	public void deleteOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,AudioNotFoundException;
	public void deleteOutBySenderIdWithContentType(String senderId,AudioType contentType)throws UserNotFoundException,AudioNotFoundException;
}