package com.df.jsonboot.server;

import com.df.jsonboot.core.springmvc.handler.RequestHandler;
import com.df.jsonboot.core.springmvc.handler.impl.GetRequestHandler;
import com.df.jsonboot.core.springmvc.handler.impl.PostRequestHandler;
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

    private static final AsciiString CONNECTION = AsciiString.cached("Connection");
    private static final AsciiString KEEP_ALIVE = AsciiString.cached("keep-alive");
    private static final Map<HttpMethod, RequestHandler> REQUEST_HANDLER_MAP;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        log.info("HttpRequestHandler complete");
        ctx.flush();
    }

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
        FullHttpResponse response;
        try{
            Object result = requestHandler.handler(request);
            response = HttpResponse.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            response = HttpResponse.internalServerError(uri);
        }
        //对获得的数据进行相应处理
        boolean keepAlive = HttpUtil.isKeepAlive(request);
        if (!keepAlive){
            //如果不是长链接，则写入数据后关闭此次channel连接
            ctx.write(response).addListener(ChannelFutureListener.CLOSE);
        }else{
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.write(response);
        }
    }


}
