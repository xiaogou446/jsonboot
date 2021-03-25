package com.df.jsonboot.annotation;

import java.lang.annotation.*;

/**
 * 将标记的类加入ioc容器
 *
 * @author qinghuo
 * @since 2021/03/25 9:51
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Component {

    String value() default "";

}
