package com.appdevsolutions.chat.web.rest.validation;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.EmailTemplateModel;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class EmailTemplateModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(EmailTemplateModelJsonValidator.class);
	
	public static EmailTemplateModel validate(String json)throws ChatOnJsonParseException{
		String name="";
		String subject="";
		String from="";
		String body="";
		final JSONParser parser = new JSONParser();

		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
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
	
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT)){
						subject = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT).toString();
						if(!(null != subject && subject.length() > 0)){
							throw new ChatOnJsonParseException("VE_1031", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT);
						}else{
							subject=subject.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1030", ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_FROM)){
						from = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_FROM).toString();
						if(!(null != from && from.length() > 0)){
							throw new ChatOnJsonParseException("VE_1037", ChatOnWebConstants.EMAIL_TEMPLATE_FROM);
						}else{
							from=from.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1036", ChatOnWebConstants.EMAIL_TEMPLATE_FROM);
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
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_EMAIL_TEMPLATE_CREATE);
		}
		final EmailTemplateModel emailTemplateModel=new EmailTemplateModel("", name, subject, from, body, LocalDateTime.now(), LocalDateTime.now());
		return emailTemplateModel;
	}
}