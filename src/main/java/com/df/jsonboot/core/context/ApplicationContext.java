package com.df.jsonboot.core.context;

import com.df.jsonboot.annotation.GetMapping;
import com.df.jsonboot.annotation.PostMapping;
import com.df.jsonboot.annotation.RestController;
import com.df.jsonboot.core.scanners.AnnotatedClassScanner;
import com.df.jsonboot.entity.MethodDetail;
import com.df.jsonboot.utils.UrlUtil;
import io.netty.handler.codec.http.HttpMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

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
     * 类路径映射
     */
    public static Map<String, Class<?>> classMapping = new HashMap<>();

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
     * Get方法 存放getFormatUrl : MethodDetail
     */
    public static Map<String, MethodDetail> getMethodDetailMapping = new HashMap<>();

    /**
     * Post方法 存放postFormatUrl : MethodDetail
     */
    public static Map<String, MethodDetail> postMethodDetailMapping = new HashMap<>();

    private ApplicationContext(){}

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

    /**
     * 获取请求的MethodDetail
     *
     * @param requestPath 请求path 如 /user/xxx
     * @param httpMethod 请求的方法类型 如 get、post
     * @return MethodDetail
     */
    public MethodDetail getMethodDetail(String requestPath, HttpMethod httpMethod){
        MethodDetail methodDetail = null;
        if (HttpMethod.GET.equals(httpMethod)){
            methodDetail = buildMethodDetail(requestPath, getMethodMapping, getUrlMapping, getMethodDetailMapping);
        }
        if (HttpMethod.POST.equals(httpMethod)){
            methodDetail = buildMethodDetail(requestPath, postMethodMapping, postUrlMapping, postMethodDetailMapping);
        }
        return methodDetail;
    }

    /**
     * 生成MethodDetail
     *
     * @param requestPath 请求路径path
     * @param getMethodMapping 存放方法的mapping映射 formatUrl:method
     * @param getUrlMapping formatUrl:url
     * @param getMethodDetailMapping formatUrl:methodDetail
     * @return MethodDetail
     */
    private MethodDetail buildMethodDetail(String requestPath, Map<String, Method> getMethodMapping
                                                , Map<String, String> getUrlMapping, Map<String, MethodDetail> getMethodDetailMapping){
        MethodDetail methodDetail = new MethodDetail();
        //遍历formatUrl与Method
        getMethodMapping.forEach((key, value) -> {
            //使用之前定义的正则，来判断是否是一个path
            Pattern pattern = Pattern.compile(key);
            if (pattern.matcher(requestPath).find()){
                methodDetail.setMethod(value);
                //获取到注解拼成到url
                String url = getUrlMapping.get(key);
                Map<String, String> urlParameterMappings = UrlUtil.getUrlParameterMappings(requestPath, url);
                methodDetail.setUrlParameterMappings(urlParameterMappings);
                getMethodDetailMapping.put(key, methodDetail);
            }
        });
        return methodDetail;
    }

    /**
     * 获取实例对象
     *
     * @return 唯一的ApplicationContext对象
     */
    public static ApplicationContext getInstance(){
        return APPLICATION_CONTEXT;
    }

}
