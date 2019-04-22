package com.cloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.cloud.consumer.domain.User;
import com.cloud.consumer.feign.UserFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Long id){
         return userFeignClient.getUser(id);
    }
    
    @GetMapping("/getName")
    @HystrixCommand(fallbackMethod = "getNameFallback")
    public String getName(){
    	 return restTemplate.getForObject("http://cloud-provider/user/getName",String.class);
    }
    
    public String getNameFallback() {
//        return restTemplate.getForObject("http://cloud-provider2/user/getName",String.class);
    	return "王3";
    }
}
