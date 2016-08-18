package com.appdevsolutions.chat.dao.repository;

import com.appdevsolutions.chat.dao.entity.ProfilePhoto;
import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;

public interface ProfilePhotoRepository {
	public void save(ProfilePhoto profilePhoto);
	public void update(ProfilePhoto profilePhoto);
	public void deleteById(String profilePhotoId)throws EntityNotFoundException;
	public void deleteByUser(User user)throws EntityNotFoundException;
	public ProfilePhoto selectById(String profilePhotoId) throws EntityNotFoundException;
	public ProfilePhoto selectByUser(User user) throws EntityNotFoundException;
}
