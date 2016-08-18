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

public class UpdatePrivilegeModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdatePrivilegeModelJsonValidator.class);
	
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String privilegeId="";
		String oldPrivilegeName="";
		String newPrivilegeName="";
		final JSONParser parser = new JSONParser();
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.PRIVILEGE_ID)){
						privilegeId = jsonObject.get(ChatOnWebConstants.PRIVILEGE_ID).toString();
						if(!(null != privilegeId && privilegeId.length() > 0)){
							throw new ChatOnJsonParseException("VE_1002", ChatOnWebConstants.PRIVILEGE_ID);
						}else{
							privilegeId=privilegeId.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1009", ChatOnWebConstants.PRIVILEGE_ID);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.OLD_PRIVILEGE_NAME)){
						oldPrivilegeName= jsonObject.get(ChatOnWebConstants.OLD_PRIVILEGE_NAME).toString();
						if(!(null != oldPrivilegeName && oldPrivilegeName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1016", ChatOnWebConstants.OLD_PRIVILEGE_NAME);
						}else{
							oldPrivilegeName=oldPrivilegeName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1018", ChatOnWebConstants.OLD_PRIVILEGE_NAME);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.NEW_PRIVILEGE_NAME)){
						newPrivilegeName = jsonObject.get(ChatOnWebConstants.NEW_PRIVILEGE_NAME).toString();
						if(!(null != newPrivilegeName && newPrivilegeName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1017", ChatOnWebConstants.NEW_PRIVILEGE_NAME);
						}else{
							newPrivilegeName=newPrivilegeName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1019", ChatOnWebConstants.NEW_PRIVILEGE_NAME);
					}
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_PRIVILEGE_NAME_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_PRIVILEGE_NAME_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_PRIVILEGE_NAME_UPDATE);
		}
		final Map<String, String> map=new HashMap<String,String>(3);
		map.put(ChatOnWebConstants.PRIVILEGE_ID, privilegeId);
		map.put(ChatOnWebConstants.OLD_PRIVILEGE_NAME, oldPrivilegeName);
		map.put(ChatOnWebConstants.NEW_PRIVILEGE_NAME, newPrivilegeName);
		return map;
	}
}