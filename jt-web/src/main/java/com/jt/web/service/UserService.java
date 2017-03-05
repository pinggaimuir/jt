package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.User;

@Service
public class UserService {
	@Autowired
	private HttpClientService httpClientService;

	//注册
	public String doRegister(User user) throws Exception {
		String url = "http://sso.jt.com/user/register";
		//doPsot httpClient参数都是以map形式提交
		Map<String,String> params = new HashMap<String,String>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getPhone());	//页面现在没有提交email
		
		//ObjectMapper新方式，直接读取对象中的值SysResult.data
		String username = httpClientService.doPost(url, params, "utf-8");
		return username;
	}

	//登录
	public String doLogin(String u, String p) throws Exception{
		String url = "http://sso.jt.com/user/login";
		Map<String,String> params = new HashMap<String,String>();
		params.put("u", u);
		params.put("p", p);
		
		String ticket = httpClientService.doPost(url, params, "utf-8");
		return ticket;
	}
}
