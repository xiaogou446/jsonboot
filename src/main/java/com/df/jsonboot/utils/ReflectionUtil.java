package com.df.jsonboot.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类
 *
 * @author qinghuo
 * @since 2021/03/22 14:39
 */
@Slf4j
public class ReflectionUtil {

    /**
     * @param method 目标方法
     * @param args   调用的参数
     * @return 执行结果
     */
    public static Object executeMethod(Method method, Object... args) {
        Object result = null;
        try {
            // 生成方法对应类的对象
            Object targetObject = method.getDeclaringClass().newInstance();
            // 调用对象的方法
            result = method.invoke(targetObject, args);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
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
        Class<?> typeClass = ObjectUtils.convertBaseClass(type);
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
