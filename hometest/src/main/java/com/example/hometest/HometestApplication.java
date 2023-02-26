package com.example.hometest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

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
@ComponentScan(basePackages = { "com.example.hometest.Swagger" })
public class HometestApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		try {
			SpringApplication.run(HometestApplication.class, args);
		} catch (Exception e) {
			throw new ResourceException(e.getMessage());
		}
	}
}
