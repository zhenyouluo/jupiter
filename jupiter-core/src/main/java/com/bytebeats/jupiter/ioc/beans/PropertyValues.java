package com.bytebeats.jupiter.ioc.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-12 23:25
 */
public class PropertyValues {
    private List<PropertyValue> propertyValueList;

    public PropertyValues() {
    }

    public void addPropertyValue(PropertyValue pv) {
        if(this.propertyValueList==null){
            this.propertyValueList = new ArrayList<PropertyValue>();
        }
        this.propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }

}
