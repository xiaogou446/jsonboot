package com.df.jsonboot.utils;

import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.annotation.springmvc.RestController;
import com.df.jsonboot.core.ioc.BeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

/**
 * 反射工具类
 *
 * @author qinghuo
 * @since 2021/03/22 14:39
 */
@Slf4j
public class ReflectionUtil {

    /**
     * 获取目标类上标注Component注解的值
     *
     * @param aClass 要获取注解值的类
     * @param annotation 标注的注解
     * @param defaultValue 默认值
     * @return 注解标注的值
     */
    public static String getComponentValue(Class<?> aClass, Class<? extends Component> annotation
                                                , String defaultValue){
        if (!aClass.isAnnotationPresent(annotation)){
            return defaultValue;
        }
        Component declaredAnnotation = aClass.getDeclaredAnnotation(annotation);
        return StringUtils.isBlank(declaredAnnotation.value()) ? defaultValue : declaredAnnotation.value();
    }

    /**
     * @param method 目标方法
     * @param args   调用的参数
     * @return 执行结果
     */
    public static Object executeMethod(Method method, Object... args) {
        Object result = null;
        try {
            String beanName = null;
            Object targetObject;
            //先判断是否已经生成该对象了 直接在ioc的容器中取出来
            Class<?> targetClass = method.getDeclaringClass();
            if (targetClass.isAnnotationPresent(RestController.class)){
                beanName = targetClass.getName();
            }
            if (targetClass.isAnnotationPresent(Component.class)){
                Component component = targetClass.getAnnotation(Component.class);
                beanName = StringUtils.isBlank(component.value()) ? targetClass.getName() : component.value();
            }
            if (StringUtils.isNotEmpty(beanName)){
                targetObject = BeanFactory.BEANS.get(beanName);
            }else{
                targetObject = method.getDeclaringClass().newInstance();
            }
            // 调用对象的方法
            result = method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过反射生成对象
     *
     * @param aClass 类的类型
     * @return 类生成的对象
     */
    public static Object newInstance(Class<?> aClass){
        Object instance = null;
        try {
            instance = aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("实例化对象失败 class: {}", aClass);
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 为对象的属性设值
     *
     * @param obj 需要设置属性的对象
     * @param field 属性
     * @param value 设置的值
     */
    public static void setReflectionField(Object obj, Field field, Object value){
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("设置对象field失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取接口的对应实现类
     *
     * @param packageName 包名
     * @param interfaceClass 接口
     * @return 接口的实现类
     */
    @SuppressWarnings("unchecked")
    public static Set<Class<?>> getSubClass(String packageName, Class<?> interfaceClass) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf((Class<Object>) interfaceClass);
    }

    /**
     * 实现路径内注解扫描功能
     *
     * @param packageName 需要扫描的包路径
     * @param annotation 需要在包路径内寻找类的注解
     * @return 包路径内包含该注解的类
     */
    public static Set<Class<?>> scanAnnotatedClass(String packageName, Class<? extends Annotation> annotation){
        //初始化工具类 指定包名和注解对应的类型
        Reflections reflections = new Reflections(packageName, new TypeAnnotationsScanner());
        //获取某个包下对应功能的注解类
        Set<Class<?>> annotationClass = reflections.getTypesAnnotatedWith(annotation, true);
        log.info("annotationClass : {}, size: {}", annotationClass, annotationClass.size());
        return annotationClass;
    }

    /**
     * 反射调用无返回值的方法
     *
     * @param targetObject 执行的目标对象
     * @param method 执行的方法
     * @param args 执行的参数
     */
    public static void executeMethodNoResult(Object targetObject, Method method, Object... args){
        try {
            System.out.println(targetObject);
            System.out.println(method);
            System.out.println(Arrays.toString(args));
            method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行目标方法
     *
     * @param targetObject 目标对象
     * @param method 目标方法
     * @param args 参数
     * @return 执行结果
     */
    public static Object executeTargetMethod(Object targetObject, Method method, Object... args){
        Object result = null;
        try {
            result = method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用于将String对象转换为需要的类型对象(已废弃，使用PropertyEditorManager代替)
     *
     * @param type 原类型
     * @param str 需要转换的字符串
     * @return 转换后的对象
     */
    @Deprecated
    public static Object getNumber(Class<?> type,String str) {
        Class<?>[] paramsClasses = { str.getClass() };
        Object[] params = { str };
        Class<?> typeClass = ObjectUtil.convertBaseClass(type);
        Object o = null;
        try {
            Constructor<?> c = typeClass.getConstructor(paramsClasses);
            o = c.newInstance(params);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return o;
    }
}
