package com.appdevsolutions.chat.web.rest.validation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.MessagesModel;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class MessagesModelJsonValidator {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MessagesModelJsonValidator.class);
	
	@SuppressWarnings("unchecked")
	public static MessagesModel validate(String json)throws ChatOnJsonParseException{
		String senderId="";
		List<String> receiverId=new ArrayList<String>();
		String message="";
		final JSONParser parser = new JSONParser();

		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.SENDER_ID)){
						senderId = jsonObject.get(ChatOnWebConstants.SENDER_ID).toString();
						if(!(null != senderId && senderId.length() > 0)){
							throw new ChatOnJsonParseException("VE_1094", ChatOnWebConstants.SENDER_ID);
						}else{
							senderId=senderId.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1093", ChatOnWebConstants.SENDER_ID);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.RECEIVER_IDS)){
						LOGGER.info("receiverIds : "+jsonObject.get(ChatOnWebConstants.RECEIVER_IDS));
						receiverId=(List<String>)jsonObject.get(ChatOnWebConstants.RECEIVER_IDS);
						//receiverId = jsonObject.get(ChatOnWebConstants.RECEIVER_IDS);
						if(!(null != receiverId && receiverId.size() > 0)){
							throw new ChatOnJsonParseException("VE_1096", ChatOnWebConstants.RECEIVER_ID);
						}else{
							//receiverId=receiverId.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1095", ChatOnWebConstants.RECEIVER_ID);
					}
					
					if(senderId.equals(receiverId)){
						throw new ChatOnJsonParseException("VE_1102", "");
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.MESSAGE)){
						message = jsonObject.get(ChatOnWebConstants.MESSAGE).toString();
						if(!(null != message && message.length() > 0)){
							throw new ChatOnJsonParseException("VE_1101", ChatOnWebConstants.MESSAGE);
						}
					}else{
						throw new ChatOnJsonParseException("VE_1100", ChatOnWebConstants.MESSAGE);
					}
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_MESSAGE_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_MESSAGE_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_MESSAGE_CREATE);
		}
		final MessagesModel messagesModel=new MessagesModel("",senderId,receiverId, message, LocalDateTime.now());
		return messagesModel;
	}
}