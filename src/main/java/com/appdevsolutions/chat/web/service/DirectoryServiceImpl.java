package com.appdevsolutions.chat.web.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.dao.repository.UserRepository;
import com.appdevsolutions.chat.web.exception.FileNotFoundException;

@Service
public class DirectoryServiceImpl implements DirectoryService {

	@Autowired
	UserRepository userRepository;
	
	//private static final Logger LOGGER=LoggerFactory.getLogger(DirectoryServiceImpl.class);
	
	public static final String SYSTEM_USER_HOME_DIR=System.getProperty("user.home");
	public static final String APP_ROOT_DIR="ChatOn";
	public static final String DEFAULT_PROFILE_PHOTO_PATH=SYSTEM_USER_HOME_DIR+File.separator+"Test"+File.separator+"ChatOn"+File.separator+"image.png";
	public static final String DIR_CONST_PROFILE_PHOTO="Profile_Photo";
	public static final String DIR_CONST_PHOTO="Photo";
	public static final String DIR_CONST_AUDIO="Audio";
	public static final String DIR_CONST_VIDEO="Video";
	public static final String DIR_CONST_FILE="File";
	
	@Override
	public void createDirectoryTree() {
		final File dirApp=new File(SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR);
        dirApp.mkdir();
        final File dirProfilePhoto=new File(dirApp.getAbsolutePath()+File.separator+DIR_CONST_PROFILE_PHOTO);
        final File dirPhotos=new File(dirApp.getAbsolutePath()+File.separator+DIR_CONST_PHOTO);
        final File dirAudios=new File(dirApp.getAbsolutePath()+File.separator+DIR_CONST_AUDIO);
        final File dirVideos=new File(dirApp.getAbsolutePath()+File.separator+DIR_CONST_VIDEO);
        final File dirFiles=new File(dirApp.getAbsolutePath()+File.separator+DIR_CONST_FILE);
        dirProfilePhoto.mkdir();
        dirPhotos.mkdir();
        dirAudios.mkdir();
        dirVideos.mkdir();
        dirFiles.mkdir();
        /*final File fileSourceProfilePhoto=new File(USER_DEFAULT_PROFILE_PHOTO_PATH);
        final File fileDestinationProfilePhoto=new File(dirProfilePhoto.getAbsolutePath(),"ProfilePhoto.png");
        try {
        	Files.copy(fileSourceProfilePhoto.toPath(), fileDestinationProfilePhoto.toPath());
        }catch(IOException ioe) {
        	LOGGER.error("user directory tree creation exception",ioe);
        }*/
		
	}

	@Override
	public File getProfilePhotoByUserId(String userId) throws FileNotFoundException {
		final File profilePhoto=new File(SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR+File.separator+DIR_CONST_PROFILE_PHOTO+File.separator+userId);
		return profilePhoto;
	}

	@Override
	public File getPhotoById(String photoId) throws FileNotFoundException {
		final File photo=new File(SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR+File.separator+DIR_CONST_PHOTO+File.separator+photoId);
		return photo;
	}

	@Override
	public File getAudioById(String audioId) throws FileNotFoundException {
		final File audio=new File(SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR+File.separator+DIR_CONST_AUDIO+File.separator+audioId);
		return audio;
	}

	@Override
	public File getFileById(String fileId) throws FileNotFoundException {
		final File file=new File(SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR+File.separator+DIR_CONST_FILE+File.separator+fileId);
		return file;
	}

	@Override
	public File getVideoById(String videoId) throws FileNotFoundException {
		final File video=new File(SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR+File.separator+DIR_CONST_VIDEO+File.separator+videoId);
		return video;
	}

	@Override
	public void saveProfilePhoto(InputStream profilePhotoStream,String fileName) throws FileNotFoundException {
		final String subPath=DIR_CONST_PROFILE_PHOTO+File.separator+fileName;
		write(profilePhotoStream, subPath);
	}

	@Override
	public void savePhoto(InputStream photoStream,String fileName) throws FileNotFoundException {
		final String subPath=DIR_CONST_PHOTO+File.separator+fileName;
		write(photoStream, subPath);
	}

	@Override
	public void saveAudioDirectory(InputStream audioStream,String fileName) throws FileNotFoundException {
		final String subPath=DIR_CONST_AUDIO+File.separator+fileName;
		write(audioStream, subPath);
	}

	@Override
	public void saveFileDirectory(InputStream fileStream,String fileName) throws FileNotFoundException {
		final String subPath=DIR_CONST_FILE+File.separator+fileName;
		write(fileStream, subPath);
	}

	@Override
	public void saveVideoDirectory(InputStream videoStream,String fileName) throws FileNotFoundException {
		final String subPath=DIR_CONST_VIDEO+File.separator+fileName;
		write(videoStream, subPath);
	}
	
	private void write(InputStream inputStream,String subPath){
		final String path=SYSTEM_USER_HOME_DIR+File.separator+APP_ROOT_DIR+File.separator+subPath;
		final byte[] buffer=new byte[4096];
		try{
			final FileOutputStream fileOutputStream=new FileOutputStream(path);
			while(inputStream.read(buffer)!=-1){
				fileOutputStream.write(buffer);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	
}
