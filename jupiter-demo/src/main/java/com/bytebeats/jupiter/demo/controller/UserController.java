package com.bytebeats.jupiter.demo.controller;

import com.bytebeats.jupiter.demo.service.UserService;

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
