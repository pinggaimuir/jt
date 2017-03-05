package com.jt.web.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Item;

@Service
public class CartService {
	@Autowired
	private HttpClientService httpClientService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//获取我的购物车 http://cart.jt.com/cart/query/{userId}
	public List<Cart> getCartList(Long userId) throws Exception{
		//访问购物车子系统
		String url = "http://cart.jt.com/cart/query/"+userId;
		String jsonData = httpClientService.doGet(url, "utf-8");
		JsonNode jsonNode = MAPPER.readTree(jsonData);
		JsonNode dataNode = jsonNode.get("data");
		
		//jackson转成List<Object>方式
		List<Cart> cartList = MAPPER.readValue(dataNode.traverse(),
                MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
		return cartList;
	}
	
	//加入购物车
	public void add(Long userId, Long itemId, Integer num) throws Exception{
		//查询3个冗余字段，访问后台系统，查询商品信息
		String url = "http://manage.jt.com/web/query/item/"+itemId;
		String jsonData = httpClientService.doGet(url, "utf-8");
		Item item = MAPPER.readValue(jsonData, Item.class);
		
		url = "http://cart.jt.com/cart/save";
		Map<String,String> params  = new HashMap<String,String>();
		params.put("userId", userId+"");
		params.put("itemId", itemId+"");
		params.put("itemTitle", item.getTitle());
		try{
			params.put("itemImage", item.getImage().split(",")[0]);
		}catch(Exception ex){
			params.put("itemImage", null);
		}
		params.put("itemPrice", item.getPrice()+"");
		params.put("num", num+"");
		
		httpClientService.doPost(url, params, "utf-8");
	}
	
	//修改数量
	public void updateNum(Long userId, Long itemId, Integer num) throws Exception{
		String url = "http://cart.jt.com/cart/update/num/"+userId+"/"+itemId+"/"+num;
		httpClientService.doGet(url, "utf-8");
	}
	
	//删除商品
	public void delete(Long userId, Long itemId) throws Exception{
		String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
		httpClientService.doGet(url, "utf-8");
	}
}
