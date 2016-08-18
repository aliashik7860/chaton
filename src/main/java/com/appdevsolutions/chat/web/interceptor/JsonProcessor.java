package com.appdevsolutions.chat.web.interceptor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;

import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.JsonValidationResponse;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonProcessor {
	public static void writeJsonValidationFailedResponse(HttpServletRequest request,HttpServletResponse response,JsonValidationResponse jsonValidationResponse)throws Exception{
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		final ChatOnResponse<JsonValidationResponse> chatOnResponse=new ChatOnResponse<JsonValidationResponse>(LocalDateTime.now(), request.getRequestURL().toString(),HttpStatus.BAD_REQUEST.toString(),HttpStatus.BAD_REQUEST,ResponseStatus.FAILURE,jsonValidationResponse);
		final ObjectMapper objectMapper=new ObjectMapper();
		final Object obj=objectMapper.readValue(objectMapper.writeValueAsString(chatOnResponse),Object.class);
		final String jsonString=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		response.getWriter().write(jsonString.toString());
		response.getWriter().flush();
	}
	public static String getBody(HttpServletRequest request)throws Exception{
		final StringBuilder stringBuilder=new StringBuilder();
		final InputStream inputStream=request.getInputStream();
		if (inputStream != null) {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final char[] charBuffer = new char[128];
            int bytesRead = -1;
            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        } else {
            stringBuilder.append("");
        }
		return stringBuilder.toString();
	}
}
