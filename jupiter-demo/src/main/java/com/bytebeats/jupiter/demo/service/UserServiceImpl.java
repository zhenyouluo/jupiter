package com.bytebeats.jupiter.demo.service;

import com.bytebeats.jupiter.demo.dao.UserDao;
import com.bytebeats.jupiter.demo.model.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean register(User user) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean login(String name, String password){
		
		System.out.println("UserServiceImpl login name->"+name+
				",password->"+password);
		
		User user = userDao.find(name);
		if(user!=null && user.getPassword().equals(password)){
			return true;
		}
		
		return false;
	}

}
