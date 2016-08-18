package com.appdevsolutions.chat.dao.exception;

public class EntityNotFoundException extends ChatOnDaoException{
	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
		super(message);
	}

}
