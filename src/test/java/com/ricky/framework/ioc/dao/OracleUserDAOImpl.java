package com.ricky.framework.ioc.dao;

import com.ricky.framework.ioc.anotation.MyBean;

@MyBean(id="oracleUserDAOImpl")
public class OracleUserDAOImpl extends UserDAO {

	@Override
	public void test() {
		System.out.println("Oracle impl");
	}

}
