package com.df.jsonboot.annotation.springmvc;

import java.lang.annotation.*;

/**
 * @author qinghuo
 * @since 2021/03/24 16:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface PathVariable {

    String value();

}
