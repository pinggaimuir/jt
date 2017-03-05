package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//http://www.jt.com/item/3141248.html
	@RequestMapping("/item/{id}")
	public String queryItemById(@PathVariable Long id, Model model){
		Item item = itemService.getItemById(id);
		model.addAttribute("item", item);	//页面就可以jstl解析
		
		ItemDesc itemDesc = itemService.getItemDescById(id);
		model.addAttribute("itemDesc", itemDesc);
		
		return "item";	//转向item.jsp
	}
}
