package com.df.jsonboot.exception;

/**
 * dependencyInjection未能找到目标类时进行报错
 *
 * @author qinghuo
 * @since 2021/03/26 11:21
 */
public class NotFountTargetBeanException extends RuntimeException{

    public NotFountTargetBeanException(String mesage){
        super(mesage);
    }

}
