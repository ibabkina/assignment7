package org.merit.securityjwt.assignment7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MeritAmericaBankApp {

	public static void main(String[] args) {
		SpringApplication.run(MeritAmericaBankApp.class, args);
	}

}
