package com.df.jsonboot.core.aop.lang;

/**
 * 连接点定义
 *
 * @author qinghuo
 * @since 2021/03/30 13:41
 */
public interface JoinPoint {

    /**
     * 获取切面的bean
     *
     * @return 切面的bean
     */
    Object getAspectBean();

    /**
     * 获取执行目标对象
     *
     * @return 执行对象
     */
    Object getTarget();

    /**
     * 获取执行的参数
     *
     * @return 执行参数
     */
    Object[] getArgs();
}
