package com.ricky.framework.ioc;

public class ClasspathFileTest {
	
	public static void main(String[] args) {
		
		String filename = "beans.xml";
		
		ClasspathFileTest.class.getResourceAsStream(filename);
	}

}
