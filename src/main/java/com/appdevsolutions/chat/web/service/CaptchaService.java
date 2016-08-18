package com.appdevsolutions.chat.web.service;

public interface CaptchaService {
	public byte[] getImageArray();
	public String getCaptchaString();
}
