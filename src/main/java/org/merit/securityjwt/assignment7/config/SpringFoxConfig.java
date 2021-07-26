package org.merit.securityjwt.assignment7.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/*To configure Swagger*/

@Configuration
public class SpringFoxConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("org.merit.securityjwt")) //.apis(RequestHandlerSelectors.any())               
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiDetails());    
    }
    
    private ApiInfo apiDetails() {
    	return new ApiInfo(
    			"MeritAmerica Bank API",
    			"REST API Design Documentation",
    			"1.0",
    			"Free to Use",
    			new springfox.documentation.service.Contact("Irina Babkina", "http://localhost:8080/swagger-ui.html", "ibabkina700@gmail.com"),
    			"API Licence",
    			"http://meritamericabank.com",
    			Collections.emptyList());			
    }
}