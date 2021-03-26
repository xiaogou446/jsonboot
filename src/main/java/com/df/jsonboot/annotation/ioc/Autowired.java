package com.df.jsonboot.annotation.ioc;

import java.lang.annotation.*;

/**
 * 实现Autowired依赖注入的注解功能
 *
 * @author qinghuo
 * @since 2021/03/25 9:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Autowired {
}
