package com.df.jsonboot.core.route;

import com.df.jsonboot.annotation.GetMapping;
import com.df.jsonboot.annotation.PostMapping;
import com.df.jsonboot.annotation.RestController;
import com.df.jsonboot.core.scanners.AnnotatedClassScanner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 将请求通过注解进行组合路由
 *
 * @author qinghuo
 * @since 2021/03/21 16:04
 */
public class Route {

    /**
     * 类路径映射
     */
    public static Map<String, Class<?>> classMapping = new HashMap<>();

    /**
     * Get方法映射路径
     */
    public static Map<String, Method> getMethodMapping = new HashMap<>();

    /**
     * Post方法映射
     */
    public static Map<String, Method> postMethodMapping = new HashMap<>();

    /**
     * 根据注解进行访问路径的拼接
     *
     * @param packageName 需要进行扫描的包名
     */
    public void loadRoutes(String packageName){
        AnnotatedClassScanner annotatedScanner = new AnnotatedClassScanner();
        Set<Class<?>> scan = annotatedScanner.scan(packageName, RestController.class);
        for (Class<?> aClass : scan){
            // 从扫描的类中获取该注解信息
            RestController restController = aClass.getAnnotation(RestController.class);
            String baseUri = restController.value();
            Method[] methods = aClass.getMethods();
            //获取方法映射
            loadMethodRoutes(baseUri, methods);
            classMapping.put(baseUri, aClass);
        }
        System.out.println(classMapping);
        System.out.println(getMethodMapping);
        System.out.println(postMethodMapping);
    }

    /**
     * 添加方法注解的路径映射
     *
     * @param baseUri 从类注解中解析的基础uri
     * @param methods 该类中的方法
     */
    private void loadMethodRoutes(String baseUri, Method[] methods){
        for (Method method : methods){
            if (method.isAnnotationPresent(GetMapping.class)){
                GetMapping getMapping = method.getAnnotation(GetMapping.class);
                getMethodMapping.put(baseUri + getMapping.value(), method);
                //如果有getMapping在上面了，则不进行postMapping的使用
                continue;
            }
            if (method.isAnnotationPresent(PostMapping.class)){
                PostMapping postMapping = method.getAnnotation(PostMapping.class);
                postMethodMapping.put(baseUri + postMapping.value(), method);
            }
        }
    }

}
