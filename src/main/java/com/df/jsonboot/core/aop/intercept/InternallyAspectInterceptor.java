package com.df.jsonboot.core.aop.intercept;

import com.df.jsonboot.annotation.aop.After;
import com.df.jsonboot.annotation.aop.Before;
import com.df.jsonboot.annotation.aop.Pointcut;
import com.df.jsonboot.core.aop.lang.JoinPoint;
import com.df.jsonboot.core.aop.lang.JoinPointImpl;
import com.df.jsonboot.entity.MethodInvocation;
import com.df.jsonboot.utils.PatternMatchUtil;
import com.df.jsonboot.utils.ReflectionUtil;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * aop实现
 *
 * @author qinghuo
 * @since 2021/03/30 13:33
 */
public class InternallyAspectInterceptor extends Interceptor{

    /**
     * 切面bean
     */
    private final Object aspectBean;

    /**
     * 切入点set
     */
    private final HashSet<String> pointCutUrls = new HashSet<>();

    /**
     * 前置方法
     */
    private final List<Method> beforeMethod = new ArrayList<>();

    /**
     * 后置方法
     */
    private final List<Method> afterMethod = new ArrayList<>();


    public InternallyAspectInterceptor(Object aspectBean){
        this.aspectBean = aspectBean;
        init();
    }

    /**
     * 初始化方法, 将方法进行分类方法
     */
    private void init(){
        for (Method method : aspectBean.getClass().getMethods()){
            Pointcut pointcut = method.getAnnotation(Pointcut.class);
            if (!Objects.isNull(pointcut) && StringUtils.isNotBlank(pointcut.value())){
                pointCutUrls.add(pointcut.value());
            }
            Before before = method.getAnnotation(Before.class);
            if (!Objects.isNull(before)){
                beforeMethod.add(method);
            }
            After after = method.getAnnotation(After.class);
            if (!Objects.isNull(after)){
                afterMethod.add(method);
            }
        }
    }


    @Override
    public boolean supports(Object bean) {
        return pointCutUrls.stream().anyMatch(url -> PatternMatchUtil.simpleMatch(url, bean.getClass().getName())) && (!beforeMethod.isEmpty() || !afterMethod.isEmpty());
    }

    @Override
    public Object intercept(MethodInvocation methodInvocation) {
        JoinPoint joinPoint = new JoinPointImpl(aspectBean, methodInvocation.getTargetObject(), methodInvocation.getArgs());
        beforeMethod.forEach(method -> {
            //与调用的方法参数要一致
            ReflectionUtil.executeMethodNoResult(aspectBean, method, joinPoint);
        });
        Object result = methodInvocation.proceed();
        afterMethod.forEach(method -> {
            ReflectionUtil.executeMethodNoResult(aspectBean, method, result, joinPoint);
        });
        return result;
    }
}
