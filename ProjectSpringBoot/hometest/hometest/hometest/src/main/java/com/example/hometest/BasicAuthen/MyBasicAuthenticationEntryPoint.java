package com.example.hometest.BasicAuthen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

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
        Map<String, Object> map = new HashMap<>();
        map.put("code", HttpServletResponse.SC_UNAUTHORIZED);
        map.put("msg", authException.getMessage());
        map.put("path", request.getServletPath());
        map.put("timestamp", System.currentTimeMillis());
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("HTTP Status 401 - " + authException.getMessage());
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new IOException();
        }
    }
}
