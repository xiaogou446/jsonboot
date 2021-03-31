package com.df.jsonboot.core.ioc;

import com.df.jsonboot.annotation.ioc.Autowired;
import com.df.jsonboot.annotation.ioc.Component;
import com.df.jsonboot.annotation.ioc.Qualifier;
import com.df.jsonboot.core.context.ApplicationContext;
import com.df.jsonboot.exception.InterfaceNotExistsImplementException;
import com.df.jsonboot.exception.NotFountTargetBeanException;
import com.df.jsonboot.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * 依赖注入
 *
 * @author qinghuo
 * @since 2021/03/25 14:41
 */
public class DependencyInjection {

    /**
     * 对目标bean进行依赖注入
     *
     * @param bean 进行依赖注入的bean
     */
    public static void injectionDependency(Object bean){
        if (bean == null){
            return;
        }
        //遍历该类中每个注入的参数
        for (Field field : bean.getClass().getDeclaredFields()){
            //判断参数上是否有@Autowired注解
            if (field.isAnnotationPresent(Autowired.class)){
                Class<?> fieldTypeClass = field.getType();
                String beanName = fieldTypeClass.getName();
                beanName  = ReflectionUtil.getComponentValue(fieldTypeClass, Component.class, beanName);
                //判断是否是接口 如果是接口那存着的就是接口信息，需要顺延找到实现类
                if (fieldTypeClass.isInterface()){
                    @SuppressWarnings("unchecked")
                    Set<Class<?>> subClass = ReflectionUtil.getSubClass(ApplicationContext.getInstance().getPackageNames(), (Class<Object>)fieldTypeClass);
                    if (subClass.size() == 0){
                        throw new InterfaceNotExistsImplementException("接口的实现类不存在");
                    }else if (subClass.size() == 1){
                        Class<?> aClass = subClass.iterator().next();
                        fieldTypeClass = aClass;
                        //实现类的名字肯定在ioc容器中，目前只考虑单个实现累的情况，如果是多个需要用@Qulifer
                        beanName  = ReflectionUtil.getComponentValue(aClass, Component.class, aClass.getName());
                    }else{
                        //有两个实现类以上，通过qualifier进行判断指定目标类
                        Qualifier qualifier = field.getDeclaredAnnotation(Qualifier.class);
                        if (qualifier == null || StringUtils.isBlank(qualifier.value())){
                            throw new NotFountTargetBeanException("该依赖没有找到目标类：" + field.toString());
                        }

                        for (Class<?> aClass : subClass){
                            beanName  = ReflectionUtil.getComponentValue(aClass, Component.class, aClass.getName());
                            if (beanName.equals(qualifier.value())){
                                fieldTypeClass = aClass;
                                beanName = qualifier.value();
                                break;
                            }
                        }
                    }
                }
                //根据类型的类名获取
                Object targetBean = BeanFactory.buildBeans(beanName, fieldTypeClass);
                if (targetBean == null){
                    throw new NotFountTargetBeanException("该依赖没有找到目标类：" + field.toString());
                }
                ReflectionUtil.setReflectionField(bean, field, targetBean);
            }
        }
    }


}
