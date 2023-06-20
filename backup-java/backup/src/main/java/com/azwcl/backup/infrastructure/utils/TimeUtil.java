package com.azwcl.backup.infrastructure.utils;

import java.util.Calendar;

/**
 * 时间工具类
 *
 * @author azwcl
 * @date 2023/06/18
 * @since v0.0.1
 */


public class TimeUtil {
    /**
     * 获取日期
     *
     * @return 日期
     */
    public static int getDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100 + calendar.get(Calendar.DATE);
    }

    /**
     * 获取时间
     *
     * @return 时间
     */
    public static int getTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY) * 10000 + calendar.get(Calendar.MINUTE) * 100 + calendar.get(Calendar.SECOND);
    }
}