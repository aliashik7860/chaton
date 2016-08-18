package com.appdevsolutions.chat.web.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.appdevsolutions.chat.web.model.ChatOnResponse;
import com.appdevsolutions.chat.web.model.Response;
import com.appdevsolutions.chat.web.model.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HttpAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER=LoggerFactory.getLogger(HttpAuthenticationEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
    	//response.setContentType("application/json");
    	
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        final Response failureBean=new Response("", "","", authException.getMessage());
        ChatOnResponse<Response> chatOnResponse=new ChatOnResponse<Response>(LocalDateTime.now(), request.getRequestURL().toString(),HttpStatus.UNAUTHORIZED.toString(),HttpStatus.UNAUTHORIZED,ResponseStatus.FAILURE,failureBean);
        final ObjectMapper objectMapper=new ObjectMapper();
        //objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        final Object object=objectMapper.readValue(objectMapper.writeValueAsString(chatOnResponse),Object.class);
        String json=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        response.getWriter().write(json.toString());
        response.getWriter().flush();
        LOGGER.info(response.toString());
    }
}