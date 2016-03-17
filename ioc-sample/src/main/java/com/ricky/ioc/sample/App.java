package com.ricky.ioc.sample;

import com.ricky.framework.ioc.ApplicationContext;
import com.ricky.framework.ioc.MyClassPathXmlApplicationContext;
import com.ricky.ioc.sample.controller.UserController;
import com.ricky.ioc.sample.service.UserService;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		ApplicationContext ctx = new MyClassPathXmlApplicationContext(
				"beans.xml");

		UserController userController = (UserController) ctx
				.getBean("userController");
		userController.login("ricky", "123");

		UserService userService = ctx.getBean(UserService.class);
		System.out.println(userService);
		
		ctx.close();
	}
}
