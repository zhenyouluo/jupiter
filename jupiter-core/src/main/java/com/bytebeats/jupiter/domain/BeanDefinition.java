package com.bytebeats.jupiter.domain;

import java.util.List;

public class BeanDefinition {
	private String id;
    private String className;
    private String scope;	//singleton|prototype
    private String initMethodName;
    private List<PropertyDefinition> properties;
    
    public BeanDefinition(String id, String className) {  
        this.id = id;  
        this.className = className;  
    }
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getInitMethodName() {
		return initMethodName;
	}
	public void setInitMethodName(String initMethodName) {
		this.initMethodName = initMethodName;
	}
	public List<PropertyDefinition> getProperties() {
		return properties;
	}
	public void setProperties(List<PropertyDefinition> properties) {
		this.properties = properties;
	}

	@Override
	public String toString() {
		return "BeanDefinition [id=" + id + ", className=" + className
				+ ", scope=" + scope + ", initMethodName=" + initMethodName
				+ ", properties=" + properties + "]";
	}

}
