package com.df.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * aop标识 设置切片
 *
 * @author qinghuo
 * @since 2021/03/29 15:39
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Aspect {

    String value() default "";

}
