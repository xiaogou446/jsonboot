package com.df.jsonboot.core.handler.impl;

import com.df.jsonboot.annotation.RequestBody;
import com.df.jsonboot.annotation.RequestParam;
import com.df.jsonboot.core.handler.RequestHandler;
import com.df.jsonboot.core.route.Route;
import com.df.jsonboot.utils.ObjectUtil;
import com.df.jsonboot.utils.ReflectionUtil;
import com.df.jsonboot.utils.UrlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
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

    /**
     * 转换格式
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object handler(FullHttpRequest fullHttpRequest) {
        String uri = fullHttpRequest.uri();
        //post请求也有可能在uri上使用参数
        Map<String, String> queryParamMap = UrlUtil.getQueryParam(uri);
        String path = UrlUtil.convertUriToPath(uri);
        Method method = Route.postMethodMapping.get(path);
        if (method == null){
            return null;
        }
        Parameter[] parameters = method.getParameters();

        String contentTypeStr = fullHttpRequest.headers().get(CONTENT_TYPE);

        String contentType = contentTypeStr.split(";")[0];
        if (StringUtils.isBlank(contentType)){
            return null;
        }
        //设置参数值
        List<Object> params = new ArrayList<>();
        //设置传入的参数实体
        String jsonContent = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));

        //如果是json格式
        if (StringUtils.equals(APPLICATION_JSON, contentType)){
            for(Parameter parameter : parameters){
                Object result = null;
                RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
                //代表当前参数上有requestBody注解
                if (requestBody != null){
                    try {
                        result = objectMapper.readValue(jsonContent, parameter.getType());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } else {
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
                    result = ObjectUtil.convertToClass(type, paramValue);
                }
                params.add(result);
            }
        }

        return ReflectionUtil.executeMethod(method, params.toArray());
    }

}
