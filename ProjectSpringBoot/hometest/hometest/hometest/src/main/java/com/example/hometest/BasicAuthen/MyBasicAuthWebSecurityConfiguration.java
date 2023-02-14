package com.example.hometest.BasicAuthen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MyBasicAuthWebSecurityConfiguration {

    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
        try {
            authentication
                    .inMemoryAuthentication()
                    .withUser("admin")
                    .password("123456")
                    .authorities("ROLE_ADMIN");
        } catch (Exception e) {
            new Exception(e);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try {
            http.authorizeRequests()
                    .requestMatchers("/")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic()
                    .authenticationEntryPoint(authenticationEntryPoint);
            return http.build();
        } catch (Exception e) {
            new Exception(e);
            return http.build();
        }
    }
}
