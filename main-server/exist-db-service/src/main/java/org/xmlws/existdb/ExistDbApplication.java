package org.xmlws.existdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ExistDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExistDbApplication.class, args);
	}
}
