package com.appdevsolutions.chat.web.service;

import java.io.IOException;

import com.appdevsolutions.chat.common.model.ProfilePhotoModel;
import com.appdevsolutions.chat.web.exception.PhotoNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
public interface ProfilePhotoService {
	public ProfilePhotoModel findByUserId(final String userId) throws PhotoNotFoundException,UserNotFoundException,IOException;
	public ProfilePhotoModel findByUsername(final String userId) throws PhotoNotFoundException,UserNotFoundException,IOException;
	public void save(final ProfilePhotoModel photo) throws UserNotFoundException,IOException;
	public void update(final ProfilePhotoModel profilePhotoModel) throws UserNotFoundException,IOException;
}
