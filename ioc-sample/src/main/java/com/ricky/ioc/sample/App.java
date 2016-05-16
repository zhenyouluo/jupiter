package com.ricky.ioc.sample;

import com.ricky.framework.ioc.ApplicationContext;
import com.ricky.framework.ioc.ClassPathXmlApplicationContext;
import com.ricky.ioc.sample.controller.UserController;
import com.ricky.ioc.sample.service.UserService;

/**
 * Hello world!
 *
 */
public class App {

	public static void main(String[] args) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");

		//通过id获取Bean
		UserController userController = (UserController) ctx.getBean("userController");
		userController.login("ricky", "123");

		//通过Class获取Bean
		UserService userService = ctx.getBean(UserService.class);
		System.out.println(userService);
		userService.login("ricky", "abc");
		
		ctx.close();
	}
}
