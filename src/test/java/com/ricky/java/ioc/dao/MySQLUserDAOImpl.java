package com.ricky.java.ioc.dao;

import com.ricky.java.ioc.anotation.MyBean;

@MyBean(id="mySQLUserDAOImpl")
public class MySQLUserDAOImpl extends UserDAO {

	@Override
	public void test() {
		System.out.println("MySQL impl");
	}

}
