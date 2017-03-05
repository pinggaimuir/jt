package com.jt.sso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User>{
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisService redisService;
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	//按用户名、手机、邮件进行检查
	public Boolean check(Integer type, String param){
		Map<String,Object> map = new HashMap<String,Object>();
		String typename = "";
		if(1==type){
			typename = "username";
		}else if(2==type){
			typename = "phone";
		}else if(3==type){
			typename = "email";
		}
		map.put("typename", typename);
		map.put("val", param);

		int i = userMapper.check(map);	//获取数量
		if(i==0){
			return false;
		}else{
			return true;
		}
	}
	
	//用户注册
	public User regiester(User user){
		//密码加密
		user.setPassword(DigestUtils.md5Hex(user.getPassword()));
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		userMapper.insertSelective(user);
		return user;
	}
	
	//用户的登录
	public String login(String username, String password){
		User param = new User();
		param.setUsername(username);	//习惯只按用户名进行查询，密码进行比较
		
		List<User> userList = userMapper.select(param);
		if(userList!=null && userList.size()>0){
			User curUser = userList.get(0); //就只有一个
			//比较密码
			String dbPassword = curUser.getPassword();	//加密
			String newPassword = DigestUtils.md5Hex(password);	//md5加密
			if(dbPassword.equals(newPassword)){	//存在用户
				//构造ticket，写入redis，必须动态，唯一标识，混淆
				String ticket = DigestUtils.md5Hex("JT_TICKET_"+System.currentTimeMillis()+username);	//代表当前用户
				//没有session，将user对象转成json串，然后放入到redis中，业务要判断是否登录看redis中是否有这ticket
				try{
					redisService.set(ticket, MAPPER.writeValueAsString(curUser), 60*60*24*10);	//10天
					return ticket;
				}catch(Exception ex){
					//写日志
				}
			}			
		}
		return null;	//前台系统就按此值如果是null就转向登录页面。
	}

	//按ticket在redis中查询user信息
	public String queryByTicket(String ticket) {
		try{
			return redisService.get(ticket);
		}catch(Exception ex){
			return null;
		}
	}
}
