package com.appdevsolutions.chat.web.rest.validation;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class UpdatePasswordModelJsonValidator {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdatePasswordModelJsonValidator.class);
	
	private final static JSONParser parser = new JSONParser();
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String userId="";
		String oldPassword="";
		String newPassword="";
		String newRePassword="";
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.USER_ID)){
						userId = jsonObject.get(ChatOnWebConstants.USER_ID).toString();
						if(!(null != userId && userId.length() > 0)){
							throw new ChatOnJsonParseException("VE_1003", ChatOnWebConstants.USER_ID);
						}else{
							userId=userId.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1002", ChatOnWebConstants.USER_ID);
					}
	
					if(jsonObject.containsKey(ChatOnWebConstants.OLD_PASSWORD)){
						oldPassword = jsonObject.get(ChatOnWebConstants.OLD_PASSWORD).toString();
						if(!(null != oldPassword && oldPassword.length() > 0)){
							throw new ChatOnJsonParseException("VE_1041", ChatOnWebConstants.OLD_PASSWORD);
						}else{
							oldPassword=oldPassword.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1040", ChatOnWebConstants.OLD_PASSWORD);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.NEW_PASSWORD)){
						newPassword = jsonObject.get(ChatOnWebConstants.NEW_PASSWORD).toString();
						if(!(null != newPassword && newPassword.length() > 0)){
							throw new ChatOnJsonParseException("VE_1043", ChatOnWebConstants.NEW_PASSWORD);
						}else{
							newPassword=newPassword.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1042", ChatOnWebConstants.NEW_PASSWORD);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.NEW_RE_PASSWORD)){
						newRePassword = jsonObject.get(ChatOnWebConstants.NEW_RE_PASSWORD).toString();
						if(!(null != newRePassword && newRePassword.length() > 0)){
							throw new ChatOnJsonParseException("VE_1045", ChatOnWebConstants.NEW_RE_PASSWORD);
						}else{
							newRePassword=newRePassword.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1044", ChatOnWebConstants.NEW_RE_PASSWORD);
					}
					
					if(jsonObject.get(ChatOnWebConstants.OLD_PASSWORD).toString().equals(jsonObject.get(ChatOnWebConstants.NEW_PASSWORD).toString())){
						throw new ChatOnJsonParseException("VE_1046", ChatOnWebConstants.NEW_PASSWORD);
					}
					
					if(jsonObject.get(ChatOnWebConstants.NEW_PASSWORD).toString().equals(jsonObject.get(ChatOnWebConstants.NEW_RE_PASSWORD).toString())){
						if(!(jsonObject.get(ChatOnWebConstants.NEW_PASSWORD).toString().length()>=4&&jsonObject.get(ChatOnWebConstants.NEW_PASSWORD).toString().length()<=8)){
							throw new ChatOnJsonParseException("VE_1047", ChatOnWebConstants.NEW_PASSWORD);
						}
					}else{
						throw new ChatOnJsonParseException("VE_1048", ChatOnWebConstants.NEW_PASSWORD);
					}
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_USER_PASSWORD_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_USER_PASSWORD_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_USER_PASSWORD_UPDATE);
		}
		final Map<String, String> map=new HashMap<String,String>(3);
		map.put(ChatOnWebConstants.USER_ID, userId);
		map.put(ChatOnWebConstants.OLD_PASSWORD, oldPassword);
		map.put(ChatOnWebConstants.NEW_PASSWORD, newPassword);
		return map;
	}
}