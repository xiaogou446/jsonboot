package com.df.jsonboot.core.handler.impl;

import com.df.jsonboot.core.handler.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;

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
     * 转换格式
     */
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Object handler(FullHttpRequest fullHttpRequest) {
        Object result = null;
        String contentTypeStr = fullHttpRequest.headers().get(CONTENT_TYPE);
        if (StringUtils.isBlank(contentTypeStr)){
            return result;
        }
        String contentType = contentTypeStr.split(";")[0];
        if (StringUtils.equals(APPLICATION_JSON, contentType)){
            String jsonContent = fullHttpRequest.content().toString(Charsets.toCharset(CharEncoding.UTF_8));
            try {
                result = objectMapper.readValue(jsonContent, Object.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
