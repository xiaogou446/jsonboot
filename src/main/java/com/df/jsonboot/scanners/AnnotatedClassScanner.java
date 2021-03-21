package com.df.jsonboot.scanners;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 注解解析类，为注解实现作用
 *
 * @author qinghuo
 * @since 2021/03/21 15:07
 */
@Slf4j
public class AnnotatedClassScanner {

    /**
     * 实现路径内注解扫描功能
     *
     * @param packageName 需要扫描的包路径
     * @param annotation 需要在包路径内寻找类的注解
     * @return 包路径内包含该注解的类
     */
    public Set<Class<?>> scan(String packageName, Class<? extends Annotation> annotation){
        //初始化工具类 指定包名和注解对应的类型
        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
        //获取某个包下对应功能的注解类
        Set<Class<?>> annotationClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("annotationClass : {}, size: {}", annotationClass, annotationClass.size());
        return annotationClass;
    }

}
