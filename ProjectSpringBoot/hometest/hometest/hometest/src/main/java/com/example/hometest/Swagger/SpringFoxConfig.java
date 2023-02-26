package com.example.hometest.Swagger;

import com.example.hometest.Module.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.actuate.autoconfigure.endpoint.web.CorsEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementPortType;
import org.springframework.boot.actuate.endpoint.ExposableEndpoint;
import org.springframework.boot.actuate.endpoint.web.EndpointLinksResolver;
import org.springframework.boot.actuate.endpoint.web.EndpointMapping;
import org.springframework.boot.actuate.endpoint.web.EndpointMediaTypes;
import org.springframework.boot.actuate.endpoint.web.WebEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ControllerEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.annotation.ServletEndpointsSupplier;
import org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@Component
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        try {
            return new Docket(DocumentationType.SWAGGER_12)
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        try {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080")
                            .allowedMethods("");
                }
            };
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    @Bean
    public WebMvcEndpointHandlerMapping webEndpointServletHandlerMapping(WebEndpointsSupplier webEndpointsSupplier,
            ServletEndpointsSupplier servletEndpointsSupplier,
            ControllerEndpointsSupplier controllerEndpointsSupplier,
            EndpointMediaTypes endpointMediaTypes, CorsEndpointProperties corsProperties,
            WebEndpointProperties webEndpointProperties, Environment environment) {
        try {
            List<ExposableEndpoint<?>> allEndpoints = new ArrayList<ExposableEndpoint<?>>();
            allEndpoints.addAll(webEndpointsSupplier.getEndpoints());
            allEndpoints.addAll(servletEndpointsSupplier.getEndpoints());
            allEndpoints.addAll(controllerEndpointsSupplier.getEndpoints());
            return new WebMvcEndpointHandlerMapping(new EndpointMapping(webEndpointProperties.getBasePath()),
                    webEndpointsSupplier.getEndpoints(),
                    endpointMediaTypes,
                    corsProperties.toCorsConfiguration(), new EndpointLinksResolver(allEndpoints,
                            webEndpointProperties.getBasePath()),
                    this.shouldRegisterLinksMapping(webEndpointProperties, environment,
                            webEndpointProperties.getBasePath()));
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }
    }

    private boolean shouldRegisterLinksMapping(WebEndpointProperties webEndpointProperties, Environment environment,
            String basePath) {
        try {
            return webEndpointProperties.getDiscovery().isEnabled() &&
                    (StringUtils.hasText(basePath)
                            || ManagementPortType.get(environment).equals(ManagementPortType.DIFFERENT));
        } catch (Exception e) {
            throw new ResourceException(e.getMessage());
        }

    }

}
