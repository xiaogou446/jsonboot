package com.df.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * aop 切入点
 *
 * @author qinghuo
 * @since 2021/03/29 15:47
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Pointcut {

    String value() default "";

}
