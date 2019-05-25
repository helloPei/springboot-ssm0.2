package com.demo.service;

import com.demo.pojo.User;
/**
 * User业务层接口
 * @author Administrator
 *
 */
public interface UserService {
	/** 将数据保存到数据库(用户信息,用户角色关系信息) */
	int saveObject(User user, Integer[] roleIds);
}