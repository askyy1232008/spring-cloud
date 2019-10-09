package com.cloud.provider.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.cloud.provider.log.MySystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.provider.service.UserService;
import com.cloud.provider.domain.User;
import com.cloud.provider.mapper.UserMapper;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUser/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user;
    }

    @GetMapping(value = "/getName")
    public String getName() {
        return "张三";
    }

    @MySystemLog.Log("添加用户信息")
    @GetMapping(value = "/addUser")
    public int addUser() {
        User u = new User();
        u.setName("test");
        u.setAge(16);
        return this.userService.addUser(u);
    }

    @GetMapping(value = "/queryUsersListMap")
    public List<Map<String, Object>> queryUsersListMap() {
        List<Map<String, Object>> users = this.userService.queryUsersListMap();
        return users;
    }

    @GetMapping(value = "/queryUserById/{id}")
    public User queryUserById(@PathVariable int id) {
        User user = this.userService.queryUserById(id);
        return user;
    }

    @MySystemLog.Log("根据ID删除用户信息")
    @GetMapping(value = "/deleteUserById/{id}")
    public int deleteUserById(@PathVariable int id) {
        return this.userService.deleteUserById(id);
    }

    @MySystemLog.Log("根据ID修改用户信息")
    @GetMapping(value = "/updateUser")
    public int updateUser() {
        User u = new User();
        u.setId(3);
        u.setName("Text");
        u.setAge(20);
        return this.userService.updateUser(u);
    }
}
