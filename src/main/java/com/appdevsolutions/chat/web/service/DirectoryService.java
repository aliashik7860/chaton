package com.appdevsolutions.chat.web.service;

import java.io.File;
import java.io.InputStream;

import com.appdevsolutions.chat.web.exception.FileNotFoundException;

public interface DirectoryService {
	public void createDirectoryTree();
	public File getProfilePhotoByUserId(String userId)throws FileNotFoundException;
	public File getPhotoById(String photoId)throws FileNotFoundException;
	public File getAudioById(String audioId)throws FileNotFoundException;
	public File getFileById(String fileId)throws FileNotFoundException;
	public File getVideoById(String videoId)throws FileNotFoundException;
	public void saveProfilePhoto(InputStream profilePhotoStream,String fileName)throws FileNotFoundException;
	public void savePhoto(InputStream photoStream,String fileName)throws FileNotFoundException;
	public void saveAudioDirectory(InputStream audioStream,String fileName)throws FileNotFoundException;
	public void saveFileDirectory(InputStream fileStream,String fileName)throws FileNotFoundException;
	public void saveVideoDirectory(InputStream videoStream,String fileName)throws FileNotFoundException;
}
