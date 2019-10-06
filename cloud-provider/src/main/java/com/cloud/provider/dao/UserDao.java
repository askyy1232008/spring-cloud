package com.cloud.provider.dao;

import java.util.List;
import java.util.Map;

import com.cloud.provider.domain.User;

public interface UserDao {

	public int addUser(User u);
	
	public int updateUser(User u);
	
	public int deleteUserById(int id);
	
	public List<Map<String,Object>> queryUsersListMap();
	
	public User queryUserById(int id);
}
