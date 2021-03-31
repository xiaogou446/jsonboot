package com.df.jsonboot.core.aop.jdk;

import com.df.jsonboot.core.aop.intercept.Interceptor;
import com.df.jsonboot.entity.MethodInvocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk动态代理生成执行类
 *
 * @author qinghuo
 * @since 2021/03/30 14:33
 */
public class JdkInvocationHandler implements InvocationHandler {

    /**
     * 目标对象
     */
    private final Object target;

    /**
     * 执行的拦截器
     */
    private final Interceptor interceptor;

    private JdkInvocationHandler(Object target, Interceptor interceptor){
        this.target = target;
        this.interceptor = interceptor;
    }

    public static Object wrap(Object target, Interceptor interceptor){
        JdkInvocationHandler jdkInvocationHandler = new JdkInvocationHandler(target, interceptor);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces()
                                        , jdkInvocationHandler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, args);
        return interceptor.intercept(methodInvocation);
    }
}
