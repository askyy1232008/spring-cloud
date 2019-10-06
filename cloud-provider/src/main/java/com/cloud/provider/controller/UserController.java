package com.cloud.provider.controller;

import java.util.List;
import java.util.Map;

import com.cloud.provider.log.MySystemLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.provider.dao.UserDao;
import com.cloud.provider.domain.User;
import com.cloud.provider.mapper.UserMapper;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;

    @MySystemLog.Log("查询用户信息")
    @GetMapping(value = "/getUser/{id}")
    public User getUser(@PathVariable Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @GetMapping(value = "/getName")
    public String getName() {
        return "张三";
    }

    @GetMapping(value = "/addUser")
    public int addUser() {
        User u = new User();
        u.setName("test11");
        u.setAge(12);
        return this.userDao.addUser(u);
    }

    @GetMapping(value = "/queryUsersListMap")
    public List<Map<String, Object>> queryUsersListMap() {
        List<Map<String, Object>> users = this.userDao.queryUsersListMap();
        return users;
    }

    @GetMapping(value = "/queryUserById/{id}")
    public User queryUserById(@PathVariable int id) {
        User user = this.userDao.queryUserById(id);
        return user;
    }

    @GetMapping(value = "/deleteUserById/{id}")
    public int deleteUserById(@PathVariable int id) {
        return this.userDao.deleteUserById(id);
    }

    @GetMapping(value = "/updateUser")
    public int updateUser() {
        User u = new User();
        u.setId(3);
        u.setName("Text");
        u.setAge(20);
        return this.userDao.updateUser(u);
    }
}
