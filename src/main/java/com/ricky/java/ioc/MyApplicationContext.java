package com.ricky.java.ioc;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ricky.java.ioc.anotation.MyBean;
import com.ricky.java.ioc.anotation.MyResource;
import com.ricky.java.ioc.processor.AnnotationDetector;

public class MyApplicationContext {

	private static MyApplicationContext myApplication;
	
	private Map<String,Object> globalBeanMap = new HashMap<String, Object>();
	
	private MyApplicationContext(String... scanPackageNames){
		bind(scanPackageNames);
	}

	public static MyApplicationContext getInstance(){
		
		return myApplication;
	}
	
	public static MyApplicationContext create(String... scanPackageNames){
		
		if(myApplication==null){
			synchronized (MyApplicationContext.class) {
				if(myApplication==null){
					myApplication = new MyApplicationContext(scanPackageNames);
				}
			}
		}
		
		return myApplication;
	}

	public Object getBean(String beanId) {
		
        return globalBeanMap.get(beanId);  
    }
	
	private void bind(String... scanPackageNames) {
		
		initializeBean();
		
		inject();
	}
	
	/**
	 * 实例化Bean
	 */
	private void initializeBean(String... scanPackageNames) {
		
		AnnotationDetector annotationScanner = AnnotationDetector.custom()
				.scanPackageNames(scanPackageNames)
				.scanAnnotation(MyBean.class)
				.target(AnnotationDetector.TYPE)
				.build();
		try {
			List<Class<?>> list = annotationScanner.detect();
			
			for (Class<?> clazz : list) {
				
				MyBean beanAnnotation = clazz.getAnnotation(MyBean.class);
				
				System.out.println("class="+clazz.getName()+",bean_id="+beanAnnotation.id());
				
				Object bean = clazz.newInstance();	//如需通过构造函数注入,需要在此处理
				
				globalBeanMap.put(beanAnnotation.id(), bean);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	private void inject(){
		
		for(String beanName : globalBeanMap.keySet()){  
            Object bean = globalBeanMap.get(beanName);  
            if(bean!=null){  
            	processSetterAnnotation(bean);
        		processFieldAnnotation(bean);
            }
        }
		
	}
		
	/**
     * 处理field上的注解
     * @param bean
     */  
	protected void processFieldAnnotation(Object bean){
    	
    	try {  
            Field[] fields = bean.getClass().getDeclaredFields();
            for(Field field : fields){  
                if(field!=null && field.isAnnotationPresent(MyResource.class)){  
                	MyResource resource = field.getAnnotation(MyResource.class);  
                    String name = resource.name();  
                    Object injectBean = null;  
                    if(name!=null&&!"".equals(name)){  
                    	injectBean = globalBeanMap.get(name);  
                    }else{  
                        for(String key : globalBeanMap.keySet()){  
                            //判断当前属性所属的类型是否在配置文件中存在  
                            if(field.getType().isAssignableFrom(globalBeanMap.get(key).getClass())){  
                                //获取类型匹配的实例对象  
                            	injectBean = globalBeanMap.get(key);  
                                break;
                            }
                        }
                    }
                    
                    if(injectBean!=null){
                    	//允许访问private字段  
                        field.setAccessible(true);  
                        //把引用对象注入属性  
                        field.set(bean, injectBean);  
                    }else{
                    	System.out.println("field inject failed,name="+name);
                    }
                    
                }
            }
        } catch (Exception e) {  
            e.printStackTrace();
        }  
    }
	
	/**
     * 处理set方法上的注解
     * @param bean
     */  
	protected void processSetterAnnotation(Object bean){ 
    	
    	try {
            //获取bean的属性描述器
            PropertyDescriptor[] ps =   
                Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();  
            for(PropertyDescriptor pd : ps){  
               
                Method setter = pd.getWriteMethod();  
                
                if(setter!=null && setter.isAnnotationPresent(MyResource.class)){  
                    //获取当前注解，并判断name属性是否为空  
                	MyResource resource = setter.getAnnotation(MyResource.class);  
                    String name = resource.name();
                    Object injectBean = null;
                    if(name!=null&&!"".equals(name)){
                    	injectBean = globalBeanMap.get(name);
                    }else{ //如果当前注解没有指定name属性,则根据类型进行匹配  
                        for(String key : globalBeanMap.keySet()){
                            if(pd.getPropertyType().isAssignableFrom(globalBeanMap.get(key).getClass())){  
                            	injectBean = globalBeanMap.get(key); 
                                break;
                            }
                        }
                    }
                    
                    if(injectBean!=null){
                        //允许访问private方法  
                        setter.setAccessible(true);
                        //把引用对象注入属性
                        setter.invoke(bean, injectBean);
                    }else{
                    	System.out.println("setter inject failed,name="+name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }  
    }
}
