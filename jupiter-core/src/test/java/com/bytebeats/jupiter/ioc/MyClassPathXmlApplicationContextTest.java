package com.bytebeats.jupiter.ioc;

/**
 * Unit test for simple App.
 */
public class MyClassPathXmlApplicationContextTest {
    
	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("beans.xml");
		classPathXmlApplicationContext.getBean("");
	}
}
