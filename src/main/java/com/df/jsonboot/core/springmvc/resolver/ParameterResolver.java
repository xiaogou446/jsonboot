package com.df.jsonboot.core.springmvc.resolver;

import com.df.jsonboot.entity.MethodDetail;

import java.lang.reflect.Parameter;

/**
 * 注解实现接口
 *
 * @author qinghuo
 * @since 2021/03/24 15:43
 */
public interface ParameterResolver {

    /**
     * 调用注解的接口
     *
     * @param methodDetail 调用方法的methodDetail
     * @param parameter 使用的参数
     * @return 处理后的结果
     */
    Object resolve(MethodDetail methodDetail, Parameter parameter);

}
