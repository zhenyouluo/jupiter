package com.bytebeats.jupiter.ioc.context;

import com.bytebeats.jupiter.ioc.beans.BeanException;
import com.bytebeats.jupiter.ioc.beans.factory.AbstractBeanFactory;
import com.bytebeats.jupiter.ioc.beans.factory.XmlBeanFactory;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    public ClassPathXmlApplicationContext(String location){
        super(new XmlBeanFactory());
    }

    @Override
    protected void loadBeanDefinitions(AbstractBeanFactory beanFactory) throws Exception {

    }

    @Override
    public Object getBean(String name) throws BeanException {
        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return null;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return false;
    }
}
