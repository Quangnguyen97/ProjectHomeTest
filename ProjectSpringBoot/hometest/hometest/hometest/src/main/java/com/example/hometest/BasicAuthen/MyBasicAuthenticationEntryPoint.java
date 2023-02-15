package com.example.hometest.BasicAuthen;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.hometest.Response.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private ResponseDto responseDto = new ResponseDto();
    private final ObjectMapper objectMapper;

    @Autowired
    public MyBasicAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter writer = response.getWriter();
        writer.println("HTTP Status 401 - " + authException.getMessage());
        responseDto.setErrorCode(HttpServletResponse.SC_UNAUTHORIZED);
        responseDto.setErrorDescription(authException.getMessage());
        responseDto.setResponse(null);
        responseText(response, responseDto);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("admin");
        super.afterPropertiesSet();
    }

    private static void responseText(HttpServletResponse response, ResponseDto responseDto) throws IOException {
        byte[] bytes = String.valueOf(responseDto).getBytes(StandardCharsets.UTF_8);
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
        response.flushBuffer();
    }
}
