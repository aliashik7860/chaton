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

public class UpdateEmailTemplateFromByIdJsonValidator {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(UpdateEmailTemplateFromByIdJsonValidator.class);
	
	private final static JSONParser parser = new JSONParser();
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String id="";
		String from="";
		try {
			if(json!=null&&!json.equals("")){
				final JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.EMAIL_TEMPLATE_ID)){
						id = jsonObject.get(ChatOnWebConstants.EMAIL_TEMPLATE_ID).toString();
						if(!(null != id && id.length() > 0)){
							throw new ChatOnJsonParseException("VE_1023", ChatOnWebConstants.EMAIL_TEMPLATE_ID);
						}else{
							id=id.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1022", ChatOnWebConstants.EMAIL_TEMPLATE_ID);
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
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_FROM_BY_ID_UPDATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_EMAIL_TEMPLATE_FROM_BY_ID_UPDATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_EMAIL_TEMPLATE_FROM_BY_ID_UPDATE);
		}
		final Map<String, String> map=new HashMap<String,String>(2);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_ID, id);
		map.put(ChatOnWebConstants.EMAIL_TEMPLATE_FROM, from);
		return map;
	}
}