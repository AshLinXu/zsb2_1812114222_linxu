package com.lx22.zsb2_1812114222_linxu.utils;

import android.content.Context;

public class CacheUtils {

    //保存在手机上
    public static void setCache(String url, String json, Context cxt) {
        PreUtils.setString(cxt, url, json);
    }

    public static String getCache(String url, Context cxt) {
        return PreUtils.getString(cxt, url, null);
    }
}
