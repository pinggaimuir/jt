package com.jt.web.controller;

import com.jt.web.pojo.User;
//实现线程安全

public class UserThreadLocal {

    private static final ThreadLocal<User> USER = new ThreadLocal<User>();

    public static void set(User user) {
        USER.set(user);
    }

    public static User get() {
        return USER.get();
    }

	public static Long getUserId(){
		if(null!=USER){
			if(null!=USER.get()){
				return USER.get().getId();
			}
		}
		return null;
	}
}

