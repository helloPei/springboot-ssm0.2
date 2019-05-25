package com.demo.service.impl;

import java.util.Date;
import java.util.UUID;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.common.exception.ServiceException;
import com.demo.mapper.UserMapper;
import com.demo.pojo.User;
import com.demo.service.UserService;
/**
 * User接口实现类
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public int saveObject(User user, Integer[] roleIds) {
		//1.验证数据合法性
		if(user == null)
			throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(user.getUsername()))
			throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(user.getPassword()))
			throw new IllegalArgumentException("密码不能为空");
		if(user.getDeptId() == null)
			throw new IllegalArgumentException("必须为用户指定部门");
		if(roleIds == null || roleIds.length == 0)
			throw new ServiceException("至少要为用户分配角色");
		//2.对密码进行加密
		//2.1获取一个盐值(salt):借助UUID获得一个随机数
		String salt = UUID.randomUUID().toString();
		//2.2对密码进行加密(借助Shiro框架API):MD5加密(消息摘要)
		SimpleHash sHash = new SimpleHash(
				"MD5",//algorithmName(算法名称)
				user.getPassword(),//Source(原密码)
				salt);//salt盐值
		String hashPassword = sHash.toHex();//将加密结果转换
		user.setSalt(salt);
		user.setPassword(hashPassword);
		//2.4设置对象其它属性默认值
		user.setCreatedTime(new Date());
		//2.5保存用户自身信息
		int rows = userMapper.insertObject(user);
		return rows;
	}
}