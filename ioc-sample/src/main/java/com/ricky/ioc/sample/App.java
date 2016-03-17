package com.ricky.ioc.sample;

import com.ricky.framework.ioc.ApplicationContext;
import com.ricky.framework.ioc.MyClassPathXmlApplicationContext;
import com.ricky.ioc.sample.controller.UserController;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new MyClassPathXmlApplicationContext(
				"beans.xml");

		UserController userController = (UserController) applicationContext
				.getBean("userController");
		userController.login("ricky", "123");

		applicationContext.close();
	}
}
