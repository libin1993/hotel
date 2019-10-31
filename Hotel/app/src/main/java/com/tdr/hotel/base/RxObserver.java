package com.tdr.hotel.base;


import com.tdr.hotel.inter.Callback;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Author：Libin on 2019/05/31 09:34
 * Email：1993911441@qq.com
 * Describe：回调封装
 */
public class RxObserver<T extends BaseBean> implements Observer<T> {

    private Callback<T> mCallback;

    public RxObserver(Callback<T> callback) {
        mCallback = callback;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.getStatus() == 2) {
            mCallback.onSuccess(t);
        } else {
            mCallback.onFail(t.getMsg());
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace();
        mCallback.onFail(e.toString());
    }

    @Override
    public void onComplete() {

    }

}
