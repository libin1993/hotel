package com.tdr.hotel.mvp.alarmrecord;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tdr.hotel.R;
import com.tdr.hotel.base.BaseMvpActivity;
import com.tdr.hotel.bean.AlarmRecordBean;
import com.tdr.hotel.mvp.login.LoginActivity;
import com.tdr.hotel.mvp.main.MainActivity;
import com.tdr.hotel.utils.FormatUtils;
import com.tdr.hotel.utils.LoadingUtils;
import com.tdr.hotel.utils.ObjectUtils;
import com.tdr.hotel.utils.SPUtils;
import com.tdr.hotel.utils.StatusBarUtils;
import com.tdr.hotel.utils.ToastUtils;
import com.tdr.hotel.widget.RVDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author：Libin on 2019/6/11 16:21
 * Description：报警警记录
 */
public class AlarmRecordActivity extends BaseMvpActivity<AlarmRecordContact.Presenter> implements AlarmRecordContact.View {


    @BindView(R.id.rv_record)
    RecyclerView rvRecord;
    @BindView(R.id.view_status_bar)
    View viewStatusBar;
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.cv_back)
    CardView cvBack;
    private List<AlarmRecordBean.DataBean.ListBean> dataList = new ArrayList<>();
    private BaseQuickAdapter<AlarmRecordBean.DataBean.ListBean, BaseViewHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_record);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        LoadingUtils.getInstance().showLoading(this, "请求中");
        mPresenter.initRecord(SPUtils.getInstance().getToken());
    }

    private void initView() {
        StatusBarUtils.getInstance().setStatusBarHeight(viewStatusBar);
        tvHotelName.setText(SPUtils.getInstance().getHotelName());
        tvUsername.setText(SPUtils.getInstance().getUsername());

        rvRecord.setLayoutManager(new LinearLayoutManager(this));
        rvRecord.addItemDecoration(new RVDividerItemDecoration(this));
        adapter = new BaseQuickAdapter<AlarmRecordBean.DataBean.ListBean, BaseViewHolder>(R.layout.rv_record_item, dataList) {
            @Override
            protected void convert(BaseViewHolder helper, AlarmRecordBean.DataBean.ListBean item) {
                helper.setText(R.id.tv_record_time, item.getCreateTime().replaceAll("T", " "));
                helper.setText(R.id.tv_record_room, item.getRoomNo());
                TextView tvType = helper.getView(R.id.tv_record_type);
                if (item.getAlarmType() == 1) {
                    tvType.setText("安防报警");
                } else {
                    tvType.setText("烟雾报警");
                }
            }
        };
        rvRecord.setAdapter(adapter);
    }

    @Override
    protected AlarmRecordContact.Presenter initPresenter() {
        return new AlarmRecordPresenter();
    }


    @Override
    public void onSuccess(Object object) {
        AlarmRecordBean.DataBean alarmRecordBean = (AlarmRecordBean.DataBean) object;
        dataList.clear();
        if (alarmRecordBean != null && ObjectUtils.getInstance().isNotNull(alarmRecordBean.getList())) {
            dataList.addAll(alarmRecordBean.getList());
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.getInstance().showToast(msg);
    }

    @Override
    public void hideLoading() {
        LoadingUtils.getInstance().dismiss();
    }


    @OnClick({R.id.tv_username, R.id.cv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_username:
                logOut();
                break;
            case R.id.cv_back:
                finish();
                break;
        }
    }


    /**
     * 退出登录
     */
    private void logOut() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_user, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, FormatUtils.getInstance().dp2px(120),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAsDropDown(tvUsername, -FormatUtils.getInstance().dp2px(55),
                -FormatUtils.getInstance().dp2px(10));

        TextView tvLogOut = contentView.findViewById(R.id.tv_log_out);


        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                SPUtils.getInstance().clear(SPUtils.FILE_USER);
                Intent intent = new Intent(AlarmRecordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }
}
