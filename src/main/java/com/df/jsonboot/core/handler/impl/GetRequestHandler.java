package com.df.jsonboot.core.handler.impl;

import com.df.jsonboot.core.factory.MethodDetailFactory;
import com.df.jsonboot.core.handler.RequestHandler;
import com.df.jsonboot.core.resolver.ParameterResolver;
import com.df.jsonboot.core.factory.ParameterResolverFactory;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.ReflectionUtil;
import com.df.jsonboot.utils.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理Get的http请求
 *
 * @author qinghuo
 * @since 2021/03/21 9:55
 */
@Slf4j
public class GetRequestHandler implements RequestHandler {

    @Override
    public Object handler(FullHttpRequest fullHttpRequest) {
        String uri = fullHttpRequest.uri();
        Map<String, String> queryParamMap = UrlUtil.getQueryParam(uri);
        String path = UrlUtil.convertUriToPath(uri);
        MethodDetail methodDetail = MethodDetailFactory.getMethodDetail(path, HttpMethod.GET);
        if (methodDetail == null || methodDetail.getMethod() == null){
            return null;
        }
        log.info("request path: {}, method: {}", path, methodDetail.getMethod().getName());
        methodDetail.setQueryParameterMappings(queryParamMap);
        //获取到该方法到参数
        Parameter[] parameters = methodDetail.getMethod().getParameters();
        List<Object> params = new ArrayList<>();
        for (Parameter parameter : parameters){
            ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
            Object result = parameterResolver.resolve(methodDetail, parameter);
            params.add(result);
        }
        return ReflectionUtil.executeMethod(methodDetail.getMethod(), params.toArray());
    }

}
