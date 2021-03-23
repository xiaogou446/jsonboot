package com.df.jsonboot.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * 日期工具类
 *
 * @author qinghuo
 * @since 2021/03/23 13:30
 */
public class DateUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                .withLocale(Locale.CHINA)
                .withZone(ZoneId.systemDefault());

    /**
     * 返回当前时间
     *
     * @return 字符串表示的时间
     */
    public static String now(){
        return FORMATTER.format(Instant.now());
    }

}
