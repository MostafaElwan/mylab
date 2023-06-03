package com.elwan.microservices.routerservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringBootApplication(exclude = ReactiveUserDetailsServiceAutoConfiguration.class)
@SpringBootApplication
@EnableDiscoveryClient
public class RouterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouterServiceApplication.class, args);
		
	}

}
