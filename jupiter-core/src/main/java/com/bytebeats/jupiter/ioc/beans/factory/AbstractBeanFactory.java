package com.bytebeats.jupiter.ioc.beans.factory;

import com.bytebeats.jupiter.ioc.beans.BeanDefinition;
import com.bytebeats.jupiter.ioc.beans.BeanException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-12 23:13
 */
public abstract class AbstractBeanFactory implements BeanFactory {
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private final List<String> beanDefinitionNames = new ArrayList<>();

    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) throws Exception {
        beanDefinitionMap.put(name, beanDefinition);
        beanDefinitionNames.add(name);
    }

    public void preInstantiateSingletons() throws Exception {
        for (Iterator<?> it = this.beanDefinitionNames.iterator(); it.hasNext();) {
            String beanName = (String) it.next();

            BeanDefinition bd = beanDefinitionMap.get(beanName);
            if("singleton".equals(bd.getScope())){
                Object bean = doCreateBean(bd);
                initializeBean(bean, bd);
            }
        }
    }

    @Override
    public Object getBean(String name) throws BeanException {
        BeanDefinition bd = beanDefinitionMap.get(name);
        if (bd == null) {
            throw new IllegalArgumentException("No bean named " + name + " is defined");
        }
        Object bean = bd.getBean();
        if("singleton".equals(bd.getScope())){
            return bean;
        }
        bean = doCreateBean(bd);
        bean = initializeBean(bean, bd);
        return bean;
    }

    @Override
    public <T> T getBean(Class<T> requiredType) throws BeanException {

        return null;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return beanDefinitionMap.containsKey(name);
    }

    protected abstract void applyPropertyValues(Object bean, BeanDefinition bd) throws BeanException;

    private Object initializeBean(Object bean, BeanDefinition bd) {

        return bean;
    }

    private Object doCreateBean(BeanDefinition bd) throws BeanException {
        Object bean = null;
        try {
            bean = createBeanInstance(bd);
        } catch (Exception e) {
            throw new BeanException("can not create bean instance:"+bd.getBeanClassName(), e);
        }
        bd.setBean(bean);
        applyPropertyValues(bean, bd);
        return bean;
    }

    private Object createBeanInstance(BeanDefinition bd) throws Exception {
        return bd.getBeanClass().newInstance();
    }
}
