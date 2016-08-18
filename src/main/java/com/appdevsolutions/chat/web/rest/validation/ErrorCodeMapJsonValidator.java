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

public class ErrorCodeMapJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(ErrorCodeMapJsonValidator.class);
	private static final String ERROR_CODE_REGEX="^[A-Z]{2}_[0-9]{4}$";
	public static Map<String, String> validate(String json)throws ChatOnJsonParseException{
		String errorCode="";
		String errorMessage="";
		final JSONParser parser = new JSONParser();
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.ERROR_CODE)){
						errorCode = jsonObject.get(ChatOnWebConstants.ERROR_CODE).toString();
						if(!(null != errorCode && errorCode.length() > 0)){
							throw new ChatOnJsonParseException("VE_1088", ChatOnWebConstants.ERROR_CODE);
						}else if(errorCode.length()!=7){
							throw new ChatOnJsonParseException("VE_1089",ChatOnWebConstants.ERROR_CODE);
						}else if(!errorCode.matches(ERROR_CODE_REGEX)){
							throw new ChatOnJsonParseException("VE_1090", ChatOnWebConstants.ERROR_CODE);
						}else{
							errorCode=errorCode.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1087", ChatOnWebConstants.ERROR_CODE);
					}
	
					if(jsonObject.containsKey(ChatOnWebConstants.ERROR_MESSAGE)){
						errorMessage = jsonObject.get(ChatOnWebConstants.ERROR_MESSAGE).toString();
						if(!(null != errorMessage && errorMessage.length() > 0)){
							throw new ChatOnJsonParseException("VE_1092", ChatOnWebConstants.ERROR_MESSAGE);
						}else{
							errorMessage=errorMessage.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1091", ChatOnWebConstants.ERROR_MESSAGE);
					}
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_ERROR_CODE_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_ERROR_CODE_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_ERROR_CODE_CREATE);
		}
		final Map<String, String> map=new HashMap<String,String>(2);
		map.put(ChatOnWebConstants.ERROR_CODE, errorCode);
		map.put(ChatOnWebConstants.ERROR_MESSAGE, errorMessage);
		return map;
	}
}