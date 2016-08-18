package com.appdevsolutions.chat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VideoController {

	@RequestMapping(value="/home/videoPage",method=RequestMethod.GET)
	public String getVideoPage() {
		return "/videoPage";
	}
}
