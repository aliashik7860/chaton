package com.appdevsolutions.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appdevsolutions.chat.web.service.AudioService;

@Controller
public class AudioController {

	@Autowired
	AudioService audioService;
	
	
	@RequestMapping(value="/home/audioPage",method=RequestMethod.GET)
	public String getAudioPage() {
		return "/audioPage";
	}
	
	
}
