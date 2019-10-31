package com.tdr.hotel.mvp.login;

import com.tdr.hotel.base.BaseBean;
import com.tdr.hotel.base.BasePresenter;
import com.tdr.hotel.base.BasePresenterImpl;
import com.tdr.hotel.base.RxObserver;
import com.tdr.hotel.base.Transformer;
import com.tdr.hotel.bean.UserBean;
import com.tdr.hotel.inter.Callback;
import com.tdr.hotel.utils.RetrofitUtils;
import com.tdr.hotel.utils.ToastUtils;

/**
 * Author：Libin on 2019/6/6 09:41
 * Description：
 */
public class LoginPresenter extends BasePresenterImpl<LoginContact.View> implements LoginContact.Presenter {

    @Override
    public void login(String account, String password) {
        if (!isViewAttached())
            return;
        RetrofitUtils.getInstance()
                .getService()
                .login(account, password)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<UserBean>() {

                    @Override
                    public void onSuccess(UserBean userBean) {
                        getView().hideLoading();
                        getView().onSuccess(userBean.getData());

                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));
    }
}
