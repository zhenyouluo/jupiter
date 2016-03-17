package com.ricky.ioc.sample.dao;

import com.ricky.ioc.sample.model.User;

public class UserDaoImpl implements UserDao{

	@Override
	public User find(String name){
		
		System.out.println("UserDaoImpl find name->"+name);
		
		User user = new User();
		user.setName(name);
		user.setPassword("123");
		
		return user;
	}
	
	@Override
	public long insert(User user){
		
		return 0;
	}
	
	public void init(){
		System.out.println("UserDaoImpl init...");
	}
}
