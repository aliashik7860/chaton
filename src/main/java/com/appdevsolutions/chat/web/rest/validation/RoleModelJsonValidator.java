package com.appdevsolutions.chat.web.rest.validation;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.RoleModel;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class RoleModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(RoleModelJsonValidator.class);
	
	public static RoleModel validate(String json)throws ChatOnJsonParseException{
		String roleName="";
		String userId="";
		final JSONParser parser = new JSONParser();
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.ROLE_NAME)){
						roleName = jsonObject.get(ChatOnWebConstants.ROLE_NAME).toString();
						if(!(null != roleName && roleName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1009", ChatOnWebConstants.ROLE_NAME);
						}else{
							roleName=roleName.trim();
						}
					}else{
						//qr.setErrorMsg("NAME IS Required !!!");
						throw new ChatOnJsonParseException("VE_1008", ChatOnWebConstants.ROLE_NAME);
					}
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
				}else{
					throw new ChatOnJsonParseException("1000", ChatOnWebConstants.JSON_ROLE_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("1000", ChatOnWebConstants.JSON_ROLE_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("1001",ChatOnWebConstants.JSON_ROLE_CREATE);
		}
		final RoleModel roleModel=new RoleModel("", roleName, userId,null, LocalDateTime.now());
		return roleModel;
	}
}