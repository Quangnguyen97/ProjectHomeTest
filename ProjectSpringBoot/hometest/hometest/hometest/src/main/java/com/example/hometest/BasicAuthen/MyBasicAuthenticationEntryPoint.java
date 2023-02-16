package com.example.hometest.BasicAuthen;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

import com.example.hometest.Module.*;
import com.example.hometest.Response.*;

@Component
public class MyBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() {
        setRealmName("admin");
        super.afterPropertiesSet();
    }

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        try {
            response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            ResponseDto responseDto = new ResponseDto();
            responseDto.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            responseDto.setDescription(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED).getReasonPhrase());
            responseDto.setMessage(authException.getMessage());
            responseDto.setResponse(null);

            PrintWriter writer = response.getWriter();
            writer.println(new Gson().toJson(responseDto));
        } catch (Exception e) {
            throw new ResourceRuntimeException(e.getMessage());
        }
    }
}
