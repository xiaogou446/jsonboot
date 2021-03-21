package com.df.jsonboot.annotation;

import java.lang.annotation.*;

/**
 * 会对方法和类产生效果
 * 会接收GET请求
 *
 * @author qinghuo
 * @since 2021/03/21 14:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface GetMapping {

    String value() default "";

}
