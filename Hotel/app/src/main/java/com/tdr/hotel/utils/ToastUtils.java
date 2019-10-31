package com.tdr.hotel.utils;

import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.tdr.hotel.application.MyApp;

/**
 * Author：Libin on 2019/6/4 11:11
 * Email：1993911441@qq.com
 * Describe：Toast重复弹出
 */
public class ToastUtils {
    private static ToastUtils mInstance;
    private Toast mToast;

    private ToastUtils() {
    }

    public static ToastUtils getInstance() {
        if (mInstance == null) {
            synchronized (ToastUtils.class) {
                if (mInstance == null) {
                    mInstance = new ToastUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * @param msg
     */
    public void showToast(String msg) {
        LogUtils.log(msg);

        if (TextUtils.isEmpty(msg)) {
            return;
        }

        if (mToast == null) {
            mToast = Toast.makeText(MyApp.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

}
