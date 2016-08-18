package com.appdevsolutions.chat.web.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.common.model.FileModel;
import com.appdevsolutions.chat.common.model.FileType;
import com.appdevsolutions.chat.dao.entity.File;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.FileRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.FileNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Service
public class FileServiceImpl implements FileService {

	@Autowired
	DirectoryService directoryService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	FileRepository fileRepository;
	
	@Override
	public void save(FileModel fileModel) throws UserNotFoundException {
		try{
				final User sender=userRepository.selectById(fileModel.getSenderId());
				final User receiver=userRepository.selectById(fileModel.getReceiverId());
				
				final File file=new File();
				file.setName(fileModel.getName());
				file.setSender(sender);
				file.setSenderFlag(true);
				file.setReceiver(receiver);
				file.setReceiverFlag(true);
				file.setContentType(fileModel.getContentType());
				//file.setSize(in.);
				file.setCreateTimestamp(LocalDateTime.now());
				file.setUpdateTimestamp(LocalDateTime.now());
				fileRepository.persist(file);
				final InputStream in=fileModel.getData();
				directoryService.saveProfilePhoto(in,getInByName(file.getName()));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, fileModel.getReceiverId());
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, fileModel.getSenderId());
		}
	}

	private FileModel getFileModel (File file){
		final FileModel fileModel=new FileModel(file.getId(), file.getSender().getId(), file.getReceiver().getId(), file.getName(), null, file.getSize(), file.getContentType(), file.getCreateTimestamp(), file.getUpdateTimestamp());
		return fileModel;
	}
	private Set<FileModel> getFileModels(List<File> files){
		final Set<FileModel> fileModels=new HashSet<FileModel>(files.size());
		for(File file:files){
			fileModels.add(getFileModel(file));
		}
		return fileModels;
	}
	
	@Override
	public Set<FileModel> getAllIn() {
		return getFileModels(fileRepository.selectAllIn());
	}

	@Override
	public Set<FileModel> getAllInWithContentType(FileType contentType) {
		return getFileModels(fileRepository.selectAllInWithContentType(contentType));
	}

	@Override
	public FileModel getInById(String id) throws FileNotFoundException {
		try{
			return getFileModel(fileRepository.selectInById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new FileNotFoundException("GE_1025", ChatOnWebConstants.FILE_ID, id);
		}
	}

	@Override
	public Set<FileModel> getInByReceiverId(String receiverId) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getFileModels(fileRepository.selectInByReceiver(receiver));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<FileModel> getInByReceiverIdWithText(String receiverId, String text) throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getFileModels(fileRepository.selectInByReceiverWithText(receiver,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<FileModel> getInByReceiverIdWithTime(String receiverId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getFileModels(fileRepository.selectInByReceiverWithTime(receiver,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<FileModel> getInByReceiverIdWithContentType(String receiverId, FileType contentType)
			throws UserNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			return getFileModels(fileRepository.selectInByReceiverWithContentType(receiver,contentType));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInById(String id) throws FileNotFoundException {
		try{
			final File file=fileRepository.selectInById(id);
			final java.io.File file1=new java.io.File(file.getReceiverPath());
			file1.delete();
			fileRepository.deleteInById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				fileRepository.updateInById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new FileNotFoundException("GE_1025", ChatOnWebConstants.FILE_ID, id);
			}
		}
	}

	@Override
	public void deleteInByReceiverId(String receiverId) throws UserNotFoundException, FileNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<File> files=fileRepository.selectInByReceiver(receiver);
				for(File file:files){
					final java.io.File file1=new java.io.File(file.getReceiverPath());
					file1.delete();
				}
				fileRepository.deleteInByReceiver(receiver);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					fileRepository.updateInByReceiver(receiver);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new FileNotFoundException("GE_1025", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByReceiverIdWithContentType(String receiverId, FileType contentType)
			throws UserNotFoundException, FileNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final List<File> files=fileRepository.selectInByReceiverWithContentType(receiver, contentType);
				for(File file:files){
					final java.io.File file1=new java.io.File(file.getReceiverPath());
					file1.delete();
				}
				fileRepository.deleteInByReceiverWithContentType(receiver,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					fileRepository.updateInByReceiverWithContentType(receiver, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new FileNotFoundException("GE_1025", ChatOnWebConstants.RECEIVER_ID, receiverId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public Set<FileModel> getAllOut() {
		return getFileModels(fileRepository.selectAllOut());
	}

	@Override
	public Set<FileModel> getAllOutWithContentType(FileType contentType) {
		return getFileModels(fileRepository.selectAllOutWithContentType(contentType));
	}

	@Override
	public FileModel getOutById(String id) throws FileNotFoundException {
		try{
			return getFileModel(fileRepository.selectOutById(id));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new FileNotFoundException("GE_1025", ChatOnWebConstants.FILE_ID, id);
		}
	}

	@Override
	public Set<FileModel> getOutBySenderId(String senderId) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getFileModels(fileRepository.selectOutBySender(sender));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<FileModel> getOutBySenderIdWithText(String senderId, String text) throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getFileModels(fileRepository.selectOutBySenderWithText(sender,text));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<FileModel> getOutBySenderIdWithTime(String senderId, LocalDateTime createTimestamp)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getFileModels(fileRepository.selectOutBySenderWithTime(sender,createTimestamp));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<FileModel> getOutBySenderIdWithContentType(String senderId, FileType contentType)
			throws UserNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			return getFileModels(fileRepository.selectOutBySenderWithContentType(sender,contentType));
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutById(String id) throws FileNotFoundException {
		try{
			final File file=fileRepository.selectOutById(id);
			final java.io.File file1=new java.io.File(file.getSenderPath());
			file1.delete();
			fileRepository.deleteOutById(id);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				fileRepository.updateOutById(id);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new FileNotFoundException("GE_1025", ChatOnWebConstants.FILE_ID, id);
			}
		}
	}

	@Override
	public void deleteOutBySenderId(String senderId) throws UserNotFoundException, FileNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<File> files=fileRepository.selectOutBySender(sender);
				for(File file:files){
					final java.io.File file1=new java.io.File(file.getSenderPath());
					file1.delete();
				}
				fileRepository.deleteOutBySender(sender);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					fileRepository.updateOutBySender(sender);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new FileNotFoundException("GE_1025", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutBySenderIdWithContentType(String senderId, FileType contentType)
			throws UserNotFoundException, FileNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final List<File> files=fileRepository.selectOutBySenderWithContentType(sender, contentType);
				for(File file:files){
					final java.io.File file1=new java.io.File(file.getSenderPath());
					file1.delete();
				}
				fileRepository.deleteOutBySenderWithContentType(sender,contentType);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					fileRepository.updateOutBySenderWithContentType(sender, contentType);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new FileNotFoundException("GE_1025", ChatOnWebConstants.SENDER_ID, senderId);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<FileModel> getInByName(String name) {
		return getFileModels(fileRepository.selectInByName(name));
	}

	@Override
	public FileModel getInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, FileNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				return getFileModel(fileRepository.selectInByReceiverWithName(receiver, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new FileNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	@Override
	public void deleteInByName(String name) throws FileNotFoundException {
		try{
			final List<File> files=fileRepository.selectInByName(name);
			for(File file:files){
				final java.io.File file1=new java.io.File(file.getReceiverPath());
				file1.delete();
			}
			fileRepository.deleteInByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				fileRepository.updateInByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new FileNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteInByReceiverIdWithName(String receiverId, String name)
			throws UserNotFoundException, FileNotFoundException {
		try{
			final User receiver=userRepository.selectById(receiverId);
			try{
				final File file=fileRepository.selectInByReceiverWithName(receiver, name);
				final java.io.File file1=new java.io.File(file.getReceiverPath());
				file1.delete();
				fileRepository.deleteInByReceiverWithName(receiver, name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					fileRepository.updateInByReceiverWithName(receiver, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new FileNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.RECEIVER_ID, receiverId);
		}
	}

	

	@Override
	public FileModel getOutBySenderIdWithName(String senderId, String name) throws UserNotFoundException,FileNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				return getFileModel(fileRepository.selectOutBySenderWithName(sender, name));
			}catch(EntityNotFoundException entityNotFoundException){
				throw new FileNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public void deleteOutByName(String name) throws FileNotFoundException {
		try{
			final List<File> files=fileRepository.selectOutByName(name);
			for(File file:files){
				final java.io.File file1=new java.io.File(file.getSenderPath());
				file1.delete();
			}
			fileRepository.deleteOutByName(name);
		}catch(EntityNotFoundException entityNotFoundException){
			try{
				fileRepository.updateOutByName(name);
			}catch(EntityNotFoundException entityNotFoundException2){
				throw new FileNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
			}
		}
	}

	@Override
	public void deleteOutBySenderIdWithName(String senderId, String name)
			throws UserNotFoundException, FileNotFoundException {
		try{
			final User sender=userRepository.selectById(senderId);
			try{
				final File file=fileRepository.selectOutBySenderWithName(sender,name);
				final java.io.File file1=new java.io.File(file.getSenderPath());
				file1.delete();
				fileRepository.deleteOutBySenderWithName(sender,name);
			}catch(EntityNotFoundException entityNotFoundException){
				try{
					fileRepository.updateOutBySenderWithName(sender, name);
				}catch(EntityNotFoundException entityNotFoundException2){
					throw new FileNotFoundException("GE_1021", ChatOnWebConstants.AUDIO_NAME, name);
				}
			}
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.SENDER_ID, senderId);
		}
	}

	@Override
	public Set<FileModel> getOutByName(String name) {
		return getFileModels(fileRepository.selectOutByName(name));
	}
}