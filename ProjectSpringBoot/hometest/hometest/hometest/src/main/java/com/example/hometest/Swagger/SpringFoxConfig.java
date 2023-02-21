package com.example.hometest.Swagger;

import com.example.hometest.Module.*;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebMvc
@EnableSwagger2
@Component
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        try {
            return new Docket(DocumentationType.SWAGGER_2)
                    .enable(true)
                    .apiInfo(this.apiInfo())
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.example.hometest.Controller"))
                    .paths(PathSelectors.any())
                    .build();
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    private ApiInfo apiInfo() {
        try {
            return (new ApiInfoBuilder()).title("Swagger Super").description("Swagger Description details")
                    .version("1.0")
                    .build();
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }
}
