package com.example.hometest.BasicAuthen;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.example.hometest.Response.ResponseDto;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setErrorCode(HttpServletResponse.SC_UNAUTHORIZED);
        responseDto.setErrorDescription(authException.getMessage());
        responseDto.setResponse(null);

        PrintWriter writer = response.getWriter();
        writer.println(new Gson().toJson(responseDto));
    }
}
