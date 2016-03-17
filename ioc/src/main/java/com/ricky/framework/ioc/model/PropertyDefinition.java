package com.ricky.framework.ioc.model;

public class PropertyDefinition {  
    private String name;
    private String ref;
  
    public PropertyDefinition(String name, String ref) {  
        this.name = name;  
        this.ref = ref;  
    }  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public String getRef() {  
        return ref;  
    }  
    public void setRef(String ref) {  
        this.ref = ref;  
    }
    
	@Override
	public String toString() {
		return "PropertyDefinition [name=" + name + ", ref=" + ref + "]";
	}
    
}  