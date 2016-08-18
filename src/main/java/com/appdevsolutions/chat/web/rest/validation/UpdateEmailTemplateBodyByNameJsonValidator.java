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

public class UpdateEmailTemplateBodyByNameJsonValidator {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateEmailTemplateBodyByNameJsonValidator.class);
	
	private final static JSONParser parser = new JSONParser();
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String name="";
		String body="";
		try {
			if(json!=null&&!json.equals("")){
				final JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_NAME)){
						name = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_NAME).toString();
						if(!(null != name && name.length() > 0)){
							throw new ChatOnJsonParseException("VE_1025", ChatOnWebConstants.EMAIL_TEMPLATE_NAME);
						}else{
							name=name.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1024", ChatOnWebConstants.EMAIL_TEMPLATE_NAME);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_BODY)){
						body = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_BODY).toString();
						if(!(null != body && body.length() > 0)){
							throw new ChatOnJsonParseException("VE_1039", ChatOnWebConstants.EMAIL_TEMPLATE_BODY);
						}else{
							body=body.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1038", ChatOnWebConstants.EMAIL_TEMPLATE_BODY);
					}
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_BODY_BY_NAME_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_BODY_BY_NAME_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1000",ChatOnWebConstants.JSON_EMAIL_TEMPLATE_BODY_BY_NAME_UPDATE);
		}
		final Map<String, String> map=new HashMap<String,String>(2);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_NAME, name);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_BODY, body);
		return map;
	}
}