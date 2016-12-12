package com.bytebeats.jupiter.ioc.beans.factory;

import com.bytebeats.jupiter.ioc.beans.BeanException;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-12 22:38
 */
public interface BeanFactory {

    Object getBean(String name) throws BeanException;

    <T> T getBean(String name, Class<T> requiredType) throws BeanException;

    <T> T getBean(Class<T> requiredType) throws BeanException;

    boolean containsBean(String name);

}
