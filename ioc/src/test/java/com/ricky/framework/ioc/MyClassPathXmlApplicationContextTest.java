package com.ricky.framework.ioc;

/**
 * Unit test for simple App.
 */
public class MyClassPathXmlApplicationContextTest {
    
	public static void main(String[] args) {
		MyClassPathXmlApplicationContext classPathXmlApplicationContext = new MyClassPathXmlApplicationContext("beans.xml");
		classPathXmlApplicationContext.getBean("");
	}
}
