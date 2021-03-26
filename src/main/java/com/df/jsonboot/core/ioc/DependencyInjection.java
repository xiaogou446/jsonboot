package com.df.jsonboot.core.ioc;

import com.df.jsonboot.annotation.Autowired;
import com.df.jsonboot.annotation.Qualifier;
import com.df.jsonboot.exception.InterfaceNotExistsImplementException;
import com.df.jsonboot.exception.NotFountTargetBeanException;
import com.df.jsonboot.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * 依赖注入
 *
 * @author qinghuo
 * @since 2021/03/25 14:41
 */
public class DependencyInjection {

    /**
     * 执行依赖注入 @Autowired
     */
    public static void loadDependency(String packageName){
        Map<String, Object> beanMap = BeanFactory.BEANS;
        //遍历每个bean
        beanMap.forEach((beanName, bean) -> {
            for (Field field : bean.getClass().getDeclaredFields()){
                if (field.isAnnotationPresent(Autowired.class)){
                    Class<?> fieldTypeClass = field.getType();
                    String className = fieldTypeClass.getName();
                    //判断是否是接口 如果是接口那存着的就是接口信息，需要顺延找到实现类
                    if (fieldTypeClass.isInterface()){
                        Set<Class<?>> subClass = ReflectionUtil.getSubClass(packageName, fieldTypeClass);
                        if (subClass.size() == 0){
                            throw new InterfaceNotExistsImplementException("接口的实现类不存在");
                        }else if (subClass.size() == 1){
                            //实现类的名字肯定在ioc容器中，目前只考虑单个实现累的情况，如果是多个需要用@Qulifer
                            className = subClass.iterator().next().getName();
                        }else{
                            Qualifier qualifier = field.getDeclaredAnnotation(Qualifier.class);
                            className = StringUtils.isBlank(qualifier.value()) ? beanName : qualifier.value();
                        }
                    }
                    //根据类型的类名获取
                    Object targetBean = beanMap.get(className);
                    if (targetBean == null){
                        throw new NotFountTargetBeanException("该依赖没有找到目标类：" + field.toString());
                    }
                    ReflectionUtil.setReflectionField(bean, field, targetBean);
                }
            }
        });
    }


}
