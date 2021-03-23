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


}
