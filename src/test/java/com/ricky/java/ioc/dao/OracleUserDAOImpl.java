package com.ricky.java.ioc.dao;

import com.ricky.java.ioc.anotation.MyBean;

@MyBean(id="oracleUserDAOImpl")
public class OracleUserDAOImpl extends UserDAO {

	@Override
	public void test() {
		System.out.println("Oracle impl");
	}

}
