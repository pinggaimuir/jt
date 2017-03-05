package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	//转向首页 views/index.jsp
	/*
	 * 浏览器			http://www.jt.com/index.html
	 * 映射(*.html)	/index.html 映射后 /index
	 */
	@RequestMapping("/index")	//全局唯一
	public String index(){
		return "index";
	}
}

