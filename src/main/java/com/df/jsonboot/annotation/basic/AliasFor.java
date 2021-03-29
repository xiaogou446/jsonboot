package com.df.jsonboot.annotation.basic;

import java.lang.annotation.*;

/**
 * 别名标注注解  组合注解的实现(暂未完成)
 *
 * @author qinghuo
 * @since 2021/03/28 13:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface AliasFor {

    /**
     * 别名对应的注解
     */
    Class<? extends Annotation> annotation() default Annotation.class;

    /**
     * 别名注解对应的属性
     */
    String attribute() default "";

    /**
     * 属性对应的值
     */
    String value() default "";

}
