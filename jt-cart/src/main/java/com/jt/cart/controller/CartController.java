package com.jt.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	//我的购物车 	http://cart.jt.com/cart/query/{userId}
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult queryListByUserId(@PathVariable Long userId){
		List<Cart> cartList = cartService.queryListByUserId(userId);
		return SysResult.ok(cartList);
	}
	
	//新增商品到购物车	http://cart.jt.com/cart/save
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		int status = cartService.saveCart(cart);
		if(200==status){
			return SysResult.ok();
		}else{
			return SysResult.build(status, "此商品已经存在，数据已经更新!");
		}
	}
	
	//商品数量修改	http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCart(@PathVariable Long userId,@PathVariable Long itemId,@PathVariable Integer num){
		Cart _cart = new Cart();
		_cart.setNum(num);
		_cart.setUserId(userId);
		_cart.setItemId(itemId);
		
		cartService.updateCart(_cart);
		return SysResult.ok();
	}
	
	//商品删除	http://cart.jt.com/cart/delete/{userId}/{itemId}
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId,@PathVariable Long itemId){
		Cart param = new Cart();
		param.setUserId(userId);
		param.setItemId(itemId);
		
		cartService.deleteCart(param);
		return SysResult.ok();
	}
}
