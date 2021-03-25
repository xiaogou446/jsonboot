package com.df.jsonboot;

import com.df.jsonboot.common.Banner;
import com.df.jsonboot.common.JsonBootBanner;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.server.HttpServer;

/**
 * jsonboot 的启动类
 *
 * @author qinghuo
 * @since 2021/03/17 2:50
 */
public class JsonBootApplication {
    public static void main(String[] args) {
        Banner banner = new JsonBootBanner();
        banner.printBanner(null, System.out);
        //初始化注解
        ApplicationContext context = ApplicationContext.getInstance();
        context.loadClass("com.df.demo");
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }
}
