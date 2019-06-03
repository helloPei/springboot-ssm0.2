package com.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.vo.JsonResult;

@RestController
@RequestMapping("/")
public class LoginController {
	/**
	 * Login登录
	 * @param username
	 * @param password
	 * @param isRememberMe
	 * @return
	 */
	@SuppressWarnings("finally")
	@RequestMapping("doLogin")
	public JsonResult doLogin(String username, String password, Integer isRememberMe){
		JsonResult jsonResult = new JsonResult();
		//用户名密码非空判断
		if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)) {
			if(StringUtils.isEmpty(username)) {
				jsonResult.setMessage("用户名不能为空");
			}
			if(StringUtils.isEmpty(password)) {
				jsonResult.setMessage("密码不能为空");
			}
			return jsonResult;
		}
		
		//1.根据用户名和密码获取一个令牌（token）
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		//1.1记住我
		if(isRememberMe == 1)token.setRememberMe(true);//可选用
		
		//2.通过SecurityUtils工具类，获取当前执行的用户用户
		Subject currentUser = SecurityUtils.getSubject();
		try {
			//3.进行认证
			currentUser.login(token);//此方法运行时可能会出现异常
			jsonResult.setMessage("success");
		} catch (UnknownAccountException e) {
			jsonResult.setMessage("此用户不存在");
		} catch (LockedAccountException e) {
			jsonResult.setMessage("此用户已被禁用");
		} catch (IncorrectCredentialsException e) {
			jsonResult.setMessage("密码错误");
		} catch (AuthenticationException e) {
			jsonResult.setMessage("用户名密码错误");
		} catch (RuntimeException e) {
			jsonResult.setMessage("未知错误，请联系管理员");
		} finally {
			return jsonResult;
		}
	}
}