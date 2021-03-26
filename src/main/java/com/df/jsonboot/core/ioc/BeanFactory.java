package com.df.jsonboot.core.ioc;

import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.annotation.springmvc.RestController;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存放对象的bean工厂
 *
 * @author qinghuo
 * @since 2021/03/25 11:43
 */
public class BeanFactory {

    /**
     * 设置存放Bean的map
     */
    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);

    /**
     * 加载bean 根据类生成对应的bean存入BEANS中
     */
    public static void loadBeans(){
        Map<Class<? extends Annotation>, Set<Class<?>>> classes = ApplicationContext.CLASSES;
        classes.forEach((key, value) -> {
            if (key == Component.class){
                for (Class<?> aClass : value){
                    Component component = aClass.getAnnotation(Component.class);
                    String beanName = StringUtils.isBlank(component.value()) ? aClass.getName() : component.value();
                    Object instance = ReflectionUtil.newInstance(aClass);
                    BEANS.put(beanName, instance);
                }
            }

            if (key == RestController.class){
                for (Class<?> aClass : value){
                    Object instance = ReflectionUtil.newInstance(aClass);
                    BEANS.put(aClass.getName(), instance);
                }
            }
        });


    }

}
