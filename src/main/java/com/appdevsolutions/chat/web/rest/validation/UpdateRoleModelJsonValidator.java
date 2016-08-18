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

public class UpdateRoleModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateRoleModelJsonValidator.class);
	
	public static Map<String,String> validate(String json)throws ChatOnJsonParseException{
		String roleId="";
		String oldRoleName="";
		String newRoleName="";
		final JSONParser parser = new JSONParser();
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.ROLE_ID)){
						roleId = jsonObject.get(ChatOnWebConstants.ROLE_ID).toString();
						if(!(null != roleId && roleId.length() > 0)){
							throw new ChatOnJsonParseException("VE_1001", ChatOnWebConstants.ROLE_ID);
						}else{
							roleId=roleId.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1008", ChatOnWebConstants.ROLE_ID);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.OLD_ROLE_NAME)){
						oldRoleName = jsonObject.get(ChatOnWebConstants.OLD_ROLE_NAME).toString();
						if(!(null != oldRoleName && oldRoleName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1005", ChatOnWebConstants.OLD_ROLE_NAME);
						}else{
							oldRoleName=oldRoleName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1012", ChatOnWebConstants.OLD_ROLE_NAME);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.NEW_ROLE_NAME)){
						newRoleName = jsonObject.get(ChatOnWebConstants.NEW_ROLE_NAME).toString();
						if(!(null != newRoleName && newRoleName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1006", ChatOnWebConstants.NEW_ROLE_NAME);
						}else{
							newRoleName= newRoleName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1013", ChatOnWebConstants.NEW_ROLE_NAME);
					}
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_ROLE_NAME_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_ROLE_NAME_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_ROLE_NAME_UPDATE);
		}
		final Map<String,String> map=new HashMap<String, String>(3);
		map.put(ChatOnWebConstants.ROLE_ID, roleId);
		map.put(ChatOnWebConstants.OLD_ROLE_NAME, oldRoleName);
		map.put(ChatOnWebConstants.NEW_ROLE_NAME, newRoleName);
		return map;
	}
}