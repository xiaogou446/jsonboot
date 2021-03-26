package com.df.jsonboot.core.springmvc.factory;

import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.annotation.springmvc.RestController;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.utils.ReflectionUtil;

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
        Set<Class<?>> restControllerClasses = ReflectionUtil.scanAnnotatedClass(packageName, RestController.class);
        Set<Class<?>> componentClasses = ReflectionUtil.scanAnnotatedClass(packageName, Component.class);
        ApplicationContext.CLASSES.put(RestController.class, restControllerClasses);
        ApplicationContext.CLASSES.put(Component.class, componentClasses);
    }

}
