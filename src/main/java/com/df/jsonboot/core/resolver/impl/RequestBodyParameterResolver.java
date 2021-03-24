package com.df.jsonboot.core.resolver.impl;

import com.df.jsonboot.annotation.RequestBody;
import com.df.jsonboot.annotation.RequestParam;
import com.df.jsonboot.core.resolver.ParameterResolver;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.ObjectUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;

/**
 *
 *
 * @author qinghuo
 * @since 2021/03/24 15:48
 */
public class RequestBodyParameterResolver implements ParameterResolver {

    /**
     * 转换格式
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object resolve(MethodDetail methodDetail, Parameter parameter) {
        Object result = null;
        try {
            result = objectMapper.readValue(methodDetail.getJson(), parameter.getType());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
