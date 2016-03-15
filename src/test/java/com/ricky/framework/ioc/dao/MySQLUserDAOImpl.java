package com.ricky.framework.ioc.dao;

import com.ricky.framework.ioc.anotation.MyBean;

@MyBean(id="mySQLUserDAOImpl")
public class MySQLUserDAOImpl extends UserDAO {

	@Override
	public void test() {
		System.out.println("MySQL impl");
	}

}
