package com.df.jsonboot;

import com.df.jsonboot.common.Banner;
import com.df.jsonboot.common.JsonBootBanner;
import com.df.jsonboot.core.route.Route;
import com.df.jsonboot.server.HttpServer;

import java.io.PrintStream;

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
        Route route = new Route();
        route.loadRoutes("com.df.demo");
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }
}
