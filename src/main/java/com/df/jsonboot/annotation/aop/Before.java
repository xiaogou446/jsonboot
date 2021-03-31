package com.df.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * aop 前置增强
 *
 * @author qinghuo
 * @since 2021/03/29 15:49
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Before {

    String value() default "";

}
