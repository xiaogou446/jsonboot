package com.df.jsonboot.entity;

import com.df.jsonboot.utils.DateUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 服务器处理异常时，返回的数据格式
 *
 * @author qinghuo
 * @since 2021/03/23 13:34
 */
@Getter
@ToString
@NoArgsConstructor
public class ErrorResponse {

    /**
     * 状态码
     */
    private int status;

    /**
     * 错误原因
     */
    private String message;

    /**
     * 请求路径
     */
    private String path;

    /**
     * 时间
     */
    private String dateTime;


    public ErrorResponse(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.dateTime = DateUtil.now();
    }
}
