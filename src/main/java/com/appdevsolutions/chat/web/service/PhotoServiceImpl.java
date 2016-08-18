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

import com.appdevsolutions.chat.common.model.ImageType;
import com.appdevsolutions.chat.common.model.PhotoModel;
import com.appdevsolutions.chat.dao.entity.Photo;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.PhotoRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.PhotoNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	DirectoryService userDirectoryService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PhotoRepository photoRepository;
	
	
	
	@Override
	public void save(PhotoModel photoModel) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(photoModel.getSenderId());
			try{
				final User receiver=userRepository.selectById(photoModel.getReceiverId());
				byte[] buffer=new byte[4096];
				final InputStream in=photoModel.getData();
				try{
					final String senderFilePath=userDirectoryService.getSentPhotoDirectoryByUserId(photoModel.getSenderId()).getAbsolutePath()+File.separator+photoModel.getName();
					final File sentFile=new File(senderFilePath);
					sentFile.createNewFile();
					final FileOutputStream fosSent=new FileOutputStream(sentFile);
					final String receiverFilePath=userDirectoryService.getReceivedPhotoDirectoryByUserId(photoModel.getReceiverId()).getAbsolutePath()+File.separator+photoModel.getName();
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
					final Photo photo=new Photo();
					photo.setName(photoModel.getName());
					photo.setSender(sender);
					photo.setSenderFlag(true);
					photo.setSenderPath(senderFilePath);
					photo.setReceiver(receiver);
					photo.setReceiverFlag(true);
					photo.setReceiverPath(receiverFilePath);
					photo.setContentType(photoModel.getContentType());
					photo.setSize(receivedFile.length());
					photo.setCreateTimestamp(LocalDateTime.now());
					photo.setUpdateTimestamp(LocalDateTime.now());
					photoRepository.persist(photo);
				}catch(FileNotFoundException fileNotFoundException){
					
				}catch(IOException ioException){
					
				}
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, photoModel.getReceiverId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, photoModel.getSenderId());
		}
	}

	private PhotoModel getPhotoModel (Photo photo){
		final PhotoModel photoModel=new PhotoModel(photo.getId(), photo.getSender().getId(), photo.getReceiver().getId(), photo.getName(), null, photo.getSize(), photo.getContentType(), photo.getCreateTimestamp(), photo.getUpdateTimestamp());
		return photoModel;
	}
	private Set<PhotoModel> getPhotoModels(List<Photo> photos){
		final Set<PhotoModel> photoModels=new HashSet<PhotoModel>(photos.size());
		for(Photo photo:photos){
			photoModels.add(getPhotoModel(photo));
		}
		return photoModels;
	}
	
	@Override
	public Set<PhotoModel> getAllIn() {
		return getPhotoModels(photoRepository.selectAllIn());
	}

	@Override
	public Set<PhotoModel> getAllInWithContentType(ImageType contentType) {
		return getPhotoModels(photoRepository.selectAllInWithContentType(contentType));
	}

	@Override
	public PhotoModel getInById(String id) throws PhotoNotFoundException {
		try{
			return getPhotoModel(photoRepository.selectInById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.PHOTO_ID, id);
		}
	}

	@Override
	public Set<PhotoModel> getInByReceiverId(String receiverId) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getPhotoModels(photoRepository.selectInByReceiver(receiver));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<PhotoModel> getInByReceiverIdWithText(String receiverId, String text) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getPhotoModels(photoRepository.selectInByReceiverWithText(receiver,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<PhotoModel> getInByReceiverIdWithTime(String receiverId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getPhotoModels(photoRepository.selectInByReceiverWithTime(receiver,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<PhotoModel> getInByReceiverIdWithContentType(String receiverId, ImageType contentType)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getPhotoModels(photoRepository.selectInByReceiverWithContentType(receiver,contentType));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInById(String id) throws PhotoNotFoundException {
		try{
			final Photo photo=photoRepository.selectInById(id);
			final File file=new File(photo.getReceiverPath());
			file.delete();
			photoRepository.deleteInById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				photoRepository.updateInById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.PHOTO_ID, id);
			}
		}
	}

	@Override
	public void deleteInByReceiverId(String receiverId) throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<Photo> photos=photoRepository.selectInByReceiver(receiver);
				for(Photo photo:photos){
					final File file=new File(photo.getReceiverPath());
					file.delete();
				}
				photoRepository.deleteInByReceiver(receiver);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					photoRepository.updateInByReceiver(receiver);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByReceiverIdWithContentType(String receiverId, ImageType contentType)
			throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<Photo> photos=photoRepository.selectInByReceiverWithContentType(receiver, contentType);
				for(Photo photo:photos){
					final File file=new File(photo.getReceiverPath());
					file.delete();
				}
				photoRepository.deleteInByReceiverWithContentType(receiver,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					photoRepository.updateInByReceiverWithContentType(receiver, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<PhotoModel> getAllOut() {
		return getPhotoModels(photoRepository.selectAllOut());
	}

	@Override
	public Set<PhotoModel> getAllOutWithContentType(ImageType contentType) {
		return getPhotoModels(photoRepository.selectAllOutWithContentType(contentType));
	}

	@Override
	public PhotoModel getOutById(String id) throws PhotoNotFoundException {
		try{
			return getPhotoModel(photoRepository.selectOutById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.PHOTO_ID, id);
		}
	}

	@Override
	public Set<PhotoModel> getOutBySenderId(String senderId) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getPhotoModels(photoRepository.selectOutBySender(sender));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<PhotoModel> getOutBySenderIdWithText(String senderId, String text) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getPhotoModels(photoRepository.selectOutBySenderWithText(sender,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<PhotoModel> getOutBySenderIdWithTime(String senderId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getPhotoModels(photoRepository.selectOutBySenderWithTime(sender,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<PhotoModel> getOutBySenderIdWithContentType(String senderId, ImageType contentType)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getPhotoModels(photoRepository.selectOutBySenderWithContentType(sender,contentType));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutById(String id) throws PhotoNotFoundException {
		try{
			final Photo photo=photoRepository.selectOutById(id);
			final File file=new File(photo.getSenderPath());
			file.delete();
			photoRepository.deleteOutById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				photoRepository.updateOutById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.PHOTO_ID, id);
			}
		}
	}

	@Override
	public void deleteOutBySenderId(String senderId) throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<Photo> photos=photoRepository.selectOutBySender(sender);
				for(Photo photo:photos){
					final File file=new File(photo.getSenderPath());
					file.delete();
				}
				photoRepository.deleteOutBySender(sender);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					photoRepository.deleteOutBySender(sender);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutBySenderIdWithContentType(String senderId, ImageType contentType)
			throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<Photo> photos=photoRepository.selectOutBySenderWithContentType(sender, contentType);
				for(Photo photo:photos){
					final File file=new File(photo.getSenderPath());
					file.delete();
				}
				photoRepository.deleteOutBySenderWithContentType(sender,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					photoRepository.updateOutBySenderWithContentType(sender, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new PhotoNotFoundException("GE_1023", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public PhotoModel getInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				return getPhotoModel(photoRepository.selectInByReceiverWithName(receiver, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new PhotoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByName(String name) throws PhotoNotFoundException {
		try{
			final List<Photo> photos=photoRepository.selectInByName(name);
			for(Photo photo:photos){
				final File file=new File(photo.getReceiverPath());
				file.delete();
			}
			photoRepository.deleteInByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				photoRepository.updateInByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new PhotoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final Photo photo=photoRepository.selectInByReceiverWithName(receiver, name);
				final File file=new File(photo.getReceiverPath());
				file.delete();
				photoRepository.deleteInByReceiverWithName(receiver, name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					photoRepository.updateInByReceiverWithName(receiver, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new PhotoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<PhotoModel> getOutByName(String name) {
		return getPhotoModels(photoRepository.selectOutByName(name));
	}

	@Override
	public PhotoModel getOutBySenderIdWithName(String senderId, String name) throws UserNotFoundException,PhotoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				return getPhotoModel(photoRepository.selectOutBySenderWithName(sender, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new PhotoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutByName(String name) throws PhotoNotFoundException {
		try{
			final List<Photo> photos=photoRepository.selectOutByName(name);
			for(Photo photo:photos){
				final File file=new File(photo.getSenderPath());
				file.delete();
			}
			photoRepository.deleteOutByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				photoRepository.updateOutByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new PhotoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteOutBySenderIdWithName(String senderId, String name)
			throws UserNotFoundException, PhotoNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final Photo photo=photoRepository.selectOutBySenderWithName(sender,name);
				final File file=new File(photo.getSenderPath());
				file.delete();
				photoRepository.deleteOutBySenderWithName(sender,name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					photoRepository.updateOutBySenderWithName(sender, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new PhotoNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<PhotoModel> getInByName(String name) {
		return getPhotoModels(photoRepository.selectInByName(name));
	}

}
