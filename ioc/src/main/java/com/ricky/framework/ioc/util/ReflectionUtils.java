package com.ricky.framework.ioc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author Ricky
 *
 */
public class ReflectionUtils {

	private ReflectionUtils(){
		
	}
	
	public static Class<?> loadClass(String className) throws ClassNotFoundException{
		
		return Class.forName(className);
	}
	
	public Class<?> loadClass(String name, boolean initialize, ClassLoader loader) throws ClassNotFoundException{
		
		return Class.forName(name, initialize, loader);
	}
	
	public static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		Class<?> clazz = Class.forName(className);
		
		return clazz.newInstance();
	}
	
	public static Object invokeMethod(Method method, Object target) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		return invokeMethod(method, target, new Object[0]);
	}

	public static Object invokeMethod(Method method, Object target, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		return method.invoke(target, args);
	}
	
	public static Object invokeMethod(Object target, String methodName, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException{
		
//		Method[] methods = target.getClass().getMethods();
		Method[] methods = target.getClass().getDeclaredMethods();
		if(methods==null || methods.length==0){
			throw new RuntimeException("sorry,this class method is empty, class:"+target.getClass());
		}
		
		for (Method method : methods) {
			if(method.getName().equals(methodName)){
				method.setAccessible(true);
				return method.invoke(target, args);
			}
		}
		
		throw new RuntimeException("sorry,no such method:"+methodName+" on class:"+target.getClass());
	}
	
}
