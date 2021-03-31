package com.df.jsonboot.core.aop.process;

/**
 * bean初始化时的前后处理器
 *
 * @author qinghuo
 * @since 2021/03/29 14:52
 */
public interface BeanPostProcessor {

    /**
     * 初始化的后置处理器，在这个地方执行了aop代理
     *
     * @param bean 执行初始化的目标bean
     * @return 后置处理后的对象
     */
    Object postProcessAfterInitialization(Object bean);

}
