package com.df.jsonboot.core.springmvc.handler;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 处理请求的上层接口
 *
 * @author qinghuo
 * @since 2021/03/21 9:52
 */
public interface RequestHandler {

    /**
     * 处理请求的接口
     *
     * @param fullHttpRequest 传入需要处理的请求信息
     * @return 返回数据
     */
    Object handler(FullHttpRequest fullHttpRequest);

}
