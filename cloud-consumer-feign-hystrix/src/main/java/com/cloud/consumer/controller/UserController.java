package com.cloud.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.consumer.domain.User;
import com.cloud.consumer.feign.UserFeignClient;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Long id){
         return userFeignClient.getUser(id);
    }
}
