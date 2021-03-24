package com.df.jsonboot.core.resolver.impl;

import com.df.jsonboot.annotation.RequestParam;
import com.df.jsonboot.core.resolver.ParameterResolver;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.ObjectUtil;

import java.lang.reflect.Parameter;

/**
 * RequestParam 注解的实现
 *
 * @author qinghuo
 * @since 2021/03/24 15:47
 */
public class RequestParamParameterResolver implements ParameterResolver {

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        //获取每个参数的类型
        Class<?> type = parameter.getType();
        //获取注解设置的value值
        String paramKey = requestParam.value();
        //以注解设置的value值为key去参数map中取出值
        String paramValue = methodDetail.getQueryParameterMappings().get(paramKey);
        return ObjectUtil.convertToClass(type, paramValue);
    }

}
