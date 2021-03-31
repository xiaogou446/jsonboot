package com.df.jsonboot.core.aop.cglib;

import com.df.jsonboot.core.aop.intercept.Interceptor;
import com.df.jsonboot.core.aop.process.AbstractAopProxyBeanPostProcessor;
import com.df.jsonboot.core.aop.process.BeanPostProcessor;

/**
 * 基于jdk动态代理的包装类
 *
 * @author qinghuo
 * @since 2021/03/31 13:45
 */
public class CglibAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

    @Override
    public Object wrapperBean(Object target, Interceptor interceptor) {
        return CglibMethodInterceptor.wrap(target, interceptor);
    }
}
