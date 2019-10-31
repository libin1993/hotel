package com.tdr.hotel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.tdr.hotel.application.MyApp;

import java.io.File;

/**
 * Author：Libin on 2019/6/6 10:10
 * Description：
 */
public class SPUtils {

    private static SPUtils mInstance = null;  //单例对象
    //用户相关
    public static final String FILE_USER = "user";

    public static final String TOKEN = "token"; //token
    public static final String USERNAME = "username"; //登录名称
    public static final String HOTEL_NAME = "hotel_name"; //酒店名称

    private SPUtils() {
    }  //私有化构造方法

    /**
     * 获取单例方式
     *
     * @return
     */
    public static SPUtils getInstance() {
        if (mInstance == null) {
            synchronized (SPUtils.class) {
                if (mInstance == null) {
                    mInstance = new SPUtils();
                }
            }
        }

        return mInstance;
    }


    /**
     * 向指定文件插入值
     *
     * @param fileName
     * @param key
     * @param object
     */
    public void put(String fileName, String key, Object object) {
        if (object == null) {
            return;
        }

        SharedPreferences sp = MyApp.getInstance().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        editor.apply();
    }


    /**
     * 得到指定文件中的key对应的value，如果没有则返回传递的默认值
     *
     * @param filename
     * @param key
     * @param defaultObject
     * @return
     */
    public Object get(String filename, String key, Object defaultObject) {

        SharedPreferences sp = MyApp.getInstance().getSharedPreferences(filename, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getFloat(key, (Long) defaultObject);
        }

        return null;
    }


    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String filename, String key) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.clear();
        editor.apply();
    }


    /**
     * 移除文件
     */
    public void clear(String filename) {
        SharedPreferences sp = MyApp.getInstance().getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }


    public String getToken() {
        return (String) get(SPUtils.FILE_USER, SPUtils.TOKEN, "");
    }

    public String getUsername() {
        return (String) get(SPUtils.FILE_USER, SPUtils.USERNAME, "");
    }


    public String getHotelName() {
        return (String) get(SPUtils.FILE_USER, SPUtils.HOTEL_NAME, "");
    }
}
