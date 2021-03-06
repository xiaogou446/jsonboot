package com.df.jsonboot.core.springmvc.factory;

import com.df.jsonboot.annotation.springmvc.PathVariable;
import com.df.jsonboot.annotation.springmvc.RequestBody;
import com.df.jsonboot.annotation.springmvc.RequestParam;
import com.df.jsonboot.core.springmvc.resolver.ParameterResolver;
import com.df.jsonboot.core.springmvc.resolver.impl.DefaultParameterResolver;
import com.df.jsonboot.core.springmvc.resolver.impl.PathVariableParameterResolver;
import com.df.jsonboot.core.springmvc.resolver.impl.RequestBodyParameterResolver;
import com.df.jsonboot.core.springmvc.resolver.impl.RequestParamParameterResolver;

import java.lang.reflect.Parameter;

/**
 * 通过方法获取实用哪个注解的简单工厂
 *
 * @author qinghuo
 * @since 2021/03/24 15:51
 */
public class ParameterResolverFactory {

    /**
     * 根据parameter的类型 获取对应的执行方式
     *
     * @param parameter 参数
     * @return 参数执行器
     */
    public static ParameterResolver get(Parameter parameter){

        if (parameter.isAnnotationPresent(RequestParam.class)){
            return new RequestParamParameterResolver();
        }
        if (parameter.isAnnotationPresent(RequestBody.class)){
            return new RequestBodyParameterResolver();
        }
        if (parameter.isAnnotationPresent(PathVariable.class)){
            return new PathVariableParameterResolver();
        }
        return new DefaultParameterResolver();
    }

}
