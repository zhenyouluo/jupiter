package com.bytebeats.jupiter.ioc.beans;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-12 23:38
 */
public interface BeanDefinitionReader {

    void loadBeanDefinitions(String location) throws Exception;
}
