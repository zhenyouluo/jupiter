package com.ricky.ioc.sample.service;

import com.ricky.ioc.sample.model.User;

public interface UserService {

	public boolean register(User user);
	
	public boolean login(String name, String password);
}
