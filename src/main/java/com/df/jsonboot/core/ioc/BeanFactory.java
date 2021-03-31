package com.df.jsonboot.core.ioc;

import com.df.jsonboot.annotation.aop.Aspect;
import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.annotation.springmvc.RestController;
import com.df.jsonboot.core.aop.factory.BeanPostProcessorFactory;
import com.df.jsonboot.core.aop.process.BeanPostProcessor;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.exception.NotFountTargetBeanException;
import com.df.jsonboot.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.DiffBuilder;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
     * 存放 @Aspect 注解记录的类型
     */
    private static final Map<String, String[]> BEAN_TYPE_NAME_MAP = new ConcurrentHashMap<>(128);

    /**
     * 加载bean 根据类生成对应的bean存入BEANS中
     */
    public static void loadBeans(){
        Map<Class<? extends Annotation>, Set<Class<?>>> classes = ApplicationContext.CLASSES;
        //优先对aspect进行实例化
        Set<Class<?>> aspectSet = classes.get(Aspect.class);
        BEAN_TYPE_NAME_MAP.put(Aspect.class.getName(),
                aspectSet.stream().map(Class::getName).toArray(size -> new String[size]));
        for (Class<?> aspectClass : aspectSet){
            buildBeans(aspectClass.getName(), aspectClass);
        }
        classes.forEach((key, value) -> {
            if (key == Aspect.class){
                return;
            }
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
            //循环依赖 这时候进入的不是二级缓存中获取的就是三级缓存中获取的对象
            return getProxyBean(beanName, bean);
        }
        //如果在二三级缓存中存在 却没有在加载流程中，则继续完成加载。
        //进行依赖注入
        DependencyInjection.injectionDependency(bean);
        //进行aop 判断是否需要aop 判断二级缓存中是否已经有代理对象
        bean = getProxyBean(beanName, bean);
        //存入一级缓存，删除二三级缓存
        BEANS.put(beanName, bean);
        EARLY_BEANS.remove(beanName);
        BASIC_OBJECTS.remove(beanName);
        CURRENT_IN_CREATION.remove(beanName);
        return bean;
    }

    /**
     * 根据名称获取对象
     * @param name bean的名称
     * @return 实例对象
     */
    public static Set<Object> getBeansByName(String name){
        Set<Object> result = new HashSet<>();
        String[] beanNames = BEAN_TYPE_NAME_MAP.get(name);
        if (Objects.isNull(beanNames)){
            return result;
        }
        for (String beanName : beanNames){
            Object bean = BEANS.get(beanName);
            if (!Objects.isNull(bean)){
                result.add(bean);
            }
        }
        return result;
    }

    /**
     * 获取代理bean
     *
     * @param beanName 目标bean的名称
     * @param bean 目标bean
     * @return 获取bean 或者生成代理bean
     */
    private static Object getProxyBean(String beanName, Object bean){
        //判断二级缓存中是否存在对象，如果存在代表该对象已经校验过是否生成代理，直接返回对象
        if (!Objects.isNull(EARLY_BEANS.get(beanName))){
            return bean;
        }
        //判断是否可以生成代理对象 这里的bean一定是三级缓存中没有判断代理的bean 如果满足aop却不是代理则生成代理返回
        BeanPostProcessor beanPostProcessor = BeanPostProcessorFactory.getBeanPostProcessor(bean);
        bean = beanPostProcessor.postProcessAfterInitialization(bean);
        //将生成的bean存到二级缓存中，如果需要代理则存入的是代理bean， 如果不需要，则是正常bean
        EARLY_BEANS.put(beanName, bean);
        return bean;
    }
}
