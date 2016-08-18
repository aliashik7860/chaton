package com.appdevsolutions.chat.web.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.common.model.AudioModel;
import com.appdevsolutions.chat.common.model.AudioType;
import com.appdevsolutions.chat.dao.entity.Audio;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.AudioRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.AudioNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Service
public class AudioServiceImpl implements AudioService {

	@Autowired
	AudioRepository audioRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	DirectoryService userDirectoryService;
	
	@Override
	public void save(AudioModel audioModel) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(audioModel.getSenderId());
			try{
				final User receiver=userRepository.selectById(audioModel.getReceiverId());
				byte[] buffer=new byte[4096];
				final InputStream in=audioModel.getData();
				try{
					final String senderFilePath=userDirectoryService.getSentAudioDirectoryByUserId(audioModel.getSenderId()).getAbsolutePath()+File.separator+audioModel.getName();
					final File sentFile=new File(senderFilePath);
					sentFile.createNewFile();
					final FileOutputStream fosSent=new FileOutputStream(sentFile);
					final String receiverFilePath=userDirectoryService.getReceivedAudioDirectoryByUserId(audioModel.getReceiverId()).getAbsolutePath()+File.separator+audioModel.getName();
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
					final Audio audio=new Audio();
					audio.setName(audioModel.getName());
					audio.setSender(sender);
					audio.setSenderFlag(true);
					audio.setSenderPath(senderFilePath);
					audio.setReceiver(receiver);
					audio.setReceiverFlag(true);
					audio.setReceiverPath(receiverFilePath);
					audio.setContentType(audioModel.getContentType());
					audio.setSize(receivedFile.length());
					audio.setCreateTimestamp(LocalDateTime.now());
					audio.setUpdateTimestamp(LocalDateTime.now());
					audioRepository.persist(audio);
				}catch(FileNotFoundException fileNotFoundException){
					
				}catch(IOException ioException){
					
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, audioModel.getReceiverId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, audioModel.getSenderId());
		}
	}

	private AudioModel getAudioModel (Audio audio){
		final AudioModel audioModel=new AudioModel(audio.getId(), audio.getSender().getId(), audio.getReceiver().getId(), audio.getName(), null, audio.getSize(), audio.getContentType(), audio.getCreateTimestamp(), audio.getUpdateTimestamp());
		return audioModel;
	}
	private Set<AudioModel> getAudioModels(List<Audio> audios,Comparator<AudioModel> comparator){
		final Set<AudioModel> audioModels=new TreeSet<AudioModel>(comparator);
		for(Audio audio:audios){
			audioModels.add(getAudioModel(audio));
		}
		return audioModels;
	}
	
	@Override
	public Set<AudioModel> getAllIn(Comparator<AudioModel> comparator) {
		return getAudioModels(audioRepository.selectAllIn(),comparator);
	}

	@Override
	public Set<AudioModel> getAllInWithContentType(AudioType contentType,Comparator<AudioModel> comparator) {
		return getAudioModels(audioRepository.selectAllInWithContentType(contentType),comparator);
	}

	@Override
	public AudioModel getInById(String id) throws AudioNotFoundException {
		try{
			return getAudioModel(audioRepository.selectInById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_ID, id);
		}
	}

	@Override
	public Set<AudioModel> getInByReceiverId(String receiverId,Comparator<AudioModel> comparator) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getAudioModels(audioRepository.selectInByReceiver(receiver),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<AudioModel> getInByReceiverIdWithText(String receiverId, String text,Comparator<AudioModel> comparator) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getAudioModels(audioRepository.selectInByReceiverWithText(receiver,text),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<AudioModel> getInByReceiverIdWithTime(String receiverId, LocalDateTime createTimestamp,Comparator<AudioModel> comparator)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getAudioModels(audioRepository.selectInByReceiverWithTime(receiver,createTimestamp),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<AudioModel> getInByReceiverIdWithContentType(String receiverId, AudioType contentType,Comparator<AudioModel> comparator)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getAudioModels(audioRepository.selectInByReceiverWithContentType(receiver,contentType),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInById(String id) throws AudioNotFoundException {
		try{
			final Audio audio=audioRepository.selectInById(id);
			final File file=new File(audio.getReceiverPath());
			file.delete();
			audioRepository.deleteInById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				audioRepository.updateInById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_ID, id);
			}
		}
	}

	@Override
	public void deleteInByReceiverId(String receiverId) throws UserNotFoundException, AudioNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<Audio> audios=audioRepository.selectInByReceiver(receiver);
				for(final Audio audio:audios){
					final File file=new File(audio.getReceiverPath());
					file.delete();
				}
				audioRepository.deleteInByReceiver(receiver);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					audioRepository.updateInByReceiver(receiver);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByReceiverIdWithContentType(String receiverId, AudioType contentType)
			throws UserNotFoundException, AudioNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<Audio> audios=audioRepository.selectInByReceiverWithContentType(receiver,contentType);
				for(final Audio audio:audios){
					final File file=new File(audio.getReceiverPath());
					file.delete();
				}
				audioRepository.deleteInByReceiverWithContentType(receiver,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					audioRepository.updateInByReceiverWithContentType(receiver, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<AudioModel> getAllOut(Comparator<AudioModel> comparator) {
		return getAudioModels(audioRepository.selectAllOut(),comparator);
	}

	@Override
	public Set<AudioModel> getAllOutWithContentType(AudioType contentType,Comparator<AudioModel> comparator) {
		return getAudioModels(audioRepository.selectAllOutWithContentType(contentType),comparator);
	}

	@Override
	public AudioModel getOutById(String id) throws AudioNotFoundException {
		try{
			return getAudioModel(audioRepository.selectOutById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_ID, id);
		}
	}

	@Override
	public Set<AudioModel> getOutBySenderId(String senderId,Comparator<AudioModel> comparator) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getAudioModels(audioRepository.selectOutBySender(sender),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<AudioModel> getOutBySenderIdWithText(String senderId, String text,Comparator<AudioModel> comparator) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getAudioModels(audioRepository.selectOutBySenderWithText(sender,text),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<AudioModel> getOutBySenderIdWithTime(String senderId, LocalDateTime createTimestamp,Comparator<AudioModel> comparator)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getAudioModels(audioRepository.selectOutBySenderWithTime(sender,createTimestamp),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<AudioModel> getOutBySenderIdWithContentType(String senderId, AudioType contentType,Comparator<AudioModel> comparator)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getAudioModels(audioRepository.selectOutBySenderWithContentType(sender,contentType),comparator);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutById(String id) throws AudioNotFoundException {
		try{
			final Audio audio=audioRepository.selectOutById(id);
			final File file=new File(audio.getSenderPath());
			file.delete();
			audioRepository.deleteOutById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				audioRepository.updateOutById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_ID, id);
			}
		}
	}

	@Override
	public void deleteOutBySenderId(String senderId) throws UserNotFoundException, AudioNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<Audio> audios=audioRepository.selectOutBySender(sender);
				for(final Audio audio:audios){
					final File file=new File(audio.getSenderPath());
					file.delete();
				}
				audioRepository.deleteOutBySender(sender);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					audioRepository.updateOutBySender(sender);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutBySenderIdWithContentType(String senderId, AudioType contentType)
			throws UserNotFoundException, AudioNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<Audio> audios=audioRepository.selectOutBySenderWithContentType(sender,contentType);
				for(final Audio audio:audios){
					final File file=new File(audio.getReceiverPath());
					file.delete();
				}
				audioRepository.deleteOutBySenderWithContentType(sender,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					audioRepository.updateOutBySenderWithContentType(sender, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<AudioModel> getInByName(String name,Comparator<AudioModel> comparator) {
		return getAudioModels(audioRepository.selectInByName(name),comparator);
	}

	@Override
	public AudioModel getInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, AudioNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				return getAudioModel(audioRepository.selectInByReceiverWithName(receiver, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}


	@Override
	public Set<AudioModel> getOutByName(String name,Comparator<AudioModel> comparator){
		return getAudioModels(audioRepository.selectOutByName(name),comparator);
	}

	@Override
	public void deleteInByName(String name) throws AudioNotFoundException {
		try{
			final List<Audio> audios=audioRepository.selectInByName(name);
			for(Audio audio:audios){
				final File file=new File(audio.getSenderPath());
				file.delete();
			}
			audioRepository.deleteInByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				audioRepository.updateInByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, AudioNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final Audio audio=audioRepository.selectInByReceiverWithName(receiver, name);
				final File file=new File(audio.getReceiverPath());
				file.delete();
				audioRepository.deleteInByReceiverWithName(receiver, name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					audioRepository.updateInByReceiverWithName(receiver, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public AudioModel getOutBySenderIdWithName(String senderId, String name)
			throws UserNotFoundException, AudioNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				return getAudioModel(audioRepository.selectOutBySenderWithName(sender, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutByName(String name) throws AudioNotFoundException {
		try{
			final List<Audio> audios=audioRepository.selectOutByName(name);
			for(Audio audio:audios){
				final File file=new File(audio.getSenderPath());
				file.delete();
			}
			audioRepository.deleteOutByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				audioRepository.updateOutByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteOutBySenderIdWithName(String senderId, String name)
			throws UserNotFoundException, AudioNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final Audio audio=audioRepository.selectOutBySenderWithName(sender,name);
				final File file=new File(audio.getSenderPath());
				file.delete();
				audioRepository.deleteOutBySenderWithName(sender,name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					audioRepository.updateOutBySenderWithName(sender, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new AudioNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}
}