package com.df.jsonboot.annotation.ioc;

import java.lang.annotation.*;

/**
 * 用于实现Qualifier注解，在@Autowired接口时选择不同的实现类名称
 *
 * @author qinghuo
 * @since 2021/03/26 9:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface Qualifier {

    String value();

}
