package com.ricky.framework.ioc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.ricky.framework.ioc.model.BeanDefinition;
import com.ricky.framework.ioc.model.PropertyDefinition;
import com.ricky.framework.ioc.util.ReflectionUtils;

public abstract class ApplicationContext {

	public abstract Object getBean(String id);
	
	public abstract <T> T getBean(Class<T> clazz);
	
	public abstract void close();
	
	protected abstract BeanDefinition getBeanDefinition(String id);
	
	protected Object createBean(BeanDefinition bd) {
		
		try {
			Object bean = ReflectionUtils.newInstance(bd.getClassName());
			if(StringUtils.isNotEmpty(bd.getInitMethodName())){
				ReflectionUtils.invokeMethod(bean, bd.getInitMethodName());
			}
			
			return bean;
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException("create bean error, class->"+bd.getClassName(), e);
		}
	}
	
	protected void injectBeanProperties(Object bean, BeanDefinition beanDefinition){
        
		try {
			PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();  
			
			for(PropertyDefinition propertyDefinition : beanDefinition.getProperties()){  
			    
				for(PropertyDescriptor propertyDescriptor:ps){
					
			        if(propertyDescriptor.getName().equals(propertyDefinition.getName())){  
			            
			        	Method setter = propertyDescriptor.getWriteMethod(); 
			        	setter.setAccessible(true); 
			            
			            setter.invoke(bean, getBean(propertyDefinition.getRef()));  
			        }
			    }
			}
		} catch (SecurityException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| IntrospectionException e) {
			throw new RuntimeException("inject bean properties error", e);
		}
	}
}
