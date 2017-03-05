package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;

	//访问我的购物车	http://www.jt.com/cart/show.html
	@RequestMapping("/show")
	public String show(Model model) throws Exception{
		//Long userId = 1L;
		Long userId = UserThreadLocal.getUserId();
		List<Cart> cartList = cartService.getCartList(userId);
		model.addAttribute("cartList", cartList);
		return "cart";
	}
	
	//加入购物车	http://www.jt.com/cart/add/${item.id}.html
	@RequestMapping("/add/{itemId}")	//springmvc接收页面的值是按name属性接收，让name和id属性一致
	public String add(@PathVariable Long itemId, Integer num) throws Exception{
		//Long userId = 1L;
		Long userId = UserThreadLocal.getUserId();
		cartService.add(userId, itemId, num);
		
		return "redirect:/cart/show.html";	//直接转向我的购物车
	}
	
	//修改商品数量	service/cart/update/num/741524/2
	@RequestMapping("/update/num/{itemId}/{num}")
	public String updateNum(@PathVariable Long itemId,@PathVariable Integer num) throws Exception{
		//Long userId = 1L;
		Long userId = UserThreadLocal.getUserId();
		cartService.updateNum(userId, itemId, num);

		return "redirect:/cart/show.html";
	}
	
	//删除商品	/cart/delete/741524.html
	@RequestMapping("/delete/{itemId}")
	public String delete(@PathVariable Long itemId) throws Exception{
		//Long userId = 1L;
		Long userId = UserThreadLocal.getUserId();
		cartService.delete(userId, itemId);
		
		return "redirect:/cart/show.html";
	}
}
