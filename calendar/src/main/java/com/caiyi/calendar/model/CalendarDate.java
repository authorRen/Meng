package com.caiyi.calendar.model;

import android.util.Log;

import com.caiyi.calendar.Utils;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Ren ZeQiang
 * @since 2017/7/19
 */
public class CalendarDate implements Serializable {
    private static final String TAG = "CalendarDate";
    private static final long serialVersionUID = 1L;
    public int year;
    public int month;
    public int day;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public CalendarDate(int year, int month, int day) {
        if (month > 12) {
            month = 1;
            year++;
        } else if (month < 1) {
            month = 12;
            year--;
        }
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public CalendarDate() {
        this.year = Utils.getYear();
        this.month = Utils.getMonth();
        this.day = Utils.getDay();
    }

    public CalendarDate modifyDay(int day) {
        int lastMonthDays = Utils.getMonthDays(this.year, this.month - 1);
        int currentMonthDays = Utils.getMonthDays(this.year, this.month);

        CalendarDate modifyData;
        if (day > currentMonthDays) {
            modifyData = new CalendarDate(this.year, this.month, this.day);
            Log.e(TAG, "移动天数过大");
        } else if (day > 0) {
            modifyData = new CalendarDate(this.year, this.month, year);
        } else if (day > 0 - lastMonthDays) {
            modifyData = new CalendarDate(this.year, this.month - 1, lastMonthDays);
        } else {
            modifyData = new CalendarDate(this.year, this.month, this.day);
            Log.e(TAG, "移动天数过大");
        }
        return modifyData;
    }

    public CalendarDate modifyWeek(int offset) {
        CalendarDate result = new CalendarDate();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        c.add(Calendar.DATE, offset * 7);
        result.setYear(c.get(Calendar.YEAR));
        result.setMonth(c.get(Calendar.MONTH));
        result.setDay(c.get(Calendar.DAY_OF_MONTH));
        return result;
    }

    public CalendarDate modifyMonth(int offset) {
        CalendarDate result = new CalendarDate();
        int addToMonth = this.month + offset;
        if (offset > 0) {
            if (addToMonth > 12) {
                result.setYear(this.year + (addToMonth - 1) / 12);
                result.setMonth(addToMonth % 12 == 0 ? 12 : addToMonth % 12);
            } else {
                result.setYear(this.year);
                result.setMonth(addToMonth);
            }
        } else {
            if (addToMonth == 0) {
                result.setYear(this.year - 1);
                result.setMonth(12);
            } else if (addToMonth < 0) {
                result.setYear(this.year + addToMonth / 12 - 1);
                int month = 12 - Math.abs(addToMonth) % 12;
                result.setMonth(addToMonth == 0 ? 12 : addToMonth);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "CalendarDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    public boolean equals(CalendarDate date) {
        if (date == null) {
            return false;
        }
        if (this.getYear() == date.getYear()
                && this.getMonth() == date.getMonth()
                && this.getDay() == date.getDay()) {
            return true;
        }
        return false;
    }

    public CalendarDate cloneSelf() {
        return new CalendarDate(year , month , day);
    }
}