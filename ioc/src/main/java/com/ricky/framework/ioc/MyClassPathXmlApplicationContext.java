package com.ricky.framework.ioc;

import java.beans.IntrospectionException;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import com.ricky.framework.ioc.model.BeanDefinition;
import com.ricky.framework.ioc.util.Constants;
import com.ricky.framework.ioc.xml.BeanXmlConfigParser;

public class MyClassPathXmlApplicationContext extends ApplicationContext {

	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<String, BeanDefinition>();
	protected Map<String, Object> beanInstanceMap = new HashMap<String, Object>();
	
	public MyClassPathXmlApplicationContext(String xmlFilePath) {
		readXml(xmlFilePath);
		initBeans();
		injectBeans();
	}

	private void readXml(String xmlFilePath) {

		BeanXmlConfigParser beanXmlConfigParser = new BeanXmlConfigParser();

		List<BeanDefinition> bean_def_list = null;
		try {
			bean_def_list = beanXmlConfigParser.parse(xmlFilePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
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
			try {
				Object bean = createBean(bd);
				beanInstanceMap.put(bd.getId(), bean);
			} catch (InstantiationException e) {
				throw new IllegalArgumentException("bean class init error,class->"+bd.getClassName(), e);
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException("bean class no access permission,class->"+bd.getClassName(), e);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("bean class is not found,class->"+bd.getClassName(), e);
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
                    injectBeanDependency(bean, beanDefinition);
                } catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IntrospectionException e) {
					e.printStackTrace();
				}
            }  
        }  
	}
	
	@Override
	public Object getBean(String id) {

		if (id == null || "".equals(id)) {
			return null;
		}

		if (beanDefinitionMap.containsKey(id)) {
			
			BeanDefinition bd = beanDefinitionMap.get(id);
			String scope = bd.getScope();
			
			Object bean = beanInstanceMap.get(id);
			
			if(Constants.BEAN_SCOPE_PROTOTYPE.equals(scope)){
				try {
					bean = createBean(bd);
					injectBeanDependency(bean, bd);
					beanInstanceMap.put(bd.getId(), bean);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IntrospectionException e) {
					e.printStackTrace();
				}
			}
			
			return bean;
		}
		throw new IllegalArgumentException("unknown bean, id->" + id);
	}

	@Override
	public void close() {

		// release resource
		beanDefinitionMap.clear();
		beanDefinitionMap = null;

		beanInstanceMap.clear();
		beanInstanceMap = null;
	}

}
