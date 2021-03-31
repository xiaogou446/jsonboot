package com.df.jsonboot.core.aop.intercept;

import com.df.jsonboot.entity.MethodInvocation;

/**
 * 拦截器抽象类
 *
 * @author qinghuo
 * @since 2021/03/30 10:39
 */
public abstract class Interceptor {

    /**
     * 拦截器优先级顺序
     *
     * @return 顺序值
     */
    public int getOrder(){
        return -1;
    }

    /**
     * 该拦截器是否适配该bean
     *
     * @param object bean
     * @return 是否适配
     */
    public boolean supports(Object object){
        return false;
    }

    /**
     * 方法执行器
     *
     * @param methodInvocation 方法执行器
     * @return 返回数据
     */
    public abstract Object intercept(MethodInvocation methodInvocation);

}
