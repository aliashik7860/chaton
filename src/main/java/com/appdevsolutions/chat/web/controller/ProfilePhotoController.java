package com.appdevsolutions.chat.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.appdevsolutions.chat.common.model.ImageType;
import com.appdevsolutions.chat.common.model.ProfilePhotoModel;
import com.appdevsolutions.chat.web.exception.PhotoNotFoundException;
import com.appdevsolutions.chat.web.exception.UserNotFoundException;
import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.MessageModel;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.appdevsolutions.chat.web.service.ProfilePhotoService;

@Controller
public class ProfilePhotoController {
	
	@Autowired
	ProfilePhotoService profilePhotoService;
	
	@RequestMapping(value=ChatOnWebConstants.URL_PROFILE_PHOTO_UPDATE,method=RequestMethod.PUT)
	public ResponseEntity<?> updateProfilePhoto(@RequestParam(ChatOnWebConstants.USER_ID) final String userId,@RequestParam(ChatOnWebConstants.PROFILE_PHOTO) final MultipartFile multipartFile,final HttpServletRequest request) throws IOException{
		final ProfilePhotoModel profilePhotoModel=new ProfilePhotoModel("", userId, multipartFile.getOriginalFilename(), ImageType.IMAGE_PNG, multipartFile.getBytes().length, multipartFile.getBytes(), LocalDateTime.now());
		try {
			profilePhotoService.update(profilePhotoModel);
			final ChatOnResponse<?> chatOnResponse=new ChatOnResponse<MessageModel>(LocalDateTime.now(), request.getRequestURL().toString(), HttpStatus.OK.toString(), HttpStatus.OK, ResponseStatus.SUCCESS, null);
			return new ResponseEntity<ChatOnResponse<?>>(chatOnResponse,HttpStatus.OK);
		} catch (UserNotFoundException userNotFoundException) {
			final Response response=new Response(ChatOnWebConstants.USER_ID, userId,userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_PROFILE_PHOTO_UPLOAD,method=RequestMethod.POST)
	public ResponseEntity<?> saveProfilePhoto(@RequestParam(ChatOnWebConstants.USER_ID) final String userId,@RequestParam(ChatOnWebConstants.PROFILE_PHOTO) final MultipartFile multipartFile,final HttpServletRequest request) throws IOException{
		final ProfilePhotoModel profilePhotoModel=new ProfilePhotoModel("", userId, multipartFile.getOriginalFilename(), ImageType.IMAGE_PNG, multipartFile.getBytes().length, multipartFile.getBytes(), LocalDateTime.now());
		try {
			profilePhotoService.save(profilePhotoModel);
			return new ResponseEntity<String>("",HttpStatus.OK);
		} catch (UserNotFoundException userNotFoundException) {
			final Response response=new Response(ChatOnWebConstants.USER_ID, userId,userNotFoundException.getCode(), userNotFoundException.getMessage());
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value=ChatOnWebConstants.URL_PROFILE_PHOTO_BY_USER_ID,method=RequestMethod.GET)
	public ResponseEntity<?> getProfilePhoto(@PathVariable(ChatOnWebConstants.USER_ID) String userId, HttpServletRequest request){
		if(userId==null||userId.equals("")) {
			final Response response=new Response(ChatOnWebConstants.USER_ID, userId,"", "Please provide userId");
			final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
			return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
		}else{
			try{
				final ProfilePhotoModel profilePhotoModel=profilePhotoService.findByUserId(userId);
				//inPhotoModel = inPhotoService.findPhoto(photoId);
				final MediaType mediaType=MediaType.parseMediaType(profilePhotoModel.getContentType().getValue());
				final HttpHeaders headers=new HttpHeaders();
				headers.setContentType(mediaType);
				headers.set(ChatOnWebConstants.DOWNLOAD_CONTENT_DISPOSITION_HEADER_KEY,ChatOnWebConstants.DOWNLOAD_FILENAME_HEADER_KEY +profilePhotoModel.getName());
				headers.setContentLength(profilePhotoModel.getBytes().length);
				final InputStream inputStream=new ByteArrayInputStream(profilePhotoModel.getBytes());
				final InputStreamResource inputStreamResource=new InputStreamResource(inputStream);
				return new ResponseEntity<InputStreamResource>(inputStreamResource,headers,HttpStatus.OK);
			}catch(PhotoNotFoundException photoNotFoundException){
				final Response response=new Response(ChatOnWebConstants.PHOTO_ID, userId,photoNotFoundException.getCode(), photoNotFoundException.getMessage());
				final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
				return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
			}catch(UserNotFoundException userNotFoundException){
				final Response response=new Response(ChatOnWebConstants.USER_ID, userId,userNotFoundException.getCode(), userNotFoundException.getMessage());
				final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
				return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
			}catch(IOException ioException){
				final Response response=new Response(ChatOnWebConstants.USER_ID, userId,"", ioException.getMessage());
				final ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(),request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,response);
				return new ResponseEntity<ChatOnResponse<Response>>(chatOnResponse,HttpStatus.BAD_REQUEST);
			}
		}
	}
}