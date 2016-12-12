package com.bytebeats.jupiter.ioc.beans.factory;

import com.bytebeats.jupiter.ioc.beans.BeanDefinition;
import com.bytebeats.jupiter.ioc.beans.BeanException;
import com.bytebeats.jupiter.ioc.beans.BeanReference;
import com.bytebeats.jupiter.ioc.beans.PropertyValue;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-12 23:35
 */
public class XmlBeanFactory extends AbstractBeanFactory {

    @Override
    protected void applyPropertyValues(Object bean, BeanDefinition bd) throws BeanException {
        for (PropertyValue propertyValue : bd.getPropertyValues().getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }

            try {
                injectPropertyValueBySetter(bean, propertyValue, value);
            } catch (NoSuchMethodException e) {
                injectPropertyValueByField(bean, propertyValue, value);
            } catch (InvocationTargetException e) {
                throw new BeanException("", e);
            } catch (IllegalAccessException e) {
                throw new BeanException("", e);
            }
        }
    }

    private void injectPropertyValueBySetter(Object bean, PropertyValue propertyValue, Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method declaredMethod = bean.getClass().getDeclaredMethod(
                "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                        + propertyValue.getName().substring(1), value.getClass());

        declaredMethod.setAccessible(true);
        declaredMethod.invoke(bean, value);
    }

    private void injectPropertyValueByField(Object bean, PropertyValue propertyValue, Object value) throws BeanException {
        try{
            Field declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
            declaredField.setAccessible(true);
            declaredField.set(bean, value);
        }catch (Exception e){
            throw new BeanException("", e);
        }

    }
}
