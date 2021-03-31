package com.df.jsonboot.annotation.aop;

import java.lang.annotation.*;

/**
 * aop 后置增强
 *
 * @author qinghuo
 * @since 2021/03/29 15:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface After {

    String value() default "";

}
