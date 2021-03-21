package com.df.jsonboot.common;

import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * jsonboot启动时的banner展示
 *
 * @author qinghuo
 * @since 2021/03/17 3:54
 */
public class JsonBootBanner implements Banner{

    /**
     * 默认的启动文件名称
     */
    private static final String DEFAULT_BANNER_NAME = "jsonbootBanner.txt";

    @Override
    public void printBanner(String bannerName, PrintStream printStream) {
        //如果为空，使用默认的bannerName
        if(StringUtils.isBlank(bannerName)){
            bannerName = DEFAULT_BANNER_NAME;
        }
        //使用当前线程从项目根目录读取配置文件
        URL url = Thread.currentThread().getContextClassLoader().getResource(bannerName);
        if (url == null){
            return;
        }
        try {
            Path path = Paths.get(url.toURI());
            Files.lines(path).forEach(printStream::println);
            printStream.println();
        } catch (URISyntaxException | IOException e) {
            printStream.printf("banner文件加载错误 banner: %s, error: %s",bannerName, e);
        }

    }
}
