package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appdevsolutions.chat.web.exception.MessageNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.MessageModel;
import com.appdevsolutions.chat.web.model.MessagesModel;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.MessageService;

@Controller
public class MessageController {
	
	@Autowired
	MessageService messageService;
	
	private static final Logger LOGGER=LoggerFactory.getLogger(MessageController.class);

	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_CREATE,method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> createMessage(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		final MessagesModel messagesModel=(MessagesModel)request.getAttribute(ChatOnWebConstants.MESSAGES_MODEL);
		request.removeAttribute(ChatOnWebConstants.MESSAGES_MODEL);
		String rejectedValues="";
		UserNotFoundException userNotFoundException=null;
		for(int i=0;i<messagesModel.getReceiverIds().size();i++){
			try{
				final MessageModel messageModel=new MessageModel("", messagesModel.getSenderId(), messagesModel.getReceiverIds().get(i), messagesModel.getMessage(), messagesModel.getCreateTimestamp());
				messageService.save(messageModel);
			}catch(UserNotFoundException e){
				userNotFoundException=e;
				rejectedValues=rejectedValues+e.getRejectValue();
				if(i+1!=messagesModel.getReceiverIds().size()){
					rejectedValues=rejectedValues+",";
				}	
			}
		}
		if(!rejectedValues.equals("")&&userNotFoundException!=null){
			LOGGER.error("User not found : ",userNotFoundException);
			final Response response=new Response(userNotFoundException.getRejectType(), userNotFoundException.getRejectValue(),userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}else{
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<MessageModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getAllIn(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getAllIn());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getInById(@PathVariable String messageId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<MessageModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getInById(messageId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (MessageNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_BY_SENDER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getOutBySenderId(@PathVariable String senderId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getOutBySenderId(senderId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_BY_SENDER_ID_WITH_TEXT,method=RequestMethod.GET)
	public ResponseEntity<?> getOutBySenderIdWithText(@PathVariable String senderId, @PathVariable String text, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getOutBySenderIdWithText(senderId,text));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_BY_SENDER_ID_WITH_TIME,method=RequestMethod.GET)
	public ResponseEntity<?> getOutBySenderIdWithTime(@PathVariable String senderId, @PathVariable String time, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final LocalDateTime createTimestamp=LocalDateTime.parse(time);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getOutBySenderIdWithTime(senderId,createTimestamp));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_BY_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteInById(@PathVariable String messageId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			messageService.deleteInById(messageId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (MessageNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_BY_SENDER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteOutBySenderId(@PathVariable String senderId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			messageService.deleteOutBySenderId(senderId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (MessageNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getAllOut(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getAllOut());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getOutById(@PathVariable String messageId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<MessageModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getOutById(messageId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (MessageNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_BY_RECEIVER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getInByReceiverId(@PathVariable String receiverId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getInByReceiverId(receiverId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_BY_RECEIVER_ID_WITH_TEXT,method=RequestMethod.GET)
	public ResponseEntity<?> getInByReceiverIdWithText(@PathVariable String receiverId, @PathVariable String text, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getInByReceiverIdWithText(receiverId,text));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_BY_RECEIVER_ID_WITH_TIME,method=RequestMethod.GET)
	public ResponseEntity<?> getInByReceiverIdWithTime(@PathVariable String receiverId, @PathVariable String time, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final LocalDateTime createTimestamp=LocalDateTime.parse(time);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<List<MessageModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,messageService.getInByReceiverIdWithTime(receiverId,createTimestamp));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_OUT_BY_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteOutById(@PathVariable String messageId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			messageService.deleteOutById(messageId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (MessageNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_MESSAGE_IN_BY_RECEIVER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteInByReceiverId(@PathVariable String receiverId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			messageService.deleteInByReceiverId(receiverId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (MessageNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (UserNotFoundException privilegeNotFoundException) {
			LOGGER.error("User not found : ",privilegeNotFoundException);
			final Response response=new Response(privilegeNotFoundException.getRejectType(), privilegeNotFoundException.getRejectValue(), privilegeNotFoundException.getCode(), privilegeNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response(null,null,"", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
}