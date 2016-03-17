package com.ricky.ioc.sample.dao;

import com.ricky.ioc.sample.model.User;

public interface UserDao {
	public User find(String name);
	public long insert(User user);
}
