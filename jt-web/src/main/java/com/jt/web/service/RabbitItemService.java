package com.jt.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.service.RedisService;

@Service
public class RabbitItemService {
	@Autowired
	private RedisService redisService;
	
	public void updateItem(String redisKey){
		//redis删除
		redisService.del(redisKey);
	}
}
