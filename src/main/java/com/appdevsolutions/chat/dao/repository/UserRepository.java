package com.appdevsolutions.chat.dao.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.appdevsolutions.chat.dao.entity.User;
import com.appdevsolutions.chat.dao.exception.EntityNotFoundException;


@Repository
public interface UserRepository {
	public void deleteById(String userId) throws EntityNotFoundException;
	public void deleteByUsername(String username) throws EntityNotFoundException;
	public User selectById(String userId) throws EntityNotFoundException;
	public List<User> selectByIds(List<String> userIds);
	public User selectByUsername(String username) throws EntityNotFoundException;
	public List<User> selectAll() throws EntityNotFoundException;
	public boolean isUserExistById(String userId);
	public boolean isUserExistByUsername(String username);
	public void persist(User user);
	public User getUserByUsernameAndPassword(String username,String password) throws EntityNotFoundException;
	public void update(User user) throws EntityNotFoundException;
	public void updateLastLoginByUserId(String userId,LocalDateTime now)throws EntityNotFoundException;
	public void updateLastLoginByUsername(String username,LocalDateTime now)throws EntityNotFoundException;
}