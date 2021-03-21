package com.df.jsonboot.server;

import com.df.jsonboot.core.handler.RequestHandler;
import com.df.jsonboot.core.handler.impl.GetRequestHandler;
import com.df.jsonboot.core.handler.impl.PostRequestHandler;
import com.df.jsonboot.serialize.impl.JacksonSerializer;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AsciiString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于处理netty服务器中的http request请求
 *
 * @author qinghuo
 * @since 2021/03/21 9:41
 */
@Slf4j
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * 定义允许使用的类型map
     */
    private static final Map<HttpMethod, RequestHandler> REQUEST_HANDLER_MAP;
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("HttpRequestHandler complete");
        ctx.flush();
    }

    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    private static final String APPLICATION_JSON = "application/json";

    static {
        REQUEST_HANDLER_MAP = new HashMap<>();
        REQUEST_HANDLER_MAP.put(HttpMethod.GET, new GetRequestHandler());
        REQUEST_HANDLER_MAP.put(HttpMethod.POST, new PostRequestHandler());
    }

    /**
     * 图标定义
     */
    private static final String FAVICON_ICON = "/favicon.ico";


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) {
        String uri = request.uri();
        //如果是访问图标的请求或者为空，直接返回
        if (StringUtils.isBlank(uri) || StringUtils.equals(FAVICON_ICON, uri)){
            return;
        }
        //根据请求的类型在map中取出对应的处理器
        RequestHandler requestHandler = REQUEST_HANDLER_MAP.get(request.method());
        Object result = requestHandler.handler(request);
        //对获得的数据进行相应处理
        FullHttpResponse response = buildHttpResponse(result);
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        if (!keepAlive){
            //如果不是长链接，则写入数据后关闭此次channel连接
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }else{
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }



    /**
     * 对请求处理的结果进行一个封装
     *
     * @param result 请求处理后得到的数据结果
     * @return 封装好的响应
     */
    private FullHttpResponse buildHttpResponse(Object result){
        JacksonSerializer jacksonSerializer = new JacksonSerializer();
        byte[] bytes = jacksonSerializer.serialize(result);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
        response.headers().set(CONTENT_TYPE, APPLICATION_JSON);
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

}
