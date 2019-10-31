package com.tdr.hotel.base;

/**
 * Author：Libin on 2019/05/31 09:34
 * Email：1993911441@qq.com
 * Describe：
 */
public interface BaseView {

    void onSuccess(Object object);

    void onFail(String msg);

    void hideLoading();

}
