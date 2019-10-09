package com.cloud.provider.service;

import com.cloud.provider.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public User getUserById(long id);

    public int addUser(User u);

    public int updateUser(User u);

    public int deleteUserById(int id);

    public List<Map<String,Object>> queryUsersListMap();

    public User queryUserById(int id);
}
