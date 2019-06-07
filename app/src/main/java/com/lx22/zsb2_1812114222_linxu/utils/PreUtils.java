package com.lx22.zsb2_1812114222_linxu.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreUtils {
    public static  boolean getBoolean(Context ctx,String key,boolean value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getBoolean(key,value);
    }
      public static  void settBoolean(Context ctx,String key,boolean value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }
      public static  String getString(Context ctx,String key,String value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getString(key,value);
    }
      public static  void setString(Context ctx,String key,String value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }
      public static  int getInt(Context ctx,String key,int value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sp.getInt(key,value);
    }
      public static  void setInt(Context ctx,String key,int value){
        SharedPreferences sp = ctx.getSharedPreferences("config",Context.MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

}
