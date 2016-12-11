package com.bytebeats.jupiter.demo.service;

import com.bytebeats.jupiter.demo.model.User;

public interface UserService {

	public boolean register(User user);
	
	public boolean login(String name, String password);
}
