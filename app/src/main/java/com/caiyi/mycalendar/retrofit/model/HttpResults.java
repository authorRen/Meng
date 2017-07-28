package com.caiyi.mycalendar.retrofit.model;


import com.caiyi.mycalendar.annotation.DoNotProguard;

/**
 * 统一接口返回格式.
 *
 * @author HuoGuangXu
 * @since 2017/5/27.
 */

@DoNotProguard
public class HttpResults<T> {
    public int code;
    public String desc;
    public T results;
}
