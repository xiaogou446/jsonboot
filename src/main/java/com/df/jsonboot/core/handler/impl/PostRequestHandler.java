package com.df.jsonboot.core.handler.impl;

import com.df.jsonboot.core.handler.RequestHandler;
import com.df.jsonboot.core.resolver.ParameterResolver;
import com.df.jsonboot.core.resolver.factory.ParameterResolverFactory;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.ReflectionUtil;
import com.df.jsonboot.utils.UrlUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理http post的请求
 *
 * @author qinghuo
 * @since 2021/03/21 9:56
 */
public class PostRequestHandler implements RequestHandler {

    /**
     * 代表http内容类型格式
     */
    private static final String CONTENT_TYPE = "Content-Type";

    /**
     * json的内容格式
     */
    private static final String APPLICATION_JSON = "application/json";

    /**
     * 表单的内容格式  之后补充
     */
    private static final String FORM_URLENCODED= "application/x-www-form-urlencoded";


    @Override
    public Object handler(FullHttpRequest fullHttpRequest) {
        String uri = fullHttpRequest.uri();
        //post请求也有可能在uri上使用参数
        Map<String, String> queryParamMap = UrlUtil.getQueryParam(uri);
        String path = UrlUtil.convertUriToPath(uri);
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        MethodDetail methodDetail = applicationContext.getMethodDetail(path, HttpMethod.POST);
        if (methodDetail == null || methodDetail.getMethod() == null){
            return null;
        }
        methodDetail.setQueryParameterMappings(queryParamMap);
        Parameter[] parameters = methodDetail.getMethod().getParameters();
        String contentTypeStr = fullHttpRequest.headers().get(CONTENT_TYPE);
        String contentType = contentTypeStr.split(";")[0];
        if (StringUtils.isBlank(contentType)){
            return null;
        }
        //设置参数值
        List<Object> params = new ArrayList<>();
        //设置传入的参数实体
        String jsonContent = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
        methodDetail.setJson(jsonContent);
        //如果是json格式  改造结构 在handler下设置annotation包 用作处理
        if (StringUtils.equals(APPLICATION_JSON, contentType)){
            for(Parameter parameter : parameters){
                ParameterResolver parameterResolver = ParameterResolverFactory.get(parameter);
                Object result = parameterResolver.resolve(methodDetail, parameter);
                params.add(result);
            }
        }
        return ReflectionUtil.executeMethod(methodDetail.getMethod(), params.toArray());
    }

}
