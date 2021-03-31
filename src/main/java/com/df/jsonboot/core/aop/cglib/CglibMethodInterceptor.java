package com.df.jsonboot.core.aop.cglib;

import com.df.jsonboot.core.aop.intercept.Interceptor;
import com.df.jsonboot.entity.MethodInvocation;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 *
 *
 * @author qinghuo
 * @since 2021/03/31 14:04
 */
public class CglibMethodInterceptor implements MethodInterceptor {

    /**
     * 目标对象
     */
    private final Object target;

    /**
     * 定义的拦截器
     */
    private final Interceptor interceptor;

    private CglibMethodInterceptor (Object target, Interceptor interceptor){
        this.target = target;
        this.interceptor = interceptor;
    }

    /**
     * 包装生成代理类
     *
     * @param target 目标对象
     * @param interceptor 执行的拦截器
     * @return 代理类
     */
    public static Object wrap(Object target, Interceptor interceptor){
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(target.getClass().getClassLoader());
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibMethodInterceptor(target, interceptor));
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        MethodInvocation methodInvocation = new MethodInvocation(target, method, objects);
        return interceptor.intercept(methodInvocation);
    }
}
