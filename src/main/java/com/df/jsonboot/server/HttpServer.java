package com.df.jsonboot.server;

import com.df.jsonboot.constant.SystemConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 通过netty编写http服务器接收请求
 *
 * @author qinghuo
 * @since 2021/03/19 15:20
 */
@Slf4j
public class HttpServer {

    /**
     * 需要使用的端口号
     */
    private int port = 8080;

    public HttpServer(){}

    public HttpServer(int port){
        this.port = port;
    }


    public void run(){
        //设置用于连接的老板组， 可在构造器中定义使用的线程数  监听端口接收客户端连接，一个端口一个线程，然后转给worker组
        //老板组用于监听客户端连接请求，有连接传入时就生成连接channel传给worker，等worker 接收请求 io多路复用，
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //设置用于工作的工作组，用于处理io操作，执行任务 这俩实际上是reactor线程池
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //定义服务启动的引导程序
            ServerBootstrap b = new ServerBootstrap();
            //将两个组都放入引导程序中
            b.group(bossGroup, workerGroup)
                    //定义使用的通道 可以选择是NIO或者是OIO 代表了worker在处理socket channel时的不同情况。oio只能1对1， nio则没有1对1对关系
                    //当netty要处理长连接时最好使用NIO，不然如果要保证效率 需要创建大量的线程，和io多路复用一致
                    .channel(NioServerSocketChannel.class)
                    //表示系统定义存放三次握手的最大临时队列长度 如果建立连接频繁可以调大这个参数
                    .option(ChannelOption.SO_BACKLOG, 128)
                    //xxx和childxxx的区别， xxx是对boss组起作用，而childxxx是对worker起作用。
                    //开启tcp底层心跳机制， 连接持续时间
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //boss组定义日志输出形式
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //定义特殊的程序处理channel 相当于可以为pipeline添加新的功能 在worker组的线程会通过这里
                    .childHandler(new ChannelInitializer<SocketChannel>(){
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            //需要添加的处理事件在这里添加
                                socketChannel.pipeline()
                                        .addLast("decoder", new HttpRequestDecoder())
                                        .addLast("encoder", new HttpResponseEncoder())
                                        //处理post请求需要
                                        .addLast("aggregator", new HttpObjectAggregator(512 * 1024))
                                        .addLast("handler", new HttpRequestHandler());
                        }
                    });
            //sync()会阻塞直到bind完成
            ChannelFuture f = b.bind(port).sync();
            log.info(SystemConstants.LOG_PORT_BANNER, this.port);
            //同步 直到channel server结束
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //关闭boss组和worker组
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }



}
