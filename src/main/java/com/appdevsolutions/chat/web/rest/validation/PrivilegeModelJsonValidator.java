package com.appdevsolutions.chat.web.rest.validation;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.PrivilegeModel;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class PrivilegeModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(PrivilegeModelJsonValidator.class);
	
	public static PrivilegeModel validate(String json)throws ChatOnJsonParseException{
		String privilegeName;
		String roleId;
		final JSONParser parser = new JSONParser();

		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.PRIVILEGE_NAME)){
						privilegeName= jsonObject.get(ChatOnWebConstants.PRIVILEGE_NAME).toString();
						if(null != privilegeName && privilegeName.length() > 0){
							privilegeName=privilegeName.trim();
						}else{
							throw new ChatOnJsonParseException("VE_1017", ChatOnWebConstants.PRIVILEGE_NAME);
						}
					}else{
						throw new ChatOnJsonParseException("VE_1016", ChatOnWebConstants.PRIVILEGE_NAME);
					}
	
					if(jsonObject.containsKey(ChatOnWebConstants.ROLE_ID)){
						roleId = jsonObject.get(ChatOnWebConstants.ROLE_ID).toString();
						if(null != roleId&& roleId.length() > 0){
							roleId=roleId.trim();
						}else{
							throw new ChatOnJsonParseException("VE_1007", ChatOnWebConstants.ROLE_ID);
						}
					}else{
						throw new ChatOnJsonParseException("VE_1006", ChatOnWebConstants.ROLE_ID);
					}
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_PRIVILEGE_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_PRIVILEGE_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_PRIVILEGE_CREATE);
		}
		final PrivilegeModel privilegeModel=new PrivilegeModel("", privilegeName, roleId, LocalDateTime.now());
		return privilegeModel;
	}
}