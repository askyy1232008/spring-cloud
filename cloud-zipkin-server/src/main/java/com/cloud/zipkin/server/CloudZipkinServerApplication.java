package com.cloud.zipkin.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import zipkin2.server.internal.EnableZipkinServer;

@EnableEurekaClient
@EnableZipkinServer
@SpringBootApplication
public class CloudZipkinServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudZipkinServerApplication.class,args);
	}
}
