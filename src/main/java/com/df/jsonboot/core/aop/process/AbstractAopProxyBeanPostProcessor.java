package com.df.jsonboot.core.aop.process;

import com.df.jsonboot.annotation.aop.Aspect;
import com.df.jsonboot.core.aop.intercept.Interceptor;
import com.df.jsonboot.core.aop.intercept.InternallyAspectInterceptor;
import com.df.jsonboot.core.ioc.BeanFactory;

import java.util.Set;

/**
 * aop 抽象类
 *
 * @author qinghuo
 * @since 2021/03/30 14:12
 */
public abstract class AbstractAopProxyBeanPostProcessor implements BeanPostProcessor{


    @Override
    public Object postProcessAfterInitialization(Object bean) {
        Object proxyBean = bean;
        //这个恶心的循环。。。迟早给他鲨了
        Set<Object> aspectBeans = BeanFactory.getBeansByName(Aspect.class.getName());
        //遍历AspectBeans的列表，并判断目标bean是否符合该aspect
        for (Object aspectBean : aspectBeans) {
            InternallyAspectInterceptor interceptor = new InternallyAspectInterceptor(aspectBean);
            if (interceptor.supports(bean)){
                //进行代理封装
                proxyBean = wrapperBean(bean, interceptor);
            }
        }
        return proxyBean;
    }

    /**
     * 封装/代理 bean
     *
     * @param target 目标bean
     * @param interceptor 拦截器
     * @return 代理bean
     */
    public abstract Object wrapperBean(Object target, Interceptor interceptor);
}
