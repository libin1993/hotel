package com.tdr.hotel.mvp.login;

import com.tdr.hotel.base.BasePresenter;
import com.tdr.hotel.base.BaseView;
import com.tdr.hotel.bean.UserBean;

/**
 * Author：Libin on 2019/6/6 09:36
 * Description：
 */
public class LoginContact {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void login(String account, String password);
    }
}
