package com.df.jsonboot.core.ioc;

import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.annotation.springmvc.RestController;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.exception.NotFountTargetBeanException;
import com.df.jsonboot.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存放对象的bean工厂
 *
 * @author qinghuo
 * @since 2021/03/25 11:43
 */
public class BeanFactory {

    /**
     * 设置存放Bean的map 一级缓存
     */
    public static final Map<String, Object> BEANS = new ConcurrentHashMap<>(128);

    /**
     * 存放暂时的aop对象 二级缓存
     */
    public static final Map<String, Object> EARLY_BEANS = new HashMap<>(16);

    /**
     * 存放实例化并未初始化的基础对象 三级缓存
     */
    public static final Map<String, Object> BASIC_OBJECTS = new HashMap<>(16);

    /**
     * 记录当前正在实例化的对象名
     */
    public static final List<String> CURRENT_IN_CREATION = new LinkedList<>();
    /**
     * 加载bean 根据类生成对应的bean存入BEANS中
     */
    public static void loadBeans(){
        Map<Class<? extends Annotation>, Set<Class<?>>> classes = ApplicationContext.CLASSES;
        classes.forEach((key, value) -> {
                for (Class<?> aClass : value){
                    String beanName = aClass.getName();
                    if (key == Component.class){
                        Component component = aClass.getAnnotation(Component.class);
                        beanName = StringUtils.isBlank(component.value()) ? beanName : component.value();
                    }
                    buildBeans(beanName, aClass);
                }
        });
    }

    /**
     * 从三级缓存中获取类的实例
     *
     * @param beanName 定义的beanName，可以是Component上的标记，也可以是@Autowired，Qualifier上的别名
     * @param aClass 实例化的类
     */
    public static Object buildBeans(String beanName, Class<?> aClass){
        if (aClass.isInterface()){
            throw new NotFountTargetBeanException("该依赖没有找到目标类：" + beanName);
        }
        //先从一级缓存中获取bean
        Object bean = BEANS.get(beanName);
        if (bean != null){
            return bean;
        }
        //再从二三级缓存中获取bean  解决循环依赖
        if ( (bean = EARLY_BEANS.get(beanName)) == null && (bean = BASIC_OBJECTS.get(beanName)) == null){
            bean = ReflectionUtil.newInstance(aClass);
            //
            BASIC_OBJECTS.put(beanName, bean);
            //将bean的名称存在正在创建的列表中
            CURRENT_IN_CREATION.add(beanName);
        }else if (CURRENT_IN_CREATION.contains(beanName)){
            //判断二三级缓存中的对象是否已经是代理对象

            //判断是否可以生成代理对象

            //如果是代理生成代理 返回未完成的bean 解决循环依赖
            return bean;
        }
        //如果在二三级缓存中存在 却没有在加载流程中，则继续完成加载。

        //进行依赖注入
        DependencyInjection.injectionDependency(bean);
        //进行aop 判断是否需要aop 判断二级缓存中是否已经有代理对象

        //存入一级缓存，删除二三级缓存
        BEANS.put(beanName, bean);
        EARLY_BEANS.remove(beanName);
        BASIC_OBJECTS.remove(beanName);
        CURRENT_IN_CREATION.remove(beanName);
        return bean;
    }

}
