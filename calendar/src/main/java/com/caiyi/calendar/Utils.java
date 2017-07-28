package com.caiyi.calendar;

import com.caiyi.calendar.model.CalendarDate;

import java.util.Calendar;
import java.util.HashMap;

/**
 * @author Ren ZeQiang
 * @since 2017/7/19
 */
public class Utils {
    public static final String[] weekName = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};

     private static HashMap<String, String> markData = new HashMap<>();

    /**
     * 获取月份对于的天数
     *
     * @param year 年份
     * @param month 月份
     * @return
     */
    public static int getMonthDays(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = 0;

        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            arr[1] = 29;
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

    /**
     * 获取年份
     * @return 返回当前的年份
     */
    public static int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 获取月份
     * @return 返回当前的月份
     */
    public static int getMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前的日期
     * @return 返回当前的日期
     */
    public static int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取星期几
     * @return 返回星期几
     */
    public static int getWeekDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }

    public static String getWeekdayPosition(CalendarDate date) {
        Calendar cal = Calendar.getInstance();
        cal.set(date.getYear(), date.getMonth() - 1, date.getDay());
        int week_index = cal.get(Calendar.DAY_OF_WEEK) + 5;
        if (week_index >= 7) {
            week_index -= 7;
        }
        return weekName[week_index];
    }

//    public static int getFirstWeekdayPosition(int year, int month) {
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(getDateFromString());
//    }
}
