package com.cloud.provider.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.cloud.provider.domain.User;

public interface UserMapper {
	
//    int deleteByPrimaryKey(Integer id);

//    int insert(User record);

//    int insertSelective(User record);
	
	@Select("Select id,name,age from user where id= #{id}")
	@Results({
		@Result(property = "id",column = "id",javaType = Integer.class),
		@Result(property = "name",column = "name",javaType = String.class),
		@Result(property = "age",column = "age",javaType = Integer.class)
	})
    User selectByPrimaryKey(@Param("id") Long id);

//    int updateByPrimaryKeySelective(User record);

//    int updateByPrimaryKey(User record);
}