package com.df.jsonboot.annotation.boot;

import java.lang.annotation.*;

/**
 * 扫描类，扫描范围内的注解加入ioc容器
 *
 * @author qinghuo
 * @since 2021/03/31 16:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {
    String[] value() default {};
}
