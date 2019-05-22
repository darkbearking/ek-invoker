package com.troila.lw;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class InvokerApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InvokerApp.class).web(true).run(args);
	}
}
