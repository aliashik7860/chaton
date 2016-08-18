package com.appdevsolutions.chat.web.rest.validation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appdevsolutions.chat.dao.entity.Address;
import com.appdevsolutions.chat.dao.entity.Name;
import com.appdevsolutions.chat.web.model.ChatOnWebConstants;
import com.appdevsolutions.chat.web.model.UserModel;
import com.appdevsolutions.chat.web.rest.validation.exception.ChatOnJsonParseException;

public class UserModelJsonValidator {
	private static final Logger LOGGER=LoggerFactory.getLogger(UserModelJsonValidator.class);
	private static final String regexMobile = "^\\d{10}$";
	
	public static UserModel validate(String json)throws ChatOnJsonParseException{
		String firstName="";
		String middleName="";
		String lastName="";
		String username="";
		String password="";
		String rePassword="";
		LocalDate dateOfBirth;
		String mobileNumber="";
		String houseNumber="";
		String street="";
		String city="";
		String state="";
		String country="";
		String captchaText="";
		final JSONParser parser = new JSONParser();
		try {
			if(json!=null&&!json.equals("")){
				JSONObject jsonObject = (JSONObject)parser.parse(json.toString());
				LOGGER.info("request json object : "+jsonObject);				
				if (null != jsonObject && !jsonObject.isEmpty()) {
					if(jsonObject.containsKey(ChatOnWebConstants.FIRST_NAME)){
						firstName = jsonObject.get(ChatOnWebConstants.FIRST_NAME).toString();
						if(!(null != firstName && firstName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1050", ChatOnWebConstants.FIRST_NAME);
						}else if(!StringUtils.isAlpha(firstName)){
							throw new ChatOnJsonParseException("VE_1051", ChatOnWebConstants.FIRST_NAME);
						}else{
							firstName=firstName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1049", ChatOnWebConstants.FIRST_NAME);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.MIDDLE_NAME)){
						middleName = jsonObject.get(ChatOnWebConstants.MIDDLE_NAME).toString();
						if(middleName.isEmpty()==false&&StringUtils.isAlpha(middleName)==true){
							throw new ChatOnJsonParseException("VE_1054", ChatOnWebConstants.MIDDLE_NAME);
						}else{
							middleName=middleName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1052", ChatOnWebConstants.MIDDLE_NAME);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.LAST_NAME)){
						lastName = jsonObject.get(ChatOnWebConstants.LAST_NAME).toString();
						if(!(null != lastName && lastName.length() > 0)){
							throw new ChatOnJsonParseException("VE_1056", ChatOnWebConstants.LAST_NAME);
						}else if(!StringUtils.isAlpha(lastName)){
							throw new ChatOnJsonParseException("VE_1057", ChatOnWebConstants.LAST_NAME);
						}else{
							lastName=lastName.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1055", ChatOnWebConstants.LAST_NAME);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.USERNAME)){
						username = jsonObject.get(ChatOnWebConstants.USERNAME).toString();
						if(!(null != username && username.length() > 0)){
							throw new ChatOnJsonParseException("VE_1005", ChatOnWebConstants.USERNAME);
						}else{
							username=username.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1004", ChatOnWebConstants.USERNAME);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.PASSWORD)){
						password= jsonObject.get(ChatOnWebConstants.PASSWORD).toString();
						if(!(null != password && password.length() > 0)){
							throw new ChatOnJsonParseException("VE_1059", ChatOnWebConstants.PASSWORD);
						}else if(password.length()<4||password.length()>9){
							throw new ChatOnJsonParseException("VE_1062", ChatOnWebConstants.PASSWORD);
						}else{
							password=password.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1058", ChatOnWebConstants.PASSWORD);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.RE_PASSWORD)){
						rePassword = jsonObject.get(ChatOnWebConstants.RE_PASSWORD).toString();
						if(!(null != rePassword && rePassword.length() > 0)){
							throw new ChatOnJsonParseException("VE_1061", ChatOnWebConstants.RE_PASSWORD);
						}else if(rePassword.length()<4||rePassword.length()>8){
							throw new ChatOnJsonParseException("VE_1062", ChatOnWebConstants.RE_PASSWORD);
						}else{
							rePassword=rePassword.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1060", ChatOnWebConstants.RE_PASSWORD);
					}
					if(!password.equals(rePassword)){
						throw new ChatOnJsonParseException("VE_1063", ChatOnWebConstants.PASSWORD);
					}
					if(jsonObject.containsKey(ChatOnWebConstants.DATE_OF_BIRTH)){
						final String temp = jsonObject.get(ChatOnWebConstants.DATE_OF_BIRTH).toString();
						if(!(null != temp && temp.length() > 0)){
							throw new ChatOnJsonParseException("VE_1066", ChatOnWebConstants.DATE_OF_BIRTH);
						}else{
							try{
								dateOfBirth=LocalDate.parse(temp);
								final Period age=Period.between(dateOfBirth, LocalDate.now());
								if(age.getYears()<18) {
									throw new ChatOnJsonParseException("VE_1068", ChatOnWebConstants.DATE_OF_BIRTH);
								}
							}catch(DateTimeParseException dateTimeParseException){
								throw new ChatOnJsonParseException("VE_1067", ChatOnWebConstants.DATE_OF_BIRTH);
							}
						}
					}else{
						throw new ChatOnJsonParseException("VE_1065", ChatOnWebConstants.DATE_OF_BIRTH);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.MOBILE_NUMBER)){
						mobileNumber= jsonObject.get(ChatOnWebConstants.MOBILE_NUMBER).toString();
						if(!(null != mobileNumber && mobileNumber.length() > 0)){
							throw new ChatOnJsonParseException("VE_1070", ChatOnWebConstants.MOBILE_NUMBER);
						}else if(!mobileNumber.matches(regexMobile)){
							throw new ChatOnJsonParseException("VE_1071", ChatOnWebConstants.MOBILE_NUMBER);
						}else{
							mobileNumber=mobileNumber.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1069", ChatOnWebConstants.MOBILE_NUMBER);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.HOUSE_NUMBER)){
						houseNumber= jsonObject.get(ChatOnWebConstants.HOUSE_NUMBER).toString();
						if(!(null != houseNumber && houseNumber.length() > 0)){
							throw new ChatOnJsonParseException("VE_1070", ChatOnWebConstants.HOUSE_NUMBER);
						}else{
							houseNumber=houseNumber.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1069", ChatOnWebConstants.HOUSE_NUMBER);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.STREET)){
						street= jsonObject.get(ChatOnWebConstants.STREET).toString();
						if(!(null != street && street.length() > 0)){
							throw new ChatOnJsonParseException("VE_1075", ChatOnWebConstants.STREET);
						}else{
							street=street.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1074", ChatOnWebConstants.STREET);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.CITY)){
						city= jsonObject.get(ChatOnWebConstants.CITY).toString();
						if(!(null != city && street.length() > 0)){
							throw new ChatOnJsonParseException("VE_1077", ChatOnWebConstants.CITY);
						}else{
							city=city.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1076", ChatOnWebConstants.CITY);
					}
					
					
					if(jsonObject.containsKey(ChatOnWebConstants.STATE)){
						state= jsonObject.get(ChatOnWebConstants.STATE).toString();
						if(!(null != state && state.length() > 0)){
							throw new ChatOnJsonParseException("VE_1080", ChatOnWebConstants.STATE);
						}else{
							state=state.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1079", ChatOnWebConstants.STATE);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.COUNTRY)){
						country= jsonObject.get(ChatOnWebConstants.COUNTRY).toString();
						if(!(null != country && country.length() > 0)){
							throw new ChatOnJsonParseException("VE_1083", ChatOnWebConstants.COUNTRY);
						}else{
							country=country.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1082", ChatOnWebConstants.COUNTRY);
					}
					
					if(jsonObject.containsKey(ChatOnWebConstants.CAPTCHA_TEXT)){
						captchaText= jsonObject.get(ChatOnWebConstants.CAPTCHA_TEXT).toString();
						if(!(null != captchaText && captchaText.length() > 0)){
							throw new ChatOnJsonParseException("VE_1086", ChatOnWebConstants.CAPTCHA_TEXT);
						}else{
							captchaText=captchaText.trim();
						}
					}else{
						throw new ChatOnJsonParseException("VE_1085", ChatOnWebConstants.CAPTCHA_TEXT);
					}
					
				}else{
					throw new ChatOnJsonParseException("1000", ChatOnWebConstants.JSON_USER_CREATE);
				}
			}else{
				throw new ChatOnJsonParseException("1000", ChatOnWebConstants.JSON_USER_CREATE);
			}
		} catch (ParseException parseException) {
			LOGGER.error("Json parse exception of "+json,parseException);
			throw new ChatOnJsonParseException("1001",ChatOnWebConstants.JSON_USER_CREATE);
		}
		final Name name=new Name();
		name.setFirstName(firstName);
		name.setMiddleName(middleName);
		name.setLastName(lastName);
		final Address address=new Address();
		address.setHouseNumber(houseNumber);
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setCountry(country);
		final UserModel userModel=new UserModel("", username, name, password, dateOfBirth, LocalDateTime.now(),LocalDateTime.now(), mobileNumber, address,true, null);
		return userModel;
	}
}