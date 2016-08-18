package com.appdevsolutions.chat.web.controller;

import java.time.LocalDateTime;
import java.util.Comparator;
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

import com.appdevsolutions.chat.common.algo.sorter.IdSorterAsc;
import com.appdevsolutions.chat.common.algo.sorter.IdSorterDesc;
import com.appdevsolutions.chat.common.algo.sorter.NameSorterAsc;
import com.appdevsolutions.chat.common.algo.sorter.NameSorterDesc;
import com.appdevsolutions.chat.common.model.ErrorCodeModel;
import com.appdevsolutions.chat.web.exception.ErrorCodeAlreadyExistException;
import com.appdevsolutions.chat.web.exception.ErrorCodeNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.ErrorCodeMessageUpdateService;

@Controller
public class ErrorCodeMessageUpdateController {

	private static final Logger LOGGER=LoggerFactory.getLogger(ErrorCodeMessageUpdateController.class);
	
	@Autowired
	ErrorCodeMessageUpdateService errorCodeMessageUpdateService;
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE,method=RequestMethod.POST)
	public ResponseEntity<?> createErrorCode(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String, String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.ERROR_CODE_MAP);
		LOGGER.info("map values : "+map);
		request.removeAttribute(ChatOnWebConstants.ERROR_CODE_MAP);
		try {
			errorCodeMessageUpdateService.save(map.get(ChatOnWebConstants.ERROR_CODE), map.get(ChatOnWebConstants.ERROR_MESSAGE));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (ErrorCodeAlreadyExistException roleAlreadyExistException) {
			LOGGER.error("Error Code Already Exist : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Internal Server Error : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE,method=RequestMethod.PUT)
	public ResponseEntity<?> update(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		@SuppressWarnings("unchecked")
		final Map<String, String> map=(Map<String, String>)request.getAttribute(ChatOnWebConstants.ERROR_CODE_MAP);
		LOGGER.info("map values : "+map);
		request.removeAttribute(ChatOnWebConstants.ERROR_CODE_MAP);
		try {
			errorCodeMessageUpdateService.update(map.get(ChatOnWebConstants.ERROR_CODE), map.get(ChatOnWebConstants.ERROR_MESSAGE));
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (ErrorCodeAlreadyExistException roleAlreadyExistException) {
			LOGGER.error("Error Code Already Exist : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("Internal Server Error occured : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE_BY_KEY,method=RequestMethod.DELETE)
	public ResponseEntity<?> remove(@PathVariable String errorCode, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			errorCodeMessageUpdateService.delete(errorCode);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (ErrorCodeNotFoundException roleAlreadyExistException) {
			LOGGER.error("User not found : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE_BY_KEY,method=RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable String errorCode, HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, errorCodeMessageUpdateService.getMessageByKey(errorCode));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (ErrorCodeNotFoundException roleAlreadyExistException) {
			LOGGER.error("User not found : ",roleAlreadyExistException);
			final Response response=new Response(roleAlreadyExistException.getRejectType(), roleAlreadyExistException.getRejectValue(),roleAlreadyExistException.getCode(),roleAlreadyExistException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.BAD_REQUEST.toString(), HttpStatus.BAD_REQUEST, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE_ALL,method=RequestMethod.GET)
	public ResponseEntity<?> getAll(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final Comparator<ErrorCodeModel> comparator=new IdSorterAsc<ErrorCodeModel>();
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<ErrorCodeModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, errorCodeMessageUpdateService.getAll(comparator));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE_ALL_SORT_BY_ID_DESC,method=RequestMethod.GET)
	public ResponseEntity<?> getAllSortByIdDesc(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final Comparator<ErrorCodeModel> comparator=new IdSorterDesc<ErrorCodeModel>();
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<ErrorCodeModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, errorCodeMessageUpdateService.getAll(comparator));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE_ALL_SORT_BY_NAME_ASC,method=RequestMethod.GET)
	public ResponseEntity<?> getAllSortByNameAsc(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final Comparator<ErrorCodeModel> comparator=new NameSorterAsc<ErrorCodeModel>();
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<ErrorCodeModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, errorCodeMessageUpdateService.getAll(comparator));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value=ChatOnWebConstants.URL_HOME_ERROR_CODE_ALL_SORT_BY_NAME_DESC,method=RequestMethod.GET)
	public ResponseEntity<?> getAllSortByNameDesc(HttpServletRequest request){
		LOGGER.info("requested url : "+request.getRequestURL().toString());
		try {
			final Comparator<ErrorCodeModel> comparator=new NameSorterDesc<ErrorCodeModel>();
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<Set<ErrorCodeModel>>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, errorCodeMessageUpdateService.getAll(comparator));
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("User not found : ",e);
			final Response response=new Response("","","", e.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), HttpStatus.INTERNAL_SERVER_ERROR, ResponseStatus.FAILURE, response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
}
