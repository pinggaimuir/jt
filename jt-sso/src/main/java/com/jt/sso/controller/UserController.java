package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	//http://sso.jt.com/user/check/{param}/{type}
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public SysResult check(@PathVariable String param,@PathVariable Integer type){
		Boolean b = userService.check(type, param);
		return SysResult.ok(b);
	}
	
	//http://sso.jt.com/user/register
	@RequestMapping("/register")
	@ResponseBody
	public SysResult register(User user){
		User _user = userService.regiester(user);
		return SysResult.ok(_user.getUsername());
	}
	
	//http://sso.jt.com/user/login
	@RequestMapping("/login")
	@ResponseBody
	public SysResult login(@RequestParam(value="u") String username,@RequestParam(value="p") String password){
		String ticket = userService.login(username, password);
		return SysResult.ok(ticket);
	}
	
	//http://sso.jt.com/user/query/{ticket}
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public SysResult queryByTicket(@PathVariable String ticket){
		String userJson = userService.queryByTicket(ticket);
		return SysResult.ok(userJson);
	}
	
	
	
}
