package com.example.xsc238.shoppingcart.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：许少聪
 * 邮箱：18750910084@163.com
 * 作用：
 */
class CacheUtils {


    /**
     * 获取数据
     *
     * @param mContext
     * @param json_cart
     */
    public static String getString(Context mContext, String json_cart) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("atguigu", Context.MODE_PRIVATE);
        return sharedPreferences.getString(json_cart, "");
    }

    /**
     * 存储数据
     *
     * @param mContext
     * @param key
     * @param value
     */
    public static void putString(Context mContext, String key, String value) {
        SharedPreferences.Editor edit = mContext.getSharedPreferences("atguigu", Context.MODE_PRIVATE).edit();
        edit.putString(key, value).commit();

    }


}
