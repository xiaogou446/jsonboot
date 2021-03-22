package com.df.jsonboot.utils;

import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于处理url的工具类
 *
 * @author qinghuo
 * @since 2021/03/22 9:30
 */
public class UrlUtils {

    /**
     * 获取查询的参数列表
     *
     * @param queryDecoder 查询实体
     * @return 参数列表map
     */
    public static Map<String, String> getQueryParam(QueryStringDecoder queryDecoder){
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


}
