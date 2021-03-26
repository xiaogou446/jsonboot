package com.df.jsonboot.core.context;

import com.df.jsonboot.core.springmvc.factory.ClassFactory;
import com.df.jsonboot.core.springmvc.factory.RouteFactory;
import com.df.jsonboot.core.ioc.BeanFactory;
import com.df.jsonboot.core.ioc.DependencyInjection;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将请求通过注解进行组合路由
 *
 * @author qinghuo
 * @since 2021/03/21 16:04
 */
public class ApplicationContext {

    /**
     * 生成饿汉式
     */
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();

    /**
     * 用于存放范围下注解扫描的类
     */
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new HashMap<>();


    private ApplicationContext(){}

    public void run(String packageName){
        ClassFactory.loadClass(packageName);
        RouteFactory.loadRoutes();
        BeanFactory.loadBeans();
        DependencyInjection.loadDependency(packageName);

    }

    /**
     * 获取实例对象
     *
     * @return 唯一的ApplicationContext对象
     */
    public static ApplicationContext getInstance(){
        return APPLICATION_CONTEXT;
    }

}
