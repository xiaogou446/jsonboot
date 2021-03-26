package com.df.jsonboot.exception;

/**
 * 定义接口无实现类异常
 *
 * @author qinghuo
 * @since 2021/03/26 10:42
 */
public class InterfaceNotExistsImplementException extends RuntimeException {

    public InterfaceNotExistsImplementException(String message){
        super(message);
    }

}
