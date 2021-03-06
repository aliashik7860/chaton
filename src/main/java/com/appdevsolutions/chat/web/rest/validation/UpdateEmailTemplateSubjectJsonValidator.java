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

public class UpdateEmailTemplateSubjectJsonValidator {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateEmailTemplateSubjectJsonValidator.class);
	
	private final static JSONParser parser = new JSONParser();
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String oldSubject="";
		String newSubject="";
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT)){
						oldSubject = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT).toString();
						if(!(null != oldSubject && oldSubject.length() > 0)){
							throw new ChatOnJsonParseException("VE_1033", ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT);
						}else{
							oldSubject=oldSubject.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1032", ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT)){
						newSubject = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT).toString();
						if(!(null != newSubject && newSubject.length() > 0)){
							throw new ChatOnJsonParseException("VE_1035", ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT);
						}else{
							newSubject=newSubject.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1034", ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT);
					}
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_SUBJECT_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_SUBJECT_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_EMAIL_TEMPLATE_SUBJECT_UPDATE);
		}
		final Map<String, String> map=new HashMap<String,String>(2);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT, oldSubject);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT, newSubject);
		return map;
	}
}