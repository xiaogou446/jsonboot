package com.df.jsonboot.core.context;

import com.df.jsonboot.annotation.boot.ComponentScan;
import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.common.Banner;
import com.df.jsonboot.common.JsonBootBanner;
import com.df.jsonboot.core.springmvc.factory.ClassFactory;
import com.df.jsonboot.core.springmvc.factory.RouteFactory;
import com.df.jsonboot.core.ioc.BeanFactory;
import com.df.jsonboot.core.ioc.DependencyInjection;
import com.df.jsonboot.server.HttpServer;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 将请求通过注解进行组合路由
 *
 * @author qinghuo
 * @since 2021/03/21 16:04
 */
public class ApplicationContext {

    /**
     * 生成饿汉式
     */
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();

    /**
     * 用于存放范围下注解扫描的类
     */
    public static final Map<Class<? extends Annotation>, Set<Class<?>>> CLASSES = new HashMap<>();

    private String[] packageNames;


    private ApplicationContext(){}

    public void run(Class<?> jsonBootClass){
        String[] packageNames = analysisPackageNames(jsonBootClass);
        setPackageName(packageNames);

        Banner banner = new JsonBootBanner();
        banner.printBanner(null, System.out);
        ClassFactory.loadClass();
        RouteFactory.loadRoutes();
        BeanFactory.loadBeans();
        //启动netty服务器
        HttpServer httpServer = new HttpServer();
        httpServer.run();
    }

    /**
     * 获取实例对象
     *
     * @return 唯一的ApplicationContext对象
     */
    public static ApplicationContext getInstance(){
        return APPLICATION_CONTEXT;
    }

    private static String[] analysisPackageNames(Class<?> jsonBootClass){
        ComponentScan componentScan = jsonBootClass.getAnnotation(ComponentScan.class);
        return Objects.isNull(componentScan) || StringUtils.isBlank(componentScan.value()[0])
                        ? new String[]{jsonBootClass.getPackage().getName()} : componentScan.value();
    }

    private void setPackageName(String[] packageNames){
        this.packageNames = packageNames;
    }

    public String[] getPackageNames(){
        return packageNames;
    }
}
