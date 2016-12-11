package com.bytebeats.jupiter.demo.dao;

import com.bytebeats.jupiter.demo.model.User;

public interface UserDao {
	public User find(String name);
	public long insert(User user);
}
