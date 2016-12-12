package com.bytebeats.jupiter.ioc.beans.xml;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bytebeats.jupiter.domain.BeanDefinition;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.bytebeats.jupiter.domain.PropertyDefinition;

public class BeanXmlConfigParser {

	public List<BeanDefinition> parse(String filename)
			throws FileNotFoundException, DocumentException {

		Document doc = getDocument(filename);

		Element root = doc.getRootElement(); // 取得根节点  

		List<BeanDefinition> bean_def_list = new ArrayList<>();
		
		for ( Iterator<?> itor = root.elementIterator( "bean" ); itor.hasNext(); ) {
			
            Element element = (Element) itor.next();
			String id = element.attributeValue("id");// id;  
            String clazz = element.attributeValue("class");  
            
			BeanDefinition beanDefinition = new BeanDefinition(id, clazz);
			beanDefinition.setScope(element.attributeValue("scope"));
			beanDefinition.setInitMethodName(element.attributeValue("init-method"));
			
			// 读取property元素
			List<PropertyDefinition> property_def_list = new ArrayList<>();
			for ( Iterator<?> i = element.elementIterator("property"); i.hasNext(); ) {
				Element e = (Element) i.next();
				
				String name = e.attributeValue("name");
				String ref = e.attributeValue("ref");
				
				property_def_list.add(new PropertyDefinition(name, ref));
	        }
							
			beanDefinition.setProperties(property_def_list);
			
			bean_def_list.add(beanDefinition);
        }

		return bean_def_list;
	}

	public Document getDocument(String filename) throws DocumentException,
			FileNotFoundException {
		
		System.out.println("xmlFilePath:"+filename);
		
		SAXReader reader = new SAXReader();
		return reader.read(this.getClass().getClassLoader().getResourceAsStream(filename));
	}
}
