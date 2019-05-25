package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.demo.common.vo.JsonResult;
import com.demo.pojo.User;
import com.demo.service.UserService;
/**
 * User控制层
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user/")
public class UserController {
	@Autowired
	private UserService userService;
	/**用户管理页面*/
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	/**用户编辑页面*/
	@RequestMapping("doUserEditUI")
	public String doUserEditUI() {
		return "sys/user_edit";
	}
    /**
     * Add添加用户
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
	public JsonResult doSaveObject(User user, Integer[] roleIds){
		userService.saveObject(user, roleIds);
		return new JsonResult("save ok", 1);
	}
}