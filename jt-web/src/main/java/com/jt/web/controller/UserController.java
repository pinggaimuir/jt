package com.jt.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	public static final String cookieName = "JT_TICKET";
	private static final ObjectMapper MAPPER = new ObjectMapper();

	//转向注册页面
	@RequestMapping("/register")
	public String register(){
		return "register";
	}
	
	//注册	/service/user/doRegister
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) throws Exception{
		String jsonData = userService.doRegister(user);
		//SysResult不能直接转换，本身对象构造有问题。
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		//获取对象的data属性内容
		String username = jsonNode.get("data").asText();
		return SysResult.ok(username);
	}
	
	//转向登录页面
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	//登录	/service/user/doLogin
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String jsonData = userService.doLogin(username, password);
		//写cookie，必须在前台系统中写入，因为其他访问时按域名来获取cookie
		
		//利用jackson，只获取SysReuslt.data属性
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		JsonNode dataJsonNode = jsonNode.get("data");
		String ticket = dataJsonNode.textValue();	//转成字符串
		
		CookieUtils.setCookie(request, response, cookieName, ticket);
		
		return SysResult.ok();
	}
	
	//登出	http://www.jt.com/user/logout.html
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response){
		//清除cookie
		CookieUtils.deleteCookie(request, response, cookieName);
		
		return "index";
	}
}
