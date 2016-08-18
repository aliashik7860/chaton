package com.appdevsolutions.chat.web.controller;


import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.service.CaptchaService;

@Controller
public class CaptchaController{
	
	@Autowired
	private CaptchaService captchaService;
	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaController.class);
	@RequestMapping(value=ChatOnWebConstants.URL_CAPTCHA, method=RequestMethod.GET, produces=MediaType.IMAGE_JPEG_VALUE)
	public @ResponseBody ResponseEntity<InputStreamResource> getCapthaPath(HttpSession session){
		logger.info("Captcha requested is accepted for registration");
        logger.info("Returning jpeg image response back to registration page with base 64 encoding scheme");
        final byte[] array=captchaService.getImageArray();
        final String generatedCaptcha=captchaService.getCaptchaString();
        logger.info("setting generated captcha to session : "+generatedCaptcha);
        session.setAttribute("captcha", generatedCaptcha);
        logger.info("set session with id : "+session.getAttribute("captcha"));
		final HttpHeaders headers=new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		headers.set(ChatOnWebConstants.DOWNLOAD_CONTENT_DISPOSITION_HEADER_KEY,ChatOnWebConstants.DOWNLOAD_FILENAME_HEADER_KEY +"captcha.jpg");
		headers.setContentLength(array.length);
		final InputStream inputStream=new ByteArrayInputStream(array);
		final InputStreamResource inputStreamResource=new InputStreamResource(inputStream);
		return new ResponseEntity<InputStreamResource>(inputStreamResource,headers,HttpStatus.OK);
	}
}