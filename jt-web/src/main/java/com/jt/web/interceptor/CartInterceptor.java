package com.jt.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.common.util.CookieUtils;
import com.jt.web.controller.UserController;
import com.jt.web.controller.UserThreadLocal;
import com.jt.web.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartInterceptor implements HandlerInterceptor{
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();

		//执行controller方法前执行
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 从cookie获取
		String ticket = CookieUtils.getCookieValue(request, UserController.cookieName);
		if(StringUtils.isNotEmpty(ticket)){
			//从redis获取数据
			String userJson = redisService.get(ticket);
			if(StringUtils.isNotEmpty(userJson)){
				//从redis还原User对象
				User curUser = MAPPER.readValue(userJson, User.class);
				//Controller就可以访问这个安全的共享变量
				UserThreadLocal.set(curUser);
			}else{
				UserThreadLocal.set(null);
			}
		}else{
			UserThreadLocal.set(null);
		}
		
		//如果没有登录，转向登录页面
		if(null == UserThreadLocal.getUserId()){
			response.sendRedirect("/user/login.html");
			return false;
		}
		return true;	//false不放行，true放行
	}

		//执行controller方法后执行
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

		//执行controller方法后执行，转向页面前（render渲染）
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
