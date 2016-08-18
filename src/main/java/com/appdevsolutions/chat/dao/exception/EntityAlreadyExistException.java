package com.appdevsolutions.chat.dao.exception;

public class EntityAlreadyExistException extends ChatOnDaoException {
	
	private static final long serialVersionUID = 1L;

	public EntityAlreadyExistException(String message) {
		super(message);
	}
}
