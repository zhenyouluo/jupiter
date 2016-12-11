package com.bytebeats.jupiter;

import com.bytebeats.jupiter.ioc.ClassPathResourceScanner;
import java.io.IOException;
import java.util.Set;

/**
 * ${DESCRIPTION}
 *
 * @author Ricky Fung
 * @create 2016-12-11 23:14
 */
public class App {

    public static void main(String[] args) throws IOException {

        ClassPathResourceScanner scanner = new ClassPathResourceScanner();
        //要扫描的包名
        String packageName = "com.bytebeats";
        //获取该包下所有的类名称
        Set<Class<?>> set = scanner.findCandidateClasses(packageName);
        System.out.println(set.size());
        for (Class<?> cls : set){
            System.out.println(cls.getName());
        }
    }
}
