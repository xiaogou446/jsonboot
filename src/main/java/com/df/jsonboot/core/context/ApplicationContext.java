package com.df.jsonboot.core.context;

import com.df.jsonboot.annotation.Component;
import com.df.jsonboot.annotation.GetMapping;
import com.df.jsonboot.annotation.PostMapping;
import com.df.jsonboot.annotation.RestController;
import com.df.jsonboot.core.scanners.AnnotatedClassScanner;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.UrlUtil;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

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

    public void loadClass(String packageName){
        AnnotatedClassScanner annotatedScanner = new AnnotatedClassScanner();
        Set<Class<?>> restControllerClasses = annotatedScanner.scan(packageName, RestController.class);
        Set<Class<?>> componentClasses = annotatedScanner.scan(packageName, Component.class);
        CLASSES.put(RestController.class, restControllerClasses);
        CLASSES.put(Component.class, componentClasses);
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
