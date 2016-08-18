package com.appdevsolutions.chat.web.rest.validation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.FriendShipModel;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class FriendShipModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(FriendShipModelJsonValidator.class);
	public static FriendShipModel validate(String json)throws ChatOnJsonParseException{
		String senderId="";
		String receiverId="";
		String comment="";
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
	
					if(jsonObject.containsKey(ChatOnWebConstants.RECEIVER_ID)){
						receiverId = jsonObject.get(ChatOnWebConstants.RECEIVER_ID).toString();
						if(!(null != receiverId && receiverId.length() > 0)){
							throw new ChatOnJsonParseException("VE_1096", ChatOnWebConstants.RECEIVER_ID);
						}else{
							receiverId=receiverId.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1095", ChatOnWebConstants.RECEIVER_ID);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.COMMENT)){
						comment = jsonObject.get(ChatOnWebConstants.COMMENT).toString();
						if(null!=comment&&comment.length()>0){
							comment=comment.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1097", ChatOnWebConstants.COMMENT);
					}
					
				}else{
					throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_FRIEND_SHIP_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("VE_1000", ChatOnWebConstants.JSON_FRIEND_SHIP_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("VE_1001",ChatOnWebConstants.JSON_FRIEND_SHIP_CREATE);
		}
		final FriendShipModel friendShipModel=new FriendShipModel("", senderId, receiverId, comment, null,null,null, null);
		return friendShipModel;
	}
}