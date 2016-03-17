package com.ricky.ioc.sample.controller;

import com.ricky.ioc.sample.service.UserService;

public class UserController {

	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void login(String name, String password){
		
		System.out.println("UserController login name->"+name+
				",password->"+password);
		
		userService.login(name, password);
	}
}
