package com.df.jsonboot.annotation.springmvc;

import java.lang.annotation.*;

/**
 * 用于标注传入参数映射
 *
 * @author qinghuo
 * @since 2021/03/22 9:34
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestParam {

    String value();

}
