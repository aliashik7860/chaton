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

public class UpdateEmailTemplateNameJsonValidator {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateEmailTemplateNameJsonValidator.class);
	
	private final static JSONParser parser = new JSONParser();
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String oldName="";
		String newName="";
		try {
			if(json!=null&&!json.equals("")){
				final JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME)){
						oldName = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME).toString();
						if(!(null != oldName && oldName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1027", ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME);
						}else{
							oldName=oldName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1026", ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME)){
						newName = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME).toString();
						if(!(null != newName && newName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1029", ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME);
						}else{
							newName=newName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1028", ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME);
					}
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_NAME_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_NAME_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_EMAIL_TEMPLATE_NAME_UPDATE);
		}
		final Map<String, String> map=new HashMap<String,String>(2);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME, oldName);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME, newName);
		return map;
	}
}