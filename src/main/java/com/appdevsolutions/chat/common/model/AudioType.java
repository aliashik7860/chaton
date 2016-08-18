package com.appdevsolutions.chat.common.model;

public enum AudioType {
	AUDIO_MP3("audio/mp3"),
	AUDIO_AAC("audio/aac"),
	AUDIO_WAV("audio/wav");
	
	private final String value;
	
	private AudioType(String value){
		this.value=value;
	}
	public String getValue(){
		return value;
	}
	public static AudioType parse(String value){
		for(AudioType imageType:AudioType.values()){
			if(imageType.getValue().equals(value)){
				return imageType;
			}
		}
		return null;
	}
}