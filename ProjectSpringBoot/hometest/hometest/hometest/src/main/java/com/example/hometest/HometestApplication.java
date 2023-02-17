package com.example.hometest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.hometest.Module.*;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.hometest.BasicAuthen" })
@ComponentScan(basePackages = { "com.example.hometest.Module" })
@ComponentScan(basePackages = { "com.example.hometest.Account" })
@ComponentScan(basePackages = { "com.example.hometest.User" })
@ComponentScan(basePackages = { "com.example.hometest.Notification" })
@ComponentScan(basePackages = { "com.example.hometest.MapStruct" })
@ComponentScan(basePackages = { "com.example.hometest.Response" })
@ComponentScan(basePackages = { "com.example.hometest.Controller" })

public class HometestApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080").allowedMethods("*");
			}
		};
	}

	public static void main(String[] args) {
		try {
			SpringApplication.run(HometestApplication.class, args);
		} catch (Exception e) {
			throw new ResourceException(e.getMessage());
		}
	}

}
