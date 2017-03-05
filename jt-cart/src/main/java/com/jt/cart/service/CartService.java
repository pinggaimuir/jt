package com.jt.cart.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;

@Service
public class CartService extends BaseService<Cart>{
	@Autowired
	private CartMapper cartMapper;
	//我的购物车
	public List<Cart> queryListByUserId(Long userId){
		return cartMapper.queryListByUserId(userId);
	}
	
	//购物车商品保存，新增，修改
	public Integer saveCart(Cart cart){
		//如果此用户的此商品是否已经在购物车中
		Cart param = new Cart();
		param.setUserId(cart.getUserId());
		param.setItemId(cart.getItemId());
		
		Integer count = cartMapper.selectCount(param);
		if(0==count){	//代表此用户的此商品不存在，新增
			cart.setCreated(new Date());
			cart.setUpdated(cart.getCreated());
			
			cartMapper.insertSelective(cart);
			return 200;
		}else{			//代表此用户的此商品已经在购物车中，修改数量
			//先获取旧的数据+提交的商品的新的数量
			Cart oldCart = super.queryByWhere(param);
			oldCart.setNum(oldCart.getNum()+cart.getNum());
			oldCart.setUpdated(new Date());
			
			cartMapper.updateByPrimaryKeySelective(oldCart);
			return 202;	//已经存在此商品，修改商品的数量
		}
	}
	
	//购物车商品数量修改
	public void updateCart(Cart cart){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("num", cart.getNum());	//直接就是页面的数量
		param.put("userId", cart.getUserId());
		param.put("itemId", cart.getItemId());
		
		cartMapper.updateByUserIdItemId(param);
	}
	
	//购物车商品删除
	public void deleteCart(Cart cart){
		cartMapper.delete(cart);
	}
}
