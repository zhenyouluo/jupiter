package com.bytebeats.jupiter.ioc.beans;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-13 00:09
 */
public class BeanReference {
    private String name;

    private Object bean;

    public BeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }
}
