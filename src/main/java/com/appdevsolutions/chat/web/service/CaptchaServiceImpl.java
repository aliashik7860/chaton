package com.appdevsolutions.chat.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.appdevsolutions.chat.web.util.CaptchaGenerator;

@Component
public class CaptchaServiceImpl implements CaptchaService{
	
	@Autowired
	private CaptchaGenerator captchaGenerator;
	
	@Override
	public byte[] getImageArray() {
		return captchaGenerator.getCaptchaImageArray();
	}
	@Override
	public String getCaptchaString() {
		return captchaGenerator.getCaptchaString();
	}
}