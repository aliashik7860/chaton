package com.appdevsolutions.chat.common.model;

public enum ImageType {
	IMAGE_JPG("image/jpg"),
	IMAGE_PNG("image/png"),
	IMAGE_GIF("image/gif");
	private final String value;
	private ImageType(String value){
		this.value=value;
	}
	public String getValue(){
		return value;
	}
	public static ImageType parse(String value){
		for(ImageType imageType:ImageType.values()){
			if(imageType.getValue().equals(value)){
				return imageType;
			}
		}
		return null;
	}
}