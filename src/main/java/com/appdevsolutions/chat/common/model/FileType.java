package com.appdevsolutions.chat.common.model;

public enum FileType 
{
	FILE_TXT("file/txt"),
	FILE_C("file/c"),
	FILE_CPP("file/cpp"),
	FILE_JAVA("file/java"),
	FILE_PHP("file/php"),
	FILE_HTML("file/html"),
	FILE_CSS("file/css"),
	FILE_JS("file/js"),
	FILE_RTF("file/rtf"),
	FILE_PDF("file/pdf"),
	FILE_DOC("file/doc"),
	FILE_DOCX("file/docx"),
	FILE_XLS("file/xls"),
	FILE_XLSX("file/xlsx"),
	FILE_ODS("file/ods"),
	FILE_CSV("file/csv"),
	FILE_ZIP("file/zip"),
	FILE_7Z("file/7z"),
	FILE_RAR("file/rar"),
	FILE_JAR("file/jar"),
	FILE_WAR("file/war"),
	FILE_EAR("file/ear");
	
	private final String value;
	
	private FileType(String value){
		this.value=value;
	}
	public String getValue(){
		return value;
	}
	public static FileType parse(String value){
		for(FileType imageType:FileType.values()){
			if(imageType.getValue().equals(value)){
				return imageType;
			}
		}
		return null;
	}
}