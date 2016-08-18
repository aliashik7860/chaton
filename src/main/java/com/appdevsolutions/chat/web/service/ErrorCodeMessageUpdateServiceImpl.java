package com.appdevsolutions.chat.web.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import com.appdevsolutions.chat.common.model.ErrorCodeModel;
import com.appdevsolutions.chat.web.exception.ErrorCodeAlreadyExistException;
import com.appdevsolutions.chat.web.exception.ErrorCodeNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;

@Service
public class ErrorCodeMessageUpdateServiceImpl implements ErrorCodeMessageUpdateService{
	
	private static Properties properties;
	static{
		try{
			properties=PropertiesLoaderUtils.loadProperties(new ClassPathResource("error-codes.properties"));
		}catch(IOException ioException){
			ioException.printStackTrace(System.err);
		}
	}
	@Override
	public void save(String errorCode, String errorMessage) throws ErrorCodeAlreadyExistException{
		if(properties.containsKey(errorCode)){
			throw new ErrorCodeAlreadyExistException("GE_1013",ChatOnWebConstants.ERROR_CODE , errorCode);
		}else{
			properties.put(errorCode, errorMessage);
		}
	}

	@Override
	public void delete(String errorCode) throws ErrorCodeNotFoundException{
		if(properties.containsKey(errorCode)&&!errorCode.equals("GE_1012")&&!errorCode.equals("GE_1013")){
			properties.remove(errorCode);
		}else{
			throw new ErrorCodeNotFoundException("GE_1012",ChatOnWebConstants.ERROR_CODE,errorCode);
		}
	}

	@Override
	public void update(String errorCode, String message) throws ErrorCodeAlreadyExistException{
		if(properties.containsKey(errorCode)&&!errorCode.equals("GE_1012")&&!errorCode.equals("GE_1013")){
			properties.setProperty(errorCode, message);
		}else{
			throw new ErrorCodeAlreadyExistException("GE_1013", ChatOnWebConstants.ERROR_CODE, errorCode);
		}
	}

	@Override
	public Set<ErrorCodeModel> getAll(Comparator<ErrorCodeModel> comparator) {
		final Set<Object> set=properties.keySet();
		final Set<ErrorCodeModel> errorCodeModels=new TreeSet<ErrorCodeModel>(comparator);
		for(Object object:set){
			final ErrorCodeModel errorCodeModel=new ErrorCodeModel(object.toString(), properties.getProperty(object.toString()));
			errorCodeModels.add(errorCodeModel);
		}
		return errorCodeModels;
	}
	
	@Override
	public String getMessageByKey(String errorCode) throws ErrorCodeNotFoundException {
		if(properties.containsKey(errorCode)){
			return properties.getProperty(errorCode);
		}else{
			throw new ErrorCodeNotFoundException("EG_1012", ChatOnWebConstants.ERROR_CODE, errorCode);
		}
	}

}
