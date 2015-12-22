package com.ricky.java.ioc.anotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Documented
public @interface MyResource {
	
	/**
	 * 在Java中使用@Autowired和@Resource注解进行装配，这两个注解分别是：
1、@Autowired按照默认类型(类名称)装配依赖对象，默认情况下它要求依赖对象必须存在，如果允许为null,可以设置它的required属性为false
如果我们按名称装配，可以结合@Qualifie注解一起使用。
@Resource默认按照名称(name="test")进行装配,名称可以通过@resource的name属性设定，当找不到与名称匹配的bean才会按类型装配。
	 */
	
    public String name() default "";
    
}
