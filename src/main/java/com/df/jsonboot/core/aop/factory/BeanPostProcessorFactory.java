package com.df.jsonboot.core.aop.factory;

import com.df.jsonboot.core.aop.cglib.CglibAopProxyBeanPostProcessor;
import com.df.jsonboot.core.aop.jdk.JdkAopProxyBeanPostProcessor;
import com.df.jsonboot.core.aop.process.BeanPostProcessor;

/**
 * 选择aop的代理工厂，jdk or cglib
 *
 * @author qinghuo
 * @since 2021/03/30 14:49
 */
public class BeanPostProcessorFactory {

    /**
     * 根据bean是否具有接口来选择对应的代理生成器
     *
     * @param bean 要进行代理的目标bean
     * @return 对应类型的代理类生成器
     */
    public static BeanPostProcessor getBeanPostProcessor(Object bean){
        if (bean.getClass().getInterfaces().length> 0){
            return new JdkAopProxyBeanPostProcessor();
        } else {
            return new CglibAopProxyBeanPostProcessor();
        }

    }

}
