package com.df.jsonboot.core.handler.impl;

import com.df.jsonboot.core.handler.RequestHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.Charsets;

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

        QueryStringDecoder queryDecoder = new QueryStringDecoder(fullHttpRequest.uri(), Charsets.toCharset(CharEncoding.UTF_8));
        Map<String, List<String>> parameters = queryDecoder.parameters();
        //暂时打印参数 先完成netty再处理后续代码
        for (Map.Entry<String, List<String>> parameter : parameters.entrySet()){
            log.info(parameter.getKey() + " = " + parameter.getValue());
        }
        return null;
    }

}
