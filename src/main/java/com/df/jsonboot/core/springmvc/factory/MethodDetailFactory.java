package com.df.jsonboot.core.springmvc.factory;

import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.UrlUtil;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Pattern;

import static com.df.jsonboot.core.springmvc.factory.RouteFactory.*;

/**
 * 生成MethodDetail的工厂类
 *
 * @author qinghuo
 * @since 2021/03/25 9:58
 */
public class MethodDetailFactory {

    /**
     * 获取请求的MethodDetail
     *
     * @param requestPath 请求path 如 /user/xxx
     * @param httpMethod 请求的方法类型 如 get、post
     * @return MethodDetail
     */
    public static MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod){
        MethodDetail methodDetail = null;
        if (HttpMethod.GET.equals(httpMethod)){
            methodDetail = buildMethodDetail(requestPath, getMethodMapping, getUrlMapping);
        }
        if (HttpMethod.POST.equals(httpMethod)){
            methodDetail = buildMethodDetail(requestPath, postMethodMapping, postUrlMapping);
        }
        return methodDetail;
    }

    /**
     * 生成MethodDetail
     *
     * @param requestPath 请求路径path
     * @param getMethodMapping 存放方法的mapping映射 formatUrl:method
     * @param getUrlMapping formatUrl:url
     * @return MethodDetail
     */
    private static MethodDetail buildMethodDetail(String requestPath, Map<String, Method> getMethodMapping
            , Map<String, String> getUrlMapping){
        MethodDetail methodDetail = new MethodDetail();
        //遍历formatUrl与Method
        getMethodMapping.forEach((key, value) -> {
            //使用之前定义的正则，来判断是否是一个path
            Pattern pattern = Pattern.compile(key);
            if (pattern.matcher(requestPath).find()){
                methodDetail.setMethod(value);
                //获取到注解拼成到url
                String url = getUrlMapping.get(key);
                Map<String, String> urlParameterMappings = UrlUtil.getUrlParameterMappings(requestPath, url);
                methodDetail.setUrlParameterMappings(urlParameterMappings);
            }
        });
        return methodDetail;
    }
}
