package com.ricky.framework.ioc;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import com.ricky.framework.ioc.model.BeanDefinition;
import com.ricky.framework.ioc.parser.BeanXmlConfigParser;
import com.ricky.framework.ioc.util.BeanScope;
import com.ricky.framework.ioc.util.ReflectionUtils;

public class ClassPathXmlApplicationContext extends ApplicationContext {

	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();
	protected Map<String, Object> beanInstanceMap = new HashMap<String, Object>();
	
	public ClassPathXmlApplicationContext(String xmlFilePath) {
		
		System.out.println("****************container init begin****************");
		
		readXml(xmlFilePath);
		initBeans();
		injectBeans();
		
		System.out.println("****************container init end****************");
	}

	private void readXml(String xmlFilePath) {

		BeanXmlConfigParser beanXmlConfigParser = new BeanXmlConfigParser();

		List<BeanDefinition> bean_def_list = null;
		try {
			bean_def_list = beanXmlConfigParser.parse(xmlFilePath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("not found bean xml, file->"+xmlFilePath, e);
		} catch (DocumentException e) {
			throw new RuntimeException("bean xml format error, file->"+xmlFilePath, e);
		}

		for (BeanDefinition beanDefinition : bean_def_list) {
			
			if(StringUtils.isEmpty(beanDefinition.getId()) || StringUtils.isEmpty(beanDefinition.getClassName())){
				throw new IllegalArgumentException("bean definition is empty!");
			}
			
			if (beanDefinitionMap.containsKey(beanDefinition.getId())) {
				throw new IllegalArgumentException(
						"duplicated bean id , id->"
								+ beanDefinition.getId());
			}

			beanDefinitionMap.put(beanDefinition.getId(), beanDefinition);
		}
	}

	private void initBeans() {

		for (Map.Entry<String, BeanDefinition> me : beanDefinitionMap.entrySet()) {

			BeanDefinition bd = me.getValue();
			if(StringUtils.isEmpty(bd.getScope()) || bd.getScope().equals(BeanScope.SINGLETON)){
				try {
					Object bean = createBean(bd);
					beanInstanceMap.put(bd.getId(), bean);
				} catch (Exception e) {
					throw new IllegalArgumentException("create bean error,class->"+bd.getClassName(), e);
				}
			}
		}
	}

	private void injectBeans() {
		
		for (Map.Entry<String, BeanDefinition> me : beanDefinitionMap.entrySet()) {
			
			BeanDefinition beanDefinition = me.getValue();
            //判断有没有注入属性  
            if (beanDefinition.getProperties() != null && beanDefinition.getProperties().size()>0) {  
                
            	Object bean = beanInstanceMap.get(beanDefinition.getId());  
                try {
                	injectBeanProperties(bean, beanDefinition);
                } catch (Exception e) {
					e.printStackTrace();
				}
            }  
        }  
	}
	
	@Override
	public Object getBean(String id) {
		
//		System.out.println("get bean by id:"+id);
		
		if (StringUtils.isEmpty(id)) {
			return null;
		}

		if (beanDefinitionMap.containsKey(id)) {
			
			BeanDefinition bd = beanDefinitionMap.get(id);
			
			if(StringUtils.isEmpty(bd.getScope()) || bd.getScope().equals(BeanScope.SINGLETON)){
				
				return beanInstanceMap.get(id);
			}
			
			Object bean = null;
			try {
				bean = createBean(bd);
				injectBeanProperties(bean, bd);
				beanInstanceMap.put(bd.getId(), bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return bean;
		}
		throw new IllegalArgumentException("unknown bean, id->" + id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> clazz) {
		
//		System.out.println("get bean by type:"+clazz.getName());
		
		for(Map.Entry<String, BeanDefinition> me : beanDefinitionMap.entrySet()){
			
			BeanDefinition bd = me.getValue();
			Class<?> beanClass = null;
			try {
				beanClass = ReflectionUtils.loadClass(bd.getClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			if(beanClass!=null && clazz.isAssignableFrom(beanClass)){
//				System.out.println("find bean by type, class->"+clazz.getName());
				return (T) getBean(bd.getId());
			}
		}
		
		return null;
	}

	@Override
	protected BeanDefinition getBeanDefinition(String id) {
		
		return beanDefinitionMap.get(id);
	}

	@Override
	public void close() {

		System.out.println("container close...");
		
		// release resource
		beanDefinitionMap.clear();
		beanDefinitionMap = null;

		beanInstanceMap.clear();
		beanInstanceMap = null;
	}

}
