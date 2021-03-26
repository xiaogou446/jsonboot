package com.df.jsonboot.core.springmvc.resolver.impl;

import com.df.jsonboot.annotation.springmvc.PathVariable;
import com.df.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.ObjectUtil;

import java.lang.reflect.Parameter;

/**
 * PathVariable注解的实现
 *
 * @author qinghuo
 * @since 2021/03/24 16:14
 */
public class PathVariableParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
        //获取每个参数的类型
        Class<?> type = parameter.getType();
        String mappingKey = pathVariable.value();
        String value = methodDetail.getUrlParameterMappings().get(mappingKey);
        return ObjectUtil.convertToClass(type, value);
    }
}
