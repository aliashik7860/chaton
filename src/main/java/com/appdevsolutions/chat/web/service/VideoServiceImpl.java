package com.appdevsolutions.chat.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.common.model.VideoModel;
import com.appdevsolutions.chat.common.model.VideoType;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.entity.Video;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.dao.repository.VideoRepository;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.exception.VideoNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	DirectoryService userDirectoryService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	VideoRepository videoRepository;
	
	@Override
	public void save(VideoModel videoModel) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(videoModel.getSenderId());
			try{
				final User receiver=userRepository.selectById(videoModel.getReceiverId());
				byte[] buffer=new byte[4096];
				final InputStream in=videoModel.getData();
				try{
					final String senderFilePath=userDirectoryService.getSentVideoDirectoryByUserId(videoModel.getSenderId()).getAbsolutePath()+File.separator+videoModel.getName();
					final File sentFile=new File(senderFilePath);
					sentFile.createNewFile();
					final FileOutputStream fosSent=new FileOutputStream(sentFile);
					final String receiverFilePath=userDirectoryService.getReceivedVideoDirectoryByUserId(videoModel.getReceiverId()).getAbsolutePath()+File.separator+videoModel.getName();
					final File receivedFile=new File(receiverFilePath);
					receivedFile.createNewFile();
					final FileOutputStream fosRecived=new FileOutputStream(receivedFile);
					while(in.read(buffer)!=-1){
						fosSent.write(buffer);
						fosRecived.write(buffer);
					}
					fosSent.flush();
					fosRecived.flush();
					fosSent.close();
					fosRecived.close();
					in.close();
					final Video video=new Video();
					video.setName(videoModel.getName());
					video.setSender(sender);
					video.setSenderFlag(true);
					video.setSenderPath(senderFilePath);
					video.setReceiver(receiver);
					video.setReceiverFlag(true);
					video.setReceiverPath(receiverFilePath);
					video.setContentType(videoModel.getContentType());
					video.setSize(receivedFile.length());
					video.setCreateTimestamp(LocalDateTime.now());
					video.setUpdateTimestamp(LocalDateTime.now());
					videoRepository.persist(video);
				}catch(FileNotFoundException fileNotFoundException){
					
				}catch(IOException ioException){
					
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, videoModel.getReceiverId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, videoModel.getSenderId());
		}
	}

	private VideoModel getVideoModel (Video video){
		final VideoModel videoModel=new VideoModel(video.getId(), video.getSender().getId(), video.getReceiver().getId(), video.getName(), null, video.getSize(), video.getContentType(), video.getCreateTimestamp(), video.getUpdateTimestamp());
		return videoModel;
	}
	private Set<VideoModel> getVideoModels(List<Video> videos){
		final Set<VideoModel> videoModels=new HashSet<VideoModel>(videos.size());
		for(Video video:videos){
			videoModels.add(getVideoModel(video));
		}
		return videoModels;
	}
	
	@Override
	public Set<VideoModel> getAllIn() {
		return getVideoModels(videoRepository.selectAllIn());
	}

	@Override
	public Set<VideoModel> getAllInWithContentType(VideoType contentType) {
		return getVideoModels(videoRepository.selectAllInWithContentType(contentType));
	}

	@Override
	public VideoModel getInById(String id) throws VideoNotFoundException {
		try{
			return getVideoModel(videoRepository.selectInById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.VIDEO_ID, id);
		}
	}

	@Override
	public Set<VideoModel> getInByReceiverId(String receiverId) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getVideoModels(videoRepository.selectInByReceiver(receiver));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<VideoModel> getInByReceiverIdWithText(String receiverId, String text) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getVideoModels(videoRepository.selectInByReceiverWithText(receiver,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<VideoModel> getInByReceiverIdWithTime(String receiverId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getVideoModels(videoRepository.selectInByReceiverWithTime(receiver,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<VideoModel> getInByReceiverIdWithContentType(String receiverId, VideoType contentType)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getVideoModels(videoRepository.selectInByReceiverWithContentType(receiver,contentType));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInById(String id) throws VideoNotFoundException {
		try{
			final Video video=videoRepository.selectInById(id);
			final File file=new File(video.getReceiverPath());
			file.delete();
			videoRepository.deleteInById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				videoRepository.updateInById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.VIDEO_ID, id);
			}
		}
	}

	@Override
	public void deleteInByReceiverId(String receiverId) throws UserNotFoundException, VideoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<Video> videos=videoRepository.selectInByReceiver(receiver);
				for(Video video:videos){
					final File file=new File(video.getReceiverPath());
					file.delete();
				}
				videoRepository.deleteInByReceiver(receiver);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					videoRepository.updateInByReceiver(receiver);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByReceiverIdWithContentType(String receiverId, VideoType contentType)
			throws UserNotFoundException, VideoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<Video> videos=videoRepository.selectInByReceiverWithContentType(receiver, contentType);
				for(Video video:videos){
					final File file=new File(video.getReceiverPath());
					file.delete();
				}
				videoRepository.deleteInByReceiverWithContentType(receiver,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					videoRepository.updateInByReceiverWithContentType(receiver, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<VideoModel> getAllOut() {
		return getVideoModels(videoRepository.selectAllOut());
	}

	@Override
	public Set<VideoModel> getAllOutWithContentType(VideoType contentType) {
		return getVideoModels(videoRepository.selectAllOutWithContentType(contentType));
	}

	@Override
	public VideoModel getOutById(String id) throws VideoNotFoundException {
		try{
			return getVideoModel(videoRepository.selectOutById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.VIDEO_ID, id);
		}
	}

	@Override
	public Set<VideoModel> getOutBySenderId(String senderId) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getVideoModels(videoRepository.selectOutBySender(sender));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<VideoModel> getOutBySenderIdWithText(String senderId, String text) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getVideoModels(videoRepository.selectOutBySenderWithText(sender,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<VideoModel> getOutBySenderIdWithTime(String senderId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getVideoModels(videoRepository.selectOutBySenderWithTime(sender,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<VideoModel> getOutBySenderIdWithContentType(String senderId, VideoType contentType)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getVideoModels(videoRepository.selectOutBySenderWithContentType(sender,contentType));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutById(String id) throws VideoNotFoundException {
		try{
			final Video video=videoRepository.selectOutById(id);
			final File file=new File(video.getSenderPath());
			file.delete();
			videoRepository.deleteOutById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				videoRepository.updateOutById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.VIDEO_ID, id);
			}
		}
	}

	@Override
	public void deleteOutBySenderId(String senderId) throws UserNotFoundException, VideoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<Video> videos=videoRepository.selectOutBySender(sender);
				for(Video video:videos){
					final File file=new File(video.getSenderPath());
					file.delete();
				}
				videoRepository.deleteOutBySender(sender);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					videoRepository.updateOutBySender(sender);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutBySenderIdWithContentType(String senderId, VideoType contentType)
			throws UserNotFoundException, VideoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<Video> videos=videoRepository.selectOutBySenderWithContentType(sender, contentType);
				for(Video video:videos){
					final File file=new File(video.getSenderPath());
					file.delete();
				}
				videoRepository.deleteOutBySenderWithContentType(sender,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					videoRepository.deleteOutBySenderWithContentType(sender, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new VideoNotFoundException("GE_1027", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<VideoModel> getInByName(String name) {
		return getVideoModels(videoRepository.selectInByName(name));
	}

	@Override
	public VideoModel getInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, VideoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				return getVideoModel(videoRepository.selectInByReceiverWithName(receiver, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new VideoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByName(String name) throws VideoNotFoundException {
		try{
			final List<Video> videos=videoRepository.selectInByName(name);
			for(Video video:videos){
				final File file=new File(video.getSenderPath());
				file.delete();
			}
			videoRepository.deleteInByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				videoRepository.updateInByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new VideoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, VideoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final Video video=videoRepository.selectInByReceiverWithName(receiver, name);
				final File file=new File(video.getReceiverPath());
				file.delete();
				videoRepository.deleteInByReceiverWithName(receiver, name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					videoRepository.updateInByReceiverWithName(receiver, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new VideoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	

	@Override
	public VideoModel getOutBySenderIdWithName(String senderId, String name) throws UserNotFoundException,VideoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				return getVideoModel(videoRepository.selectOutBySenderWithName(sender, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new VideoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}	
	}

	@Override
	public void deleteOutByName(String name) throws VideoNotFoundException {
		try{
			final List<Video> videos=videoRepository.selectOutByName(name);
			for(Video video:videos){
				final File file=new File(video.getReceiverPath());
				file.delete();
			}
			videoRepository.deleteOutByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				videoRepository.updateOutByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new VideoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteOutBySenderIdWithName(String senderId, String name)
			throws UserNotFoundException, VideoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final Video video=videoRepository.selectOutBySenderWithName(sender,name);
				final File file=new File(video.getSenderPath());
				file.delete();
				videoRepository.deleteOutBySenderWithName(sender,name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					videoRepository.updateOutBySenderWithName(sender, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new VideoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<VideoModel> getOutByName(String name) {
		return getVideoModels(videoRepository.selectOutByName(name));
	}
	
}