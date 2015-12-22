package com.ricky.java.ioc;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	
	public void testIoc(){

		MyApplicationContext applicationContext = MyApplicationContext.create("com.ricky");
		UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
		
		if(userService!=null){
			userService.fire();
		}
		
	}
}
