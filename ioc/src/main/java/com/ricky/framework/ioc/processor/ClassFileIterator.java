package com.ricky.framework.ioc.processor;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.ricky.framework.ioc.processor.AnnotationDetector.AnnotationFilter;

public class ClassFileIterator {
	
	private HashSet<String> classNameSet = new HashSet<String>();
	
	public ClassFileIterator(File[] files){
		
		for (File jarFile : files) {
			System.out.println("************"+jarFile.getAbsolutePath()+"***********");
			listClassFile(jarFile, classNameSet);
			System.out.println("*****************************************************");
		}
		
		System.out.println("classNameSet size="+classNameSet.size());
		
	}

	public List<Class<?>> iterate(Class<? extends Annotation> scanAnnotation, AnnotationFilter annotationFilter) {
		
		List<Class<?>> classList = new ArrayList<Class<?>>();
		
		for (String className : classNameSet) {
			
			Class<?> clazz = null;
			try {
				clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(clazz!=null && annotationFilter.filter(clazz, scanAnnotation)){
				classList.add(clazz);
			}
		}
		
		return classList;
	}

	private void listClassFile(File jarFile, HashSet<String> classNameSet){
		
		if(jarFile.isDirectory()){
			
			File[] files = jarFile.listFiles();
			for (File file : files) {
				listClassFile(file, classNameSet);
			}
		}else if(jarFile.isFile()){
			
			String filename = jarFile.getAbsolutePath();
			if(filename.endsWith(".class")){
				System.out.println("class file:"+jarFile.getAbsolutePath());
				if(filename.indexOf('$') != -1){	//内部类
					
				}
				String className = filename.substring(filename.indexOf("bin")+4, filename.lastIndexOf(".")).replace("\\", ".");
				System.out.println("className:"+className);
				classNameSet.add(className);
			}
		}
		
	}
}
