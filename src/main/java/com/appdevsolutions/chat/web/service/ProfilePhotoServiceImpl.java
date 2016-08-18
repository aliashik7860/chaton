package com.appdevsolutions.chat.web.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.common.model.ProfilePhotoModel;
import com.appdevsolutions.chat.dao.entity.ProfilePhoto;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;
import com.appdevsolutions.chat.dao.repository.ProfilePhotoRepository;
import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.PhotoNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Service
public class ProfilePhotoServiceImpl implements ProfilePhotoService {

	private static final Logger LOGGER=LoggerFactory.getLogger(ProfilePhotoServiceImpl.class);
	
	@Autowired
	DirectoryService userDirectoryService;
	
	@Autowired
	ProfilePhotoRepository profilePhotoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private void delete(File profilePhotoDirectory) {
		getProfilePhotoFile(profilePhotoDirectory).delete();
	}

	private String getProfilePhotoName(File profilePhotoDirectory){
		final String[] subFilesNames=profilePhotoDirectory.list();
		return subFilesNames[0];
	}
	
	private File getProfilePhotoFile(File profilePhotoName){
		return new File(getProfilePhotoName(profilePhotoName));
	}
	
	private ProfilePhotoModel findByProfileDirectory(File profilePhotoDirectory,User user) throws PhotoNotFoundException,IOException{
		File profilePhotoFile=null;
		try{
			final ProfilePhoto profilePhoto=profilePhotoRepository.selectByUser(user);
			profilePhotoFile=getProfilePhotoFile(profilePhotoDirectory);
			final File file=new File(profilePhotoDirectory,profilePhoto.getName());
			final InputStream inputStream=new FileInputStream(file);
			final byte[] bytes=IOUtils.toByteArray(inputStream);
			final ProfilePhotoModel profilePhotoModel=new ProfilePhotoModel(profilePhoto.getId(),user.getId(), profilePhoto.getName(), profilePhoto.getContentType(), profilePhoto.getSize(), bytes, profilePhoto.getCreateTimestamp());
			return profilePhotoModel;
		}catch(EntityNotFoundException profilePhotoEntityNotFoundException){
			LOGGER.error("Profile photo not with user id : "+user.getId());
			throw new PhotoNotFoundException("GE_1023",ChatOnWebConstants.USER_ID,user.getId());
		}catch(FileNotFoundException fileNotFoundException){
			LOGGER.error("File not found with file name : "+profilePhotoFile.getName());
			throw new PhotoNotFoundException("GE_1023",ChatOnWebConstants.PHOTO_NAME,profilePhotoFile.getName());
		}catch (IOException ioException) {
			LOGGER.error("IO Exception has occured in file name : "+profilePhotoFile.getName());
			throw ioException;
		}
	}
	
	private void saveOrUpdateByProfilePhotoDirectory(File profilePhotoDirectory, ProfilePhotoModel profilePhotoModel,ProfilePhoto profilePhoto) throws IOException{
		try{
			final File profilePhotoFile=new File(profilePhotoDirectory,profilePhotoModel.getName());
			profilePhotoFile.createNewFile();
			final BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(new FileOutputStream(profilePhotoFile));
			bufferedOutputStream.write(profilePhotoModel.getBytes());
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			profilePhoto.setName(profilePhotoModel.getName());
			//profilePhoto.setUserId(profilePhotoModel.getUserId());
			profilePhoto.setContentType(profilePhotoModel.getContentType());
			profilePhoto.setSize(profilePhotoModel.getSize());
			profilePhoto.setCreateTimestamp(profilePhotoModel.getCreateTimestamp());
		}catch (IOException ioException) {
			LOGGER.error("IO Exception Occured while saving profile photo : "+profilePhotoModel.getName(),ioException);
			throw ioException;
		}
	}
	
	@Override
	public ProfilePhotoModel findByUserId(String userId) throws PhotoNotFoundException, UserNotFoundException,IOException {
		try{
			final User user=userRepository.selectById(userId);
			final File profilePhotoDirectory=userDirectoryService.getProfilePhotoDirectoryByUserId(userId);
			return findByProfileDirectory(profilePhotoDirectory,user);
		}catch(EntityNotFoundException entityNotFoundException){
			throw new UserNotFoundException("GE_1000", ChatOnWebConstants.USER_ID, userId);
		}catch(UserNotFoundException userNotFoundException){
			throw userNotFoundException;
		}
	}
	
	@Override
	public ProfilePhotoModel findByUsername(String username) throws PhotoNotFoundException, UserNotFoundException,IOException {
		/*try{
			final File profilePhotoDirectory=userDirectoryService.getProfilePhotoDirectoryByUsername(username);
			return findByProfileDirectory(profilePhotoDirectory,username);
		}catch(UserNotFoundException userNotFoundException){
			throw userNotFoundException;
		}*/
		return null;
	}

	@Override
	public void save(ProfilePhotoModel profilePhotoModel) throws UserNotFoundException,IOException {
		try{
			final File profilePhotoDirectory=userDirectoryService.getProfilePhotoDirectoryByUserId(profilePhotoModel.getUserId());
			final ProfilePhoto profilePhoto=new ProfilePhoto();
			saveOrUpdateByProfilePhotoDirectory(profilePhotoDirectory, profilePhotoModel,profilePhoto);
			profilePhotoRepository.save(profilePhoto);
		}catch(UserNotFoundException userNotFoundException){
			LOGGER.error("User not found with userId",userNotFoundException);
			throw userNotFoundException;
		}
	}

	@Override
	public void update(ProfilePhotoModel profilePhotoModel) throws UserNotFoundException,IOException {
		try{
			final File profilePhotoDirectory=userDirectoryService.getProfilePhotoDirectoryByUserId(profilePhotoModel.getUserId());
			delete(profilePhotoDirectory);
			final ProfilePhoto profilePhoto=new ProfilePhoto();
			saveOrUpdateByProfilePhotoDirectory(profilePhotoDirectory, profilePhotoModel,profilePhoto);
			profilePhotoRepository.update(profilePhoto);
		}catch(UserNotFoundException userNotFoundException){
			LOGGER.error("User not found with userId",userNotFoundException);
			throw userNotFoundException;
		}
	}
	
}