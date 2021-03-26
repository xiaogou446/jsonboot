package com.df.jsonboot.annotation.springmvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * postMapping 接收post请求
 *
 * @author qinghuo
 * @since 2021/03/21 16:36
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PostMapping {
    String value() default "";
}
