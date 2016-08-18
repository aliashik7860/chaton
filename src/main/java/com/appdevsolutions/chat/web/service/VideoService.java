package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.Set;

import com.appdevsolutions.chat.common.model.VideoModel;
import com.appdevsolutions.chat.common.model.VideoType;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.exception.VideoNotFoundException;

public interface VideoService{
	public void save(VideoModel videoModel)throws UserNotFoundException;
	public Set<VideoModel> getAllIn();
	public Set<VideoModel> getAllInWithContentType(VideoType contentType);
	public VideoModel getInById(String id)throws VideoNotFoundException;
	public Set<VideoModel> getInByName(String name);
	public Set<VideoModel> getInByReceiverId(String receiverId)throws UserNotFoundException;
	public VideoModel getInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,VideoNotFoundException;
	public Set<VideoModel> getInByReceiverIdWithText(String receiverId, String text)throws UserNotFoundException;
	public Set<VideoModel> getInByReceiverIdWithTime(String receiverId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public Set<VideoModel> getInByReceiverIdWithContentType(String receiverId,VideoType contentType)throws UserNotFoundException;
	public void deleteInById(String id)throws VideoNotFoundException;
	public void deleteInByName(String name)throws VideoNotFoundException;
	public void deleteInByReceiverId(String receiverId)throws UserNotFoundException,VideoNotFoundException;
	public void deleteInByReceiverIdWithName(String receiverId,String name)throws UserNotFoundException,VideoNotFoundException;
	public void deleteInByReceiverIdWithContentType(String receiverId,VideoType contentType)throws UserNotFoundException,VideoNotFoundException;
	public Set<VideoModel> getAllOut();
	public Set<VideoModel> getAllOutWithContentType(VideoType contentType);
	public VideoModel getOutById(String id)throws VideoNotFoundException;
	public Set<VideoModel> getOutByName(String name);
	public Set<VideoModel> getOutBySenderId(String senderId)throws UserNotFoundException;
	public VideoModel getOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,VideoNotFoundException;
	public Set<VideoModel> getOutBySenderIdWithText(String senderId, String text)throws UserNotFoundException;
	public Set<VideoModel> getOutBySenderIdWithTime(String senderId,LocalDateTime createTimestamp)throws UserNotFoundException;
	public Set<VideoModel> getOutBySenderIdWithContentType(String senderId,VideoType contentType)throws UserNotFoundException;
	public void deleteOutById(String id)throws VideoNotFoundException;
	public void deleteOutByName(String name)throws VideoNotFoundException;
	public void deleteOutBySenderId(String senderId)throws UserNotFoundException,VideoNotFoundException;
	public void deleteOutBySenderIdWithName(String senderId,String name)throws UserNotFoundException,VideoNotFoundException;
	public void deleteOutBySenderIdWithContentType(String senderId,VideoType contentType)throws UserNotFoundException,VideoNotFoundException;
}