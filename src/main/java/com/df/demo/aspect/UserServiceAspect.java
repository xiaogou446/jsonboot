package com.df.demo.aspect;

import com.df.jsonboot.annotation.aop.After;
import com.df.jsonboot.annotation.aop.Aspect;
import com.df.jsonboot.annotation.aop.Before;
import com.df.jsonboot.annotation.aop.Pointcut;
import com.df.jsonboot.core.aop.lang.JoinPoint;

@Aspect
public class UserServiceAspect {

    @Pointcut(value = "com.df.demo.UserService*")
    public void  userAspect(){

    }

    @Before
    public void before(JoinPoint joinPoint){
        System.out.println("这是userService的前置拦截器");
    }

    @After
    public void after(Object result, JoinPoint joinPoint){
        System.out.println("这是userService的后置拦截器");
    }

}
