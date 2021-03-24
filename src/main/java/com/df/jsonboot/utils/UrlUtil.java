package com.df.jsonboot.utils;

import io.netty.handler.codec.http.QueryStringDecoder;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于处理url的工具类
 *
 * @author qinghuo
 * @since 2021/03/22 9:30
 */
public class UrlUtil {

    /**
     * 获取查询的参数列表
     *
     * @param uri get请求链接
     * @return 参数列表map
     */
    public static Map<String, String> getQueryParam(String uri){
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, List<String>> parameters = queryDecoder.parameters();
        Map<String, String> queryParam = new HashMap<>();
        for(Map.Entry<String, List<String>> parameter : parameters.entrySet()){
            for(String value : parameter.getValue()){
                queryParam.put(parameter.getKey(), value);
            }
        }
        System.out.println(queryParam);
        return queryParam;
    }

    /**
     * 将请求链接的uri转换为path
     *
     * @param uri 请求链接的uri
     * @return path路径
     */
    public static String convertUriToPath(String uri){
        QueryStringDecoder queryDecoder = new QueryStringDecoder(uri, Charsets.toCharset(CharEncoding.UTF_8));
        return queryDecoder.path();
    }

    /**
     * 定义替换{xx}为匹配中文 英文字母 下划线的正则
     * 如 /user/{age}  -> ^/user/[\u4e00-\u9fa5_a-zA-Z0-9]+/?$
     *
     * @param url 拼装路径
     * @return 能够匹配{xxx}的正则
     */
    public static String formatUrl(String url) {
        String originPattern = url.replaceAll("(\\{\\w+})", "[\\\\u4e00-\\\\u9fa5_a-zA-Z0-9]+");
        String pattern = "^" + originPattern + "/?$";
        return pattern.replaceAll("/+", "/");
    }

    /**
     * 获取参数的映射MAP 如/user/{name} 传入 /user/xxx 返回的是 user:user, name:xxx
     *
     * @param requestPath 外部传入的路径
     * @param url 注解上拼成的url路径
     * @return 映射map
     */
    public static Map<String, String> getUrlParameterMappings(String requestPath, String url){
        //外部进入的
        String[] paramArray = requestPath.split("/");
        String[] urlParamArray = url.split("/");
        Map<String, String> urlParameterMappings = new HashMap<>();
        for(int i = 0; i < urlParamArray.length; i++){
            urlParameterMappings.put(urlParamArray[i].replace("{", "").replace("}", ""), paramArray[i]);
        }
        return urlParameterMappings;
    }

}
