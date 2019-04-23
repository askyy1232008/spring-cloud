package com.cloud.provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.provider.domain.User;
import com.cloud.provider.mapper.UserMapper;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
    private UserMapper userMapper;
	
    @GetMapping(value = "/getUser/{id}")
    public User getUser(@PathVariable Long id){
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
    @GetMapping(value = "/getName")
    public String getName(){
        return "张三";
    }
}
