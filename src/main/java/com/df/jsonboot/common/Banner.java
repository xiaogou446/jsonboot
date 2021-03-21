package com.df.jsonboot.common;

import java.io.PrintStream;

/**
 * Banner接口 用于控制台打印 使用接口方便以后拓展参数打印
 *
 * @author qinghuo
 * @since 2021/03/17 3:42
 */
public interface Banner {

    /**
     * 打印banner文件中的内容
     *
     * @param bannerName banner文件名，如xxx.txt
     * @param printStream 输出流，用于打印
     */
    void printBanner(String bannerName, PrintStream printStream);

}
