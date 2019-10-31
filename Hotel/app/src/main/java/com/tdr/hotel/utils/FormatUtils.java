package com.tdr.hotel.utils;

import android.text.TextUtils;

import com.tdr.hotel.application.MyApp;

/**
 * Author：Libin on 2019/6/6 15:49
 * Description：
 */
public class FormatUtils {
    private static FormatUtils mInstance;

    private FormatUtils() {
    }

    public static FormatUtils getInstance() {
        if (mInstance == null) {
            synchronized (FormatUtils.class) {
                if (mInstance == null) {
                    mInstance = new FormatUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 将px转换为与之相等的dp
     */
    public int px2dp(float pxValue) {
        final float scale = MyApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 将dp转换为与之相等的px
     */
    public int dp2px(float dipValue) {
        final float scale = MyApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


}
