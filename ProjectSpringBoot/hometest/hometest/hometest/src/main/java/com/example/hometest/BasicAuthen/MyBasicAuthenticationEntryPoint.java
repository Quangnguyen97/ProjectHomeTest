package com.example.hometest.BasicAuthen;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

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

        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("errorCode", HttpServletResponse.SC_UNAUTHORIZED);
        hashMap.put("errorDescription", authException.getMessage());
        hashMap.put("response", "null");

        JSONObject jsonObject = new JSONObject(hashMap);

        PrintWriter writer = response.getWriter();
        writer.println(jsonObject);
    }
}
