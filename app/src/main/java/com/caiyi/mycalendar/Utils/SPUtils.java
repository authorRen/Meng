package com.caiyi.mycalendar.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.caiyi.mycalendar.compant.MyApplication;

import java.util.List;

/**
 * @author Ren ZeQiang
 * @since 2017/7/27
 */
public class SPUtils {

    public SPUtils() {
    }

    public static SharedPreferences getSP() {
        return MyApplication.getAppContext().getSharedPreferences("sp", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSP(String spName) {
        if (StringUtil.isNullOrEmpty(spName)) {
            throw new RuntimeException("sp name not null or empty");
        }
        return MyApplication.getAppContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    public static void putEmpty(String key) {
        getSP().edit().putString(key, "").apply();
    }

    public static void putString(String key, String value) {
        getSP().edit().putString(key,value).apply();
    }

    public static String getString(String key) {
        return getString(key, "");
    }


    public static String getString(String key, String defValue) {
        return getSP().getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        getSP().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defValue) {
        return getSP().getInt(key, defValue);
    }

    public static void putFloat(String key, float value) {
        getSP().edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key, float defValue) {
        return getSP().getFloat(key, defValue);
    }

    public static void putLong(String key, long value) {
        getSP().edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defValue) {
        return getSP().getLong(key, defValue);
    }

    public static void putBoolean(String key, boolean value) {
        getSP().edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return getSP().getBoolean(key, defValue);
    }

    public static boolean contains(String key) {
        return getSP().contains(key);
    }

    public static void remove(String key) {
        getSP().edit().remove(key).apply();
    }

    /**
     * 清空sp
     */
    public static void clear() {
        getSP().edit().clear().apply();
    }

    /** 把List转换成Json字符串保存到SP */
    public static <T> void putListToSp(String key, List<T> list) {
        if (list == null) {
            return;
        }
        getSP().edit().putString(key, JsonUtil.encode(list)).apply();
    }

    /** 保存对象到SP */
    public static void putObject(String key, Object obj) {
        getSP().edit().putString(key, JsonUtil.encode(obj)).apply();
    }

    /** 从SP中获取相应的对象 */
    public static <T> T getObject(String key, Class<T> clazz) {
        return JsonUtil.decode(SPUtils.getString(key), clazz);
    }
}
