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

    /**
     * jsonboot启动方法
     *
     * @param jsonBootClass 目标启动类
     * @param args 执行参数
     */
    public static void run(Class<?> jsonBootClass, String... args){
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        applicationContext.run(jsonBootClass);
    }
}
