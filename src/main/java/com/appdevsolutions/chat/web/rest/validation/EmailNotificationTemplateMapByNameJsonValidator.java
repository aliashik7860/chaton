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

public class EmailNotificationTemplateMapByNameJsonValidator 
{
	private static final Logger LOGGER=LoggerFactory.getLogger(EmailNotificationTemplateMapByNameJsonValidator.class);
	//private static final String ERROR_CODE_REGEX="^[A-Z]{2}_[0-9]{4}$";
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String emailTemplateName="";
		String userId="";
		final JSONParser parser = new JSONParser();

		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_NAME)){
						emailTemplateName = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_NAME).toString();
						if(!(null != emailTemplateName && emailTemplateName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1025", ChatOnWebConstants.EMAIL_TEMPLATE_NAME);
						}else{
							emailTemplateName=emailTemplateName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1024", ChatOnWebConstants.EMAIL_TEMPLATE_NAME);
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
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME_CREATE);
		}
		final Map<String, String> map=new HashMap<String,String>(2);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_NAME, emailTemplateName);
		map.put(ChatOnWebConstants.USER_ID, userId);
		return map;
	}
}