package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

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

import com.appdevsolutions.chat.web.exception.EmailNotificationTemplateAlreadyExistException;
import com.appdevsolutions.chat.web.exception.EmailNotificationTemplateNotFoundException;
import com.appdevsolutions.chat.web.exception.EmailTemplateNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.EmailNotificationTemplateModel;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.EmailNotificationTemplateService;

@Controller
public class EmailNotificationTemplateController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(EmailNotificationTemplateController.class);
	
	@Autowired
	EmailNotificationTemplateService emailNotificationTemplateService;
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SAVE_BY_ID,method=RequestMethod.POST)
	public ResponseEntity<?> createEmailNotificationTemplateById(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String, String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_MAP_BY_ID);
		request.removeAttribute(ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_MAP_BY_ID);
		try {
			emailNotificationTemplateService.saveById(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_ID),map.get(ChatOnWebConstants.USER_ID));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailNotificationTemplateAlreadyExistException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SAVE_BY_NAME,method=RequestMethod.POST)
	public ResponseEntity<?> createEmailNotificationTemplateByName(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String, String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_MAP_BY_NAME);
		request.removeAttribute(ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_MAP_BY_NAME);
		try {
			emailNotificationTemplateService.saveByName(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_NAME),map.get(ChatOnWebConstants.USER_ID));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailNotificationTemplateAlreadyExistException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SAVE_BY_SUBJECT,method=RequestMethod.POST)
	public ResponseEntity<?> createEmailNotificationTemplateBySubject(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String, String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_MAP_BY_SUBJECT);
		request.removeAttribute(ChatOnWebConstants.EMAIL_NOTIFICATION_TEMPLATE_MAP_BY_SUBJECT);
		try {
			emailNotificationTemplateService.saveBySubject(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT),map.get(ChatOnWebConstants.USER_ID));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailNotificationTemplateAlreadyExistException emailTemplateAlreadyExistException) {
			LOGGER.error("EmailTemplate is already exist : ",emailTemplateAlreadyExistException);
			final Response response=new Response(emailTemplateAlreadyExistException.getRejectType(), emailTemplateAlreadyExistException.getRejectValue(),emailTemplateAlreadyExistException.getCode(),emailTemplateAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getAll(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getByAll());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable String emailNotificationTemplateId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<EmailNotificationTemplateModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getById(emailNotificationTemplateId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getByEmailTemplateId(@PathVariable String emailTemplateId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getByEmailTemplateId(emailTemplateId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME,method=RequestMethod.GET)
	public ResponseEntity<?> getByEmailTemplateName(@PathVariable String emailTemplateName, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getByEmailTemplateName(emailTemplateName));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_ID_WITH_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getByEmailTemplateIdWithUserId(@PathVariable String emailTemplateId, @PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getByEmailTemplateIdWithUserId(emailTemplateId,userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME_WITH_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getByEmailTemplateNameWithUserId(@PathVariable String emailTemplateName, @PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getByEmailTemplateNameWithUserId(emailTemplateName,userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getByUserId(@PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.getByUserId(userId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByEmailTemplateId(@PathVariable String emailTemplateId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.removeByEmailTemplateId(emailTemplateId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByEmailTemplateName(@PathVariable String emailTemplateName, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.removeByEmailTemplateName(emailTemplateName);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_ID_WITH_USER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByEmailTemplateIdWithUserId(@PathVariable String emailTemplateId, @PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.removeByEmailTemplateIdWithUserId(emailTemplateId,userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_EMAIL_TEMPLATE_NAME_WITH_USER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByEmailTemplateNameWithUserId(@PathVariable String emailTemplateName, @PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.removeByEmailTemplateNameWithUserId(emailTemplateName,userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeById(@PathVariable String emailNotificationTemplateId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.removeById(emailNotificationTemplateId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_USER_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByUserId(@PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.removeByUserId(userId);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/*@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> checkActiveById(@PathVariable String emailNotificationTemplateId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Boolean>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailNotificationTemplateService.isActive(emailNotificationTemplateId));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SET_ACTIVE_BY_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> setActiveById(@PathVariable String emailNotificationTemplateId, @PathVariable Boolean active, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.setActiveById(emailNotificationTemplateId, active);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Boolean>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SET_ACTIVE_BY_USER_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> setActiveByUserId(@PathVariable String userId, @PathVariable Boolean active, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.setActiveByUserId(userId, active);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Boolean>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SET_ACTIVE_BY_EMAIL_TEMPLATE_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> setActiveByEmailTemplateId(@PathVariable String emailTemplateId, @PathVariable Boolean active, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.setActiveByEmailTemplateId(emailTemplateId,active);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SET_ACTIVE_BY_EMAIL_TEMPLATE_NAME,method=RequestMethod.PUT)
	public ResponseEntity<?> setActiveByEmailTemplateName(@PathVariable String emailTemplateName, @PathVariable Boolean active, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.setActiveByEmailTemplateName(emailTemplateName,active);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SET_ACTIVE_BY_EMAIL_TEMPLATE_ID_WITH_USER_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> setActiveByEmailTemplateIdWithUserId(@PathVariable String emailTemplateId, @PathVariable Boolean active, @PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.setActiveByEmailTemplateIdWithUserId(emailTemplateId,userId,active);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_NOTIFICATION_TEMPLATE_SET_ACTIVE_BY_EMAIL_TEMPLATE_NAME_WITH_USER_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> setActiveByEmailTemplateNameWithUserId(@PathVariable String emailTemplateName, @PathVariable Boolean active, @PathVariable String userId, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailNotificationTemplateService.setActiveByEmailTemplateNameWithUserId(emailTemplateName,userId,active);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailNotificationTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (UserNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		} catch (EmailNotificationTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}