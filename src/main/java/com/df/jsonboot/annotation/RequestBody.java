package com.df.jsonboot.annotation;

import java.lang.annotation.*;

/**
 * requestBody注解，适用于json格式的post请求
 *
 * @author qinghuo
 * @since 2021/03/23 14:02
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RequestBody {
}
