package com.jt.web.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.spring.exetend.PropertyConfig;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;

@Service
public class ItemService {
	@PropertyConfig
	private String MANAGE_URL;	//spring
	
	@Autowired
	private HttpClientService httpClientService;
	private Logger log = Logger.getLogger(ItemService.class);
	//jackson转换支持
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	public Item getItemById(Long id){
		//模拟发出http请求	// http://manage.jt.com/web/query/item/{id}
		String url = MANAGE_URL+"/web/query/item/"+id;
		try{
			//超时，会报错，（断点）
			String jsonData = httpClientService.doGet(url, "utf-8");
			//将json串转成java对象
			Item item = MAPPER.readValue(jsonData, Item.class);	//把json转成Item
			return item;
		}catch(Exception ex){
			//写日志
			log.error(ex.getMessage());
		}
		return null;
	}
	
	public ItemDesc getItemDescById(Long id){
		String url = MANAGE_URL+"/web/query/itemdesc/"+id;
		try{
			String jsonData = httpClientService.doGet(url, "utf-8");
			ItemDesc itemDesc = MAPPER.readValue(jsonData, ItemDesc.class);
			return itemDesc;
		}catch(Exception ex){
			log.error(ex.getMessage());
		}
		return null;
	}
}
