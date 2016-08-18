package com.appdevsolutions.chat.web.service;

import java.time.LocalDateTime;
import java.util.List;

import com.appdevsolutions.chat.web.exception.UserAlreadyExistException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.exception.UserUpdatePasswordException;
import com.appdevsolutions.chat.web.model.UserModel;

public interface UserService {
	public void basicAuthentication(String authorization)throws UserNotFoundException;
	public UserModel getByUsernameAndPassword(String username,String password)throws UserNotFoundException;
	public UserModel liveMe()throws UserNotFoundException;
	public List<UserModel> liveUsers();
	public List<UserModel> eachUserUsers(final String userId) throws UserNotFoundException;
	public void deleteUserById(final String userId) throws UserNotFoundException;
	public void deleteUserByUsername(final String username) throws UserNotFoundException;
	public UserModel findUserById(final String userId) throws UserNotFoundException;
	public UserModel findUserByUsername(final String username) throws UserNotFoundException;
	public boolean isUsernameRegistered(final String username);
	public List<UserModel> allUsers();
	public void saveUser(final UserModel userModel)throws UserAlreadyExistException;
	public void saveAdminUser(final UserModel user)throws UserAlreadyExistException;
	public void updateUserPassword(String id, String oldPassword,String newPassword) throws UserNotFoundException,UserUpdatePasswordException;
	public void setLastLoginByUserId(final String userId,LocalDateTime now)throws UserNotFoundException;
	public void setLastLoginByUsername(final String username,LocalDateTime now)throws UserNotFoundException;
}