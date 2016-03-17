package com.ricky.framework.ioc;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.ricky.framework.ioc.model.BeanDefinition;
import com.ricky.framework.ioc.model.PropertyDefinition;

public abstract class ApplicationContext {

	public abstract Object getBean(String id);
	
	public abstract void close();
	
	/**create bean instance*/
	protected Object createBean(BeanDefinition bd) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		
		Class<?> clazz = Class.forName(bd.getClassName());
		Object bean = clazz.newInstance();
		if(StringUtils.isNotEmpty(bd.getInitMethodName())){
			try {
				Method method = clazz.getDeclaredMethod(bd.getInitMethodName());
				if(method!=null){
					method.setAccessible(true);
					method.invoke(bean);
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
	
	protected boolean injectBeanDependency(Object bean, BeanDefinition beanDefinition) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        
		boolean flag = false;
		//得到被注入bean的所有的属性  
        PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();  
        //得到所有的注入bean属性  
        for(PropertyDefinition propertyDefinition : beanDefinition.getProperties()){  
            
        	for(PropertyDescriptor propertyDescriptor:ps){
        		
                if(propertyDescriptor.getName().equals(propertyDefinition.getName())){  
                    
                	Method setter = propertyDescriptor.getWriteMethod();//获取set方法  
                	setter.setAccessible(true);//得到private权限  
                    //注入属性  
                    setter.invoke(bean, getBean(propertyDefinition.getRef()));  
                    flag = true;
                    break;
                }
            }
        }
        
        return flag;
	}
}
