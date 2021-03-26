package com.df.jsonboot.core.springmvc.resolver.impl;

import com.df.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.ObjectUtil;

import java.lang.reflect.Parameter;

/**
 * 如果parameter上没有任何注解，就使用默认的parameter处理方式
 *
 * @author qinghuo
 * @since 2021/03/24 16:48
 */
public class DefaultParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        //获取每个参数的类型
        Class<?> type = parameter.getType();
        //如果没有注解，则直接进行名称对应查找
        String paramValue = methodDetail.getQueryParameterMappings().get(parameter.getName());
        return ObjectUtil.convertToClass(type, paramValue);
    }
}
