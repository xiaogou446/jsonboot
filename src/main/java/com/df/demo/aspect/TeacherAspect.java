package com.df.demo.aspect;

import com.df.jsonboot.annotation.aop.After;
import com.df.jsonboot.annotation.aop.Aspect;
import com.df.jsonboot.annotation.aop.Before;
import com.df.jsonboot.annotation.aop.Pointcut;
import com.df.jsonboot.core.aop.lang.JoinPoint;

@Aspect
public class TeacherAspect {

    @Pointcut(value = "com.df.demo.TeacherServiceImpl*")
    public void aspect(){

    }

    @Before
    public void beforeAction(JoinPoint joinPoint) {
        System.out.println("aspect teacher before to do something");
    }

    @After
    public void afterAction(Object result, JoinPoint joinPoint) {
        System.out.println(result);
        System.out.println("aspect teacher after to do something");
    }

}
