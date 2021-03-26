package com.df.jsonboot.annotation.springmvc;

import com.df.jsonboot.annotation.ioc.Component;

import java.lang.annotation.*;

/**
 * 定义RestController注解
 * 本意为ResponseBody + Controller注解
 * 会有Controller的ioc容器范围以及ResponseBody的数据返回
 *
 * @author qinghuo
 * @since 2021/03/21 3:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface RestController {

    String value() default "";

}
