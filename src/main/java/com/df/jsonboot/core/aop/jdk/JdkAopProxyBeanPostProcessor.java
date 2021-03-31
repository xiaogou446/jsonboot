package com.df.jsonboot.core.aop.jdk;

import com.df.jsonboot.core.aop.intercept.Interceptor;
import com.df.jsonboot.core.aop.process.AbstractAopProxyBeanPostProcessor;

/**
 * jdk代理包装类
 *
 * @author qinghuo
 * @since 2021/03/30 14:32
 */
public class JdkAopProxyBeanPostProcessor extends AbstractAopProxyBeanPostProcessor {

    @Override
    public Object wrapperBean(Object target, Interceptor interceptor) {
        return JdkInvocationHandler.wrap(target, interceptor);
    }
}
