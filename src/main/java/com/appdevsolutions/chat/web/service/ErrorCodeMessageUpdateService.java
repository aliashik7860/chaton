package com.appdevsolutions.chat.web.service;

import java.util.Comparator;
import java.util.Set;

import com.appdevsolutions.chat.common.model.ErrorCodeModel;
import com.appdevsolutions.chat.web.exception.ErrorCodeAlreadyExistException;
import com.appdevsolutions.chat.web.exception.ErrorCodeNotFoundException;

public interface ErrorCodeMessageUpdateService {
	public void save(String errorCode,String errorMessage)throws ErrorCodeAlreadyExistException;
	public void delete(String errorCode)throws ErrorCodeNotFoundException;
	public void update(String errorCode,String message)throws ErrorCodeAlreadyExistException;
	public String getMessageByKey(String errorCode)throws ErrorCodeNotFoundException;
	public Set<ErrorCodeModel> getAll(Comparator<ErrorCodeModel> comparator);
}
