package com.df.jsonboot.core.handler.impl;

import com.df.jsonboot.annotation.RequestParam;
import com.df.jsonboot.core.handler.RequestHandler;
import com.df.jsonboot.core.route.Route;
import com.df.jsonboot.utils.ObjectUtil;
import com.df.jsonboot.utils.ReflectionUtil;
import com.df.jsonboot.utils.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

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
        Method method = Route.getMethodMapping.get(path);
        if (method == null){
            return null;
        }
        log.info("request path: {}, method: {}", path, method.getName());
        //获取到该方法到参数
        Parameter[] parameters = method.getParameters();
        List<Object> params = new ArrayList<>();
        for (Parameter parameter : parameters){
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            //获取每个参数的类型
            Class<?> type = parameter.getType();
            //定义最终参数
            String paramValue;
            //有注解的情况
            if (requestParam != null){
                //获取注解设置的value值
                String paramKey = requestParam.value();
                //以注解设置的value值为key去参数map中取出值
                paramValue = queryParamMap.get(paramKey);
            }else{
                //如果没有注解，则直接进行名称对应查找
                paramValue = queryParamMap.get(parameter.getName());
            }
            params.add(ObjectUtil.convertToClass(type, paramValue));
        }
        return ReflectionUtil.executeMethod(method, params.toArray());
    }

}
