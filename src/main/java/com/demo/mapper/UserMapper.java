package com.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.demo.pojo.User;

/**
 * User持久层实现Mybatis框架中的Mapper接口，声名对数据库的操作方法
 * @author Administrator
 *
 */
@Mapper
public interface UserMapper {
	/**保存用户自身信息*/
	int insertObject(User user);
	/**基于用户名获取用户对象*/
	User findUserByUserName(String username);
}