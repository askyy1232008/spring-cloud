package com.cloud.provider.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cloud.provider.dao.UserDao;
import com.cloud.provider.domain.User;
import com.cloud.provider.mapper.UserMapper1;

@Repository("userDao")
public class UserDaoImp implements UserDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public int addUser(User u) {
		 String sql = "insert into user(name,age) values(?,?)";
         Object[] args = { u.getName(), u.getAge() };
         int[] argTypes = { Types.VARCHAR, Types.INTEGER };
         return this.jdbcTemplate.update(sql, args, argTypes);
//		String sql = "insert into user(name,age) values(:name,:age)";
//		NamedParameterJdbcTemplate npjt = new NamedParameterJdbcTemplate(this.jdbcTemplate.getDataSource());
//		return npjt.update(sql, new BeanPropertySqlParameterSource(u));
	}

	@Override
	public int updateUser(User u) {
		String sql = "update user set name = ?,age = ? where id = ?";
        Object[] args = { u.getName(), u.getAge(), u.getId() };
        int[] argTypes = { Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
        return this.jdbcTemplate.update(sql, args, argTypes);
	}

	@Override
	public int deleteUserById(int id) {
		String sql = "delete from user where id = ?";
		Object[] args = { id };
		int[] argTypes = { Types.INTEGER };
		return this.jdbcTemplate.update(sql,args,argTypes);
	}

	@Override
	public List<Map<String, Object>> queryUsersListMap() {
		String sql = "select * from user";
		return this.jdbcTemplate.queryForList(sql);
	}

	@Override
	public User queryUserById(int id) {
		String sql = "select * from user where id =?";
		Object[] args = { id };
		int[] argTypes = { Types.INTEGER };
		List<User> userList = this.jdbcTemplate.query(sql,args,argTypes,new UserMapper1());
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}else {
			return null;
		}
	}
	
}
