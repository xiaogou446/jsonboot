package com.df.jsonboot.core.factory;

import com.df.jsonboot.annotation.Component;
import com.df.jsonboot.annotation.RestController;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.core.scanners.AnnotatedClassScanner;

import java.util.Set;

/**
 * 启动时类加载
 *
 * @author qinghuo
 * @since 2021/03/25 11:25
 */
public class ClassFactory {

    /**
     * 加载包路径下的类
     *
     * @param packageName 需要加载类的包路径
     */
    public static void loadClass(String packageName){
        AnnotatedClassScanner annotatedScanner = new AnnotatedClassScanner();
        Set<Class<?>> restControllerClasses = annotatedScanner.scan(packageName, RestController.class);
        Set<Class<?>> componentClasses = annotatedScanner.scan(packageName, Component.class);
        ApplicationContext.CLASSES.put(RestController.class, restControllerClasses);
        ApplicationContext.CLASSES.put(Component.class, componentClasses);
    }

}
