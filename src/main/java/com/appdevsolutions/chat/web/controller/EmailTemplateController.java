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

import com.appdevsolutions.chat.web.exception.EmailTemplateAlreadyExistException;
import com.appdevsolutions.chat.web.exception.EmailTemplateNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.EmailTemplateModel;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.EmailTemplateService;

@Controller
public class EmailTemplateController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(EmailTemplateController.class);
	
	@Autowired
	EmailTemplateService emailTemplateService;
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_CREATE,method=RequestMethod.POST)
	public ResponseEntity<?> createEmailTemplate(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		final EmailTemplateModel emailTemplateModel=(EmailTemplateModel)request.getAttribute("emailTemplateModel");
		request.removeAttribute("emailTemplateModel");
		try {
			emailTemplateService.save(emailTemplateModel);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateAlreadyExistException emailTemplateAlreadyExistException) {
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
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BY_ID,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeById(@PathVariable String id, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailTemplateService.deleteById(id);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BY_NAME,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeByName(@PathVariable String name, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailTemplateService.deleteByName(name);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BY_SUBJECT,method=RequestMethod.DELETE)
	public ResponseEntity<?> removeBySubject(@PathVariable String subject, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			emailTemplateService.deleteBySubject(subject);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<String>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS,null );
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(), emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getAll( HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<EmailTemplateModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailTemplateService.getAll());
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BY_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable String id, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<EmailTemplateModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailTemplateService.getById(id));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BY_NAME,method=RequestMethod.GET)
	public ResponseEntity<?> getByName(@PathVariable String name, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<EmailTemplateModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailTemplateService.getByName(name));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BY_SUBJECT,method=RequestMethod.GET)
	public ResponseEntity<?> getBySubject(@PathVariable String subject, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<EmailTemplateModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, emailTemplateService.getBySubject(subject));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_UPDATE_BY_NAME,method=RequestMethod.PUT)
	public ResponseEntity<?> updateName(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_NAME);
		request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_NAME);
		try {
			final String oldName=map.get(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_NAME);
			final String newName=map.get(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_NAME);
			emailTemplateService.updateName(oldName, newName);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailTemplateAlreadyExistException roleAlreadyExistException) {
			LOGGER.error("Role already exist : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_UPDATE_BY_SUBJECT,method=RequestMethod.PUT)
	public ResponseEntity<?> updateBySubject(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_SUBJECT);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_SUBJECT);
			emailTemplateService.updateSubject(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_OLD_SUBJECT),map.get(ChatOnWebConstants.EMAIL_TEMPLATE_NEW_SUBJECT));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("Could not process the requested details : ",emailTemplateNotFoundException);
			final Response response=new Response(emailTemplateNotFoundException.getRejectType(), emailTemplateNotFoundException.getRejectValue(),emailTemplateNotFoundException.getCode(), emailTemplateNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (EmailTemplateAlreadyExistException roleAlreadyExistException) {
			LOGGER.error("Role already exist : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Could not process the requested details : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_FROM_BY_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> updateEmailFromById(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_ID);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_ID);
			emailTemplateService.updateEmailFromById(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_ID), map.get(ChatOnWebConstants.EMAIL_TEMPLATE_FROM));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_FROM_BY_NAME,method=RequestMethod.PUT)
	public ResponseEntity<?> updateEmailFromByName(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_NAME);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_NAME);
			emailTemplateService.updateEmailFromByName(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_NAME), map.get(ChatOnWebConstants.EMAIL_TEMPLATE_FROM));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_FROM_BY_SUBJECT,method=RequestMethod.PUT)
	public ResponseEntity<?> updateEmailFromBySubject(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_SUBJECT);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_FROM_BY_SUBJECT);
			emailTemplateService.updateEmailFromBySubject(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT), map.get(ChatOnWebConstants.EMAIL_TEMPLATE_FROM));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BODY_BY_ID,method=RequestMethod.PUT)
	public ResponseEntity<?> updateBodyById(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_BODY_BY_ID);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_BODY_BY_ID);
			emailTemplateService.updateBodyById(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_ID), map.get(ChatOnWebConstants.EMAIL_TEMPLATE_BODY));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
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
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BODY_BY_NAME,method=RequestMethod.PUT)
	public ResponseEntity<?> updateBodyByName(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_BODY_BY_NAME);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_BODY_BY_NAME);
			emailTemplateService.updateBodyByName(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_NAME), map.get(ChatOnWebConstants.EMAIL_TEMPLATE_BODY));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("EmailTemplate not found : ",emailTemplateNotFoundException);
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
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_EMAIL_TEMPLATE_BODY_BY_SUBJECT,method=RequestMethod.PUT)
	public ResponseEntity<?> updateBodyBySubject(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			@SuppressWarnings("unchecked")
			final Map<String,String> map=(Map<String,String>)request.getAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_BODY_BY_SUBJECT);
			request.removeAttribute(ChatOnWebConstants.UPDATE_EMAIL_TEMPLATE_BODY_BY_SUBJECT);
			emailTemplateService.updateBodyByName(map.get(ChatOnWebConstants.EMAIL_TEMPLATE_SUBJECT), map.get(ChatOnWebConstants.EMAIL_TEMPLATE_BODY));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (EmailTemplateNotFoundException emailTemplateNotFoundException) {
			LOGGER.error("EmailTemplate not found : ",emailTemplateNotFoundException);
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