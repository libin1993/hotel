package com.tdr.hotel.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author：Libin on 2019/05/31 09:34
 * Email：1993911441@qq.com
 * Describe：
 */
public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView {
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();

        mPresenter.attach(this);
    }


    protected abstract P initPresenter();

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        super.onDestroyView();

    }
}
