package com.ricky.java.ioc.processor;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AnnotationDetector {
	
	private String[] scanPackageNames;
	private Class<? extends Annotation> scanAnnotation;
	private int target;
	
	public static final int TYPE = 1;
	public static final int METHOD = 2;
	public static final int FIELD = 3;
	
	public interface AnnotationFilter {
		
        boolean filter(Class<?> clazz, Class<? extends Annotation> scanAnnotation);
    }
	
	public class TypeAnnotationFilter implements AnnotationFilter{

		public boolean filter(Class<?> clazz,
				Class<? extends Annotation> scanAnnotation) {
			
			return clazz.isAnnotationPresent(scanAnnotation);
		}
		
	}
	
	public class MethodAnnotationFilter implements AnnotationFilter{

		public boolean filter(Class<?> clazz,
				Class<? extends Annotation> scanAnnotation) {
			
			Method[] methods = clazz.getDeclaredMethods();
			if(methods!=null){
				for (Method method : methods) {
					if(method.isAnnotationPresent(scanAnnotation)){
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
	public class FieldAnnotationFilter implements AnnotationFilter{

		public boolean filter(Class<?> clazz,
				Class<? extends Annotation> scanAnnotation) {
			
			Field[] fields = clazz.getDeclaredFields();
			if(fields!=null){
				for (Field field : fields) {
					if(field.isAnnotationPresent(scanAnnotation)){
						return true;
					}
				}
			}
			return false;
		}
		
	}
	
	AnnotationDetector(){}
	
	AnnotationDetector(String[] scanPackageNames,
			Class<? extends Annotation> scanAnnotation, int target) {
		this.scanPackageNames = scanPackageNames;
		this.scanAnnotation = scanAnnotation;
		this.target = target;
	}

	public static class Builder {
        private String[] scanPackageNames;
        private Class<? extends Annotation> scanAnnotation;
        private int target;
        
        public Builder scanPackageNames(final String... scanPackageNames) {  
            this.scanPackageNames = scanPackageNames;  
            return this;  
        }
        
        public Builder scanAnnotation(final Class<? extends Annotation> scanAnnotation) {  
            this.scanAnnotation = scanAnnotation;  
            return this;  
        }  
          
        public Builder target(final int target) {  
            this.target = target;  
            return this;  
        }  
        
        public AnnotationDetector build() {  
              
            return new AnnotationDetector(scanPackageNames, scanAnnotation, target);
        }  
    }  
	
    public static AnnotationDetector.Builder custom() {  
        return new Builder();  
    }
	
	public List<Class<?>> detect() throws IOException{
		
		File[] scanFiles = null;
		if(scanPackageNames!=null && scanPackageNames.length>0){
			scanFiles = getPackagePathFile(scanPackageNames);
		}else{
			scanFiles = getClassPathFile();
		}
		
		AnnotationFilter annotationFilter = null;
		switch (target) {
		case TYPE:
			annotationFilter = new TypeAnnotationFilter();
			break;
		case METHOD:
			annotationFilter = new MethodAnnotationFilter();
			break;
		case FIELD:
			annotationFilter = new FieldAnnotationFilter();
			break;
		default:
			throw new IllegalArgumentException("Unknown AnnotationFilter");
		}
		
		return new ClassFileIterator(scanFiles).iterate(scanAnnotation, annotationFilter);
	}
	
	public File[] getPackagePathFile(final String... packageNames) throws IOException {
        final String[] pkgNameFilter = new String[packageNames.length];
        for (int i = 0; i < pkgNameFilter.length; ++i) {
            pkgNameFilter[i] = packageNames[i].replace('.', '/');
            if (!pkgNameFilter[i].endsWith("/")) {
                pkgNameFilter[i] = pkgNameFilter[i].concat("/");
            }
        }
        final Set<File> fileSet = new HashSet<File>();
        for (final String packageName : pkgNameFilter) {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final Enumeration<URL> resourceEnum = loader.getResources(packageName);
            while (resourceEnum.hasMoreElements()) {
                final URL url = resourceEnum.nextElement();
                final boolean isVfs = url.getProtocol().startsWith("vfs");
                if ("file".equals(url.getProtocol()) || isVfs) {
                    final File dir = toFile(url);
                    if (dir.isDirectory()) {
                    	fileSet.add(dir);
                    } else if (isVfs) {
                        String jarPath = dir.getPath();
                        final int idx = jarPath.indexOf(".jar");
                        if (idx > -1) {
                            jarPath = jarPath.substring(0, idx + 4);
                            final File jarFile = new File(jarPath);
                            if (jarFile.isFile()) {
                            	fileSet.add(jarFile);
                            }
                        }
                    } else {
                        throw new AssertionError("Not a recognized file URL: " + url);
                    }
                } else {
                    // Resource in Jar File
                    final File jarFile =
                        toFile(((JarURLConnection)url.openConnection()).getJarFileURL());
                    if (jarFile.isFile()) {
                    	fileSet.add(jarFile);
                    } else {
                        throw new AssertionError("Not a File: " + jarFile);
                    }
                }
            }
        }
        
        return fileSet.toArray(new File[fileSet.size()]);
    }
	
	private File toFile(final URL url) throws MalformedURLException {
        
        try {
            return new File(url.toURI().getPath());
        } catch (Exception ex) {
            final MalformedURLException mue = new MalformedURLException(url.toExternalForm());
            mue.initCause(ex);
            throw mue;
        }
    }
	
	private File[] getClassPathFile() {
        final String[] fileNames = System.getProperty("java.class.path")
            .split(File.pathSeparator);
        final File[] files = new File[fileNames.length];
        for (int i = 0; i < files.length; ++i) {
            files[i] = new File(fileNames[i]);
        }
        return files;
    }
}
