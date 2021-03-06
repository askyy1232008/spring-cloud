package com.cloud.provider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@MapperScan("com.cloud.provider.mapper")
@SpringBootApplication
@EnableEurekaClient
@EnableCaching//启用Rediscahce注解
public class CloudProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudProviderApplication.class, args);
	}
}
