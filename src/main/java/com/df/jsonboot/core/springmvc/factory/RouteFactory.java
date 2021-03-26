package com.df.jsonboot.core.springmvc.factory;

import com.df.jsonboot.annotation.springmvc.GetMapping;
import com.df.jsonboot.annotation.springmvc.PostMapping;
import com.df.jsonboot.annotation.springmvc.RestController;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.utils.UrlUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 组装请求路由
 *
 * @author qinghuo
 * @since 2021/03/25 10:05
 */
public class RouteFactory {

    /**
     * Get方法映射路径 用于@GetMapping路由  存放getFormatUrl : method
     */
    public static Map<String, Method> getMethodMapping = new HashMap<>();

    /**
     * Post方法映射 用于@PostMapping路由   存放postFormatUrl : method
     */
    public static Map<String, Method> postMethodMapping = new HashMap<>();

    /**
     * Get方法 存放getFormatUrl : 原url
     */
    public static Map<String, String> getUrlMapping = new HashMap<>();

    /**
     * Post方法 存放postFormatUrl : 原url
     */
    public static Map<String, String> postUrlMapping = new HashMap<>();

    /**
     * 根据注解进行访问路径的拼接
     *
     */
    public static void loadRoutes(){
        Set<Class<?>> scan = ApplicationContext.CLASSES.get(RestController.class);
        for (Class<?> aClass : scan){
            // 从扫描的类中获取该注解信息
            RestController restController = aClass.getAnnotation(RestController.class);
            String baseUri = restController.value();
            Method[] methods = aClass.getMethods();
            //获取方法映射
            loadMethodRoutes(baseUri, methods);
        }
        System.out.println(getMethodMapping);
        System.out.println(postMethodMapping);
    }

    /**
     * 添加方法注解的路径映射
     *
     * @param baseUri 从类注解中解析的基础uri
     * @param methods 该类中的方法
     */
    private static void loadMethodRoutes(String baseUri, Method[] methods){
        for (Method method : methods){
            if (method.isAnnotationPresent(GetMapping.class)){
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                String url = baseUri + getMapping.value();
                String formatUrl = UrlUtil.formatUrl(url);
                getMethodMapping.put(formatUrl, method);
                getUrlMapping.put(formatUrl, url);
                //如果有getMapping在上面了，则不进行postMapping的使用
                continue;
            }
            if (method.isAnnotationPresent(PostMapping.class)){
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                String url = baseUri + postMapping.value();
                String formatUrl = UrlUtil.formatUrl(url);
                postMethodMapping.put(formatUrl, method);
                postUrlMapping.put(formatUrl, url);
            }
        }
    }

}
