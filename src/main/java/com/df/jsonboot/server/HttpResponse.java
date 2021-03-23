package com.df.jsonboot.server;

import com.df.jsonboot.entity.ErrorResponse;
import com.df.jsonboot.serialize.impl.JacksonSerializer;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AsciiString;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

/**
 * 响应配置类
 *
 * @author qinghuo
 * @since 2021/03/22 15:42
 */
public class HttpResponse {

    private static final AsciiString CONTENT_LENGTH = AsciiString.cached("Content-Length");
    private static final AsciiString CONTENT_TYPE = AsciiString.cached("Content-Type");
    private static final String APPLICATION_JSON = "application/json";
    private static final JacksonSerializer JACKSON_SERIALIZER = new JacksonSerializer();

    /**
     * 处理成功时返回的数据封装响应
     *
     * @param object 成功时返回的数据
     * @return 封装的响应
     */
    public static FullHttpResponse ok(Object object){
        byte[] bytes = JACKSON_SERIALIZER.serialize(object);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(bytes));
        response.headers().set(CONTENT_TYPE, APPLICATION_JSON);
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    /**
     * 当请求处理失败时，生成失败响应
     * @param uri 请求路径
     * @return 响应
     */
    public static FullHttpResponse internalServerError(String uri){
        ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR.code(), INTERNAL_SERVER_ERROR.reasonPhrase(), uri);
        byte[] bytes = JACKSON_SERIALIZER.serialize(errorResponse);
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, INTERNAL_SERVER_ERROR, Unpooled.wrappedBuffer(bytes));
        response.headers().set(CONTENT_TYPE, "application/text");
        response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

}
