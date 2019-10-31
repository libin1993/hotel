package com.tdr.hotel.mvp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.tdr.hotel.R;
import com.tdr.hotel.base.BaseMvpActivity;
import com.tdr.hotel.bean.UserBean;
import com.tdr.hotel.mvp.main.MainActivity;
import com.tdr.hotel.utils.LoadingUtils;
import com.tdr.hotel.utils.SPUtils;
import com.tdr.hotel.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：Libin on 2019/6/6 09:43
 * Description：登录
 */
public class LoginActivity extends BaseMvpActivity<LoginContact.Presenter> implements LoginContact.View {
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!TextUtils.isEmpty(SPUtils.getInstance().getToken())) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);
        }

    }

    @Override
    protected LoginContact.Presenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onSuccess(Object object) {
        UserBean.DataBean dataBean = (UserBean.DataBean) object;
        SPUtils.getInstance().put(SPUtils.FILE_USER, SPUtils.TOKEN, dataBean.getCompanyId());
        SPUtils.getInstance().put(SPUtils.FILE_USER, SPUtils.USERNAME, dataBean.getLoginName());
        SPUtils.getInstance().put(SPUtils.FILE_USER, SPUtils.HOTEL_NAME, dataBean.getTraveIindustryName());

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.getInstance().showToast(msg);
    }

    @Override
    public void hideLoading() {
        LoadingUtils.getInstance().dismiss();
    }


    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        login();
    }

    private void login() {

        String account = etAccount.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();

        if (TextUtils.isEmpty(account)) {
            ToastUtils.getInstance().showToast("请输入账号");
            return;
        }

        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.getInstance().showToast("请输入密码");
            return;
        }

        LoadingUtils.getInstance().showLoading(this, "登录中");
        mPresenter.login(account, pwd);

    }

}
