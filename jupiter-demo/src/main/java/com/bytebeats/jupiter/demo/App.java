package com.bytebeats.jupiter.demo;

import com.bytebeats.jupiter.demo.controller.UserController;
import com.bytebeats.jupiter.demo.service.UserService;
import com.ricky.framework.ioc.ApplicationContext;
import com.ricky.framework.ioc.ClassPathXmlApplicationContext;

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
