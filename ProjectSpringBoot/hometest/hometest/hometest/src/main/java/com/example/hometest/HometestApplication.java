package com.example.hometest;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.hometest.Module" })
@ComponentScan(basePackages = { "com.example.hometest.Account" })
@ComponentScan(basePackages = { "com.example.hometest.User" })
@ComponentScan(basePackages = { "com.example.hometest.MapStruct" })
@ComponentScan(basePackages = { "com.example.hometest.Controller" })

public class HometestApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(HometestApplication.class, args);
	}
}
