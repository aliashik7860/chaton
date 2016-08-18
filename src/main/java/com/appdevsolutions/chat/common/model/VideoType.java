package com.appdevsolutions.chat.common.model;

public enum VideoType {
	VIDEO_MP4("video/mp4"),
	VIDEO_WMV("video/wmv"),
	VIDEO_MKV("video/mkv"),
	VIDEO_3GP("video/3gp");
	
	private final String value;
	
	private VideoType(String value){
		this.value=value;
	}
	public String getValue(){
		return value;
	}
	public static VideoType parse(String value){
		for(VideoType imageType:VideoType.values()){
			if(imageType.getValue().equals(value)){
				return imageType;
			}
		}
		return null;
	}
}