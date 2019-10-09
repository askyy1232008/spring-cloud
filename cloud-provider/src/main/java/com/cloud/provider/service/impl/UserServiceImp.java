package com.cloud.provider.service.impl;

import com.cloud.provider.dao.UserDao;
import com.cloud.provider.domain.User;
import com.cloud.provider.mapper.UserMapper;
import com.cloud.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("UserService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    @CachePut(value = "user")
    public int addUser(User u) {
        return this.userDao.addUser(u);
    }

    @Override
    @CachePut(value = "user")
    public int updateUser(User u) {
        return this.userDao.updateUser(u);
    }

    @Override
    @CacheEvict(value = "user")
    public int deleteUserById(int id) {
        return this.userDao.deleteUserById(id);
    }

    @Override
    @Cacheable(value = "user")
    public List<Map<String, Object>> queryUsersListMap() {
        List<Map<String, Object>> users = this.userDao.queryUsersListMap();
        return users;
    }

    @Override
    @Cacheable(value = "user")
    public User queryUserById(int id) {
        User user = this.userDao.queryUserById(id);
        return user;
    }
}
