package com.tdr.hotel.mvp.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.tdr.hotel.R;
import com.tdr.hotel.base.BaseMvpActivity;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.bean.RoomListBean;
import com.tdr.hotel.mvp.alarmrecord.AlarmRecordActivity;
import com.tdr.hotel.mvp.login.LoginActivity;
import com.tdr.hotel.mvp.room.RoomFragment;
import com.tdr.hotel.utils.FormatUtils;
import com.tdr.hotel.utils.FragmentTabUtils;
import com.tdr.hotel.utils.LoadingUtils;
import com.tdr.hotel.utils.ObjectUtils;
import com.tdr.hotel.utils.SPUtils;
import com.tdr.hotel.utils.StatusBarUtils;
import com.tdr.hotel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Author：Libin on 2019/6/6 11:20
 * Description：
 */
public class MainActivity extends BaseMvpActivity<MainContact.Presenter> implements MainContact.View {

    @BindView(R.id.ctl_main)
    CommonTabLayout ctlMain;
    @BindView(R.id.view_status_bar)
    View viewStatusBar;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_check_in)
    TextView tvCheckIn;
    @BindView(R.id.tv_check_out)
    TextView tvCheckOut;
    @BindView(R.id.et_search_room)
    EditText etSearchRoom;
    @BindView(R.id.tv_hotel_name)
    TextView tvHotelName;
    @BindView(R.id.iv_clear_text)
    ImageView ivClearText;
    private FragmentTabUtils fragmentTabUtils;

    private PopupWindow popupWindow;
    //房间号
    private TextView tvRoomNo;
    //fragment类型
    private int type;
    //房间id
    private String roomId;
    //入住人数
    private int num;
    private BaseQuickAdapter<RoomListBean, BaseViewHolder> adapter;
    private List<RoomListBean> roomList = new ArrayList<>();
    //连续点击退出应用
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        StatusBarUtils.getInstance().setStatusBarHeight(viewStatusBar);
        tvHotelName.setText(SPUtils.getInstance().getHotelName());
        tvUsername.setText(SPUtils.getInstance().getUsername());

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(RoomFragment.newInstance(1));
        fragmentList.add(RoomFragment.newInstance(2));
        fragmentList.add(RoomFragment.newInstance(3));

        ArrayList<CustomTabEntity> tabList = new ArrayList<>();
        tabList.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "全部房间";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });

        tabList.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "安防预警";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });

        tabList.add(new CustomTabEntity() {
            @Override
            public String getTabTitle() {
                return "烟雾预警";
            }

            @Override
            public int getTabSelectedIcon() {
                return 0;
            }

            @Override
            public int getTabUnselectedIcon() {
                return 0;
            }
        });
        ctlMain.setTabData(tabList);
        fragmentTabUtils = new FragmentTabUtils(getSupportFragmentManager(), fragmentList, R.id.fl_container, ctlMain);


        etSearchRoom.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(v.getText().toString().trim())) {
                    searchRoom(v.getText().toString().trim());
                    return true;
                }
                return false;
            }
        });

        etSearchRoom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    ivClearText.setVisibility(View.GONE);
                    etSearchRoom.setCursorVisible(false);
                    cancelSearch();
                } else {
                    etSearchRoom.setCursorVisible(true);
                    ivClearText.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    /**
     * 搜索房间号
     *
     * @param roomNo
     */
    private void searchRoom(String roomNo) {
        LoadingUtils.getInstance().showLoading(this, "请求中");
        fragmentTabUtils.setCurrentFragment(0);
        RoomFragment fragment = (RoomFragment) getSupportFragmentManager().findFragmentByTag("0");
        if (fragment != null) {
            fragment.searchRoom(roomNo);
        }
    }


    /**
     * 取消搜索
     */
    private void cancelSearch() {
        RoomFragment fragment = (RoomFragment) getSupportFragmentManager().findFragmentByTag("0");
        if (fragment != null && (fragment.subscribe == null || fragment.subscribe.isDisposed())) {
            LoadingUtils.getInstance().showLoading(this, "请求中");
            fragmentTabUtils.setCurrentFragment(0);
            fragment.initData();
        }

    }


    @Override
    protected MainContact.Presenter initPresenter() {
        return new MainPresenter();
    }

    @Override
    public void hideLoading() {
        LoadingUtils.getInstance().dismiss();
    }


    @Override
    public void onSuccess(Object object) {
        RoomBean.DataBean dataBean = (RoomBean.DataBean) object;
        if (dataBean != null && ObjectUtils.getInstance().isNotNull(dataBean.getList())) {
            selectRoom(dataBean.getList());
        } else {
            ToastUtils.getInstance().showToast("暂无房间");
        }
    }

    @Override
    public void onFail(String msg) {
        ToastUtils.getInstance().showToast(msg);
    }

    @Override
    public void editSuccess(String msg) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        ToastUtils.getInstance().showToast(msg);
        fragmentTabUtils.setCurrentFragment(0);
        refreshData();

    }

    /**
     * 刷新页面数据
     */
    public void refreshData() {

        RoomFragment fragment1 = (RoomFragment) getSupportFragmentManager().findFragmentByTag("0");
        if (fragment1 != null) {
            if (fragment1.subscribe != null && !fragment1.subscribe.isDisposed()) {
                fragment1.subscribe.dispose();
                fragment1.subscribe = null;
            }
            fragment1.initData();
        }


        RoomFragment fragment2 = (RoomFragment) getSupportFragmentManager().findFragmentByTag("1");
        if (fragment2 != null) {
            if (fragment2.subscribe != null && !fragment2.subscribe.isDisposed()) {
                fragment2.subscribe.dispose();
                fragment2.subscribe = null;
            }
            fragment2.initData();
        }


        RoomFragment fragment3 = (RoomFragment) getSupportFragmentManager().findFragmentByTag("2");
        if (fragment3 != null) {
            if (fragment1 != null && fragment3.subscribe != null && !fragment3.subscribe.isDisposed()) {
                fragment3.subscribe.dispose();
                fragment3.subscribe = null;
            }
            fragment3.initData();
        }
    }


    @OnClick({R.id.tv_username, R.id.tv_check_in, R.id.tv_check_out, R.id.iv_clear_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_username:
                showMore();
                break;
            case R.id.tv_check_in:
                roomId = null;
                num = 0;
                type = 1;
                roomList.clear();
                checkInOrCheckOut();
                break;
            case R.id.tv_check_out:
                roomId = null;
                num = 0;
                type = 2;
                checkInOrCheckOut();
                break;
            case R.id.iv_clear_text:
                if (!TextUtils.isEmpty(etSearchRoom.getText().toString().trim())) {
                    etSearchRoom.setText(null);
                }

                break;
        }
    }

    /**
     * 一键入住、一键退房
     */
    private void checkInOrCheckOut() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popup_select_room, null);
        popupWindow = new PopupWindow(contentView, FormatUtils.getInstance().dp2px(455),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAtLocation(ctlMain, Gravity.CENTER, 0, 0);
        StatusBarUtils.getInstance().setBackgroundAlpha(MainActivity.this, true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                StatusBarUtils.getInstance().setBackgroundAlpha(MainActivity.this, false);
            }
        });


        TextView tvTitle = contentView.findViewById(R.id.tv_select_title);
        ImageView ivCancel = contentView.findViewById(R.id.iv_cancel_select);
        final RelativeLayout rlSelect = contentView.findViewById(R.id.rl_select_room);
        TextView tvSelect = contentView.findViewById(R.id.tv_select_room);
        tvRoomNo = contentView.findViewById(R.id.tv_select_room_mo);
        LinearLayout llSelect = contentView.findViewById(R.id.ll_select_num);
        RecyclerView rvSelect = contentView.findViewById(R.id.rv_select_num);
        Button btn = contentView.findViewById(R.id.btn_confirm);

        if (type == 1) {
            tvTitle.setText("入住信息");
            tvSelect.setText("选择入住房间");
            llSelect.setVisibility(View.VISIBLE);

            rvSelect.setLayoutManager(new GridLayoutManager(this, 4));
            adapter = new BaseQuickAdapter<RoomListBean, BaseViewHolder>(R.layout.rv_select_room_item, roomList) {
                @Override
                protected void convert(BaseViewHolder helper, RoomListBean item) {
                    TextView tvNum = helper.getView(R.id.tv_check_in_num);
                    tvNum.setText(item.getNum() + "人");
                    if (item.isSelect()) {
                        tvNum.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.blue_3d9));
                        tvNum.setBackgroundResource(R.drawable.bg_room_select);
                    } else {
                        tvNum.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.gray_55));
                        tvNum.setBackgroundResource(R.drawable.bg_room_normal);
                    }
                }
            };

            rvSelect.setAdapter(adapter);

            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    num = roomList.get(position).getNum();
                    for (int i = 0; i < roomList.size(); i++) {
                        roomList.get(i).setSelect(i == position);
                    }

                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            tvTitle.setText("退房信息");
            tvSelect.setText("选择房间");
            llSelect.setVisibility(View.INVISIBLE);
        }

        rlSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRoom();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    if (TextUtils.isEmpty(roomId)) {
                        ToastUtils.getInstance().showToast("请选择入住房间");
                        return;
                    }

                    if (num == 0) {
                        ToastUtils.getInstance().showToast("请选择入住人数");
                        return;
                    }

                    LoadingUtils.getInstance().showLoading(MainActivity.this, "请求中");
                    mPresenter.checkIn(roomId, num);
                } else {
                    if (TextUtils.isEmpty(roomId)) {
                        ToastUtils.getInstance().showToast("请选择房间");
                        return;
                    }
                    LoadingUtils.getInstance().showLoading(MainActivity.this, "请求中");
                    mPresenter.checkOut(roomId);
                }
            }
        });
    }

    /**
     * 空置房间
     */
    private void initRoom() {
        mPresenter.selectRoom(type);
    }


    /**
     * 选择房间
     *
     * @param dataList
     */
    private void selectRoom(final List<RoomBean.DataBean.ListBean> dataList) {

        List<String> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            list.add(dataList.get(i).getRoomNo());
        }
        OptionPicker picker = new OptionPicker(this, list);
        picker.setCanceledOnTouchOutside(true);
        picker.setCycleDisable(true);

        picker.setCancelTextColor(ContextCompat.getColor(this, R.color.gray_66));
        picker.setCancelTextSize(17);
        picker.setSubmitTextColor(ContextCompat.getColor(this, R.color.blue_3d9));
        picker.setSubmitTextSize(17);
        picker.setTitleTextColor(ContextCompat.getColor(this, R.color.gray_22));
        picker.setTitleTextSize(17);
        picker.setTopBackgroundColor(ContextCompat.getColor(this, R.color.gray_f2));
        picker.setTopLineVisible(false);
        picker.setTopHeight(40);
        picker.setTextSize(18);
        picker.setBackgroundColor(Color.WHITE);
        picker.setTextColor(ContextCompat.getColor(this, R.color.blue_3d9), ContextCompat.getColor(this, R.color.gray_88));
        picker.setDividerColor(ContextCompat.getColor(this, R.color.gray_ee));
        picker.setDividerRatio((float) 0.75);


        if (type == 1) {
            picker.setTitleText("选择入住房间");
        } else {
            picker.setTitleText("选择房间");
        }

        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                roomId = dataList.get(index).getRoomId();
                tvRoomNo.setText(item+"室");
                if (type == 1) {
                    roomList.clear();
                    num = 0;
                    for (int i = 1; i <= dataList.get(index).getBedNum(); i++) {
                        roomList.add(new RoomListBean(i, false));
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });
        picker.show();
    }


    /**
     * 退出登录、报警记录
     */
    private void showMore() {
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


        TextView tvRecord = contentView.findViewById(R.id.tv_report_record);
        TextView tvLogOut = contentView.findViewById(R.id.tv_log_out);
        View viewLine = contentView.findViewById(R.id.view_popup_line);

        tvRecord.setVisibility(View.VISIBLE);
        viewLine.setVisibility(View.VISIBLE);

        tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                startActivity(new Intent(MainActivity.this, AlarmRecordActivity.class));

            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                SPUtils.getInstance().clear(SPUtils.FILE_USER);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.getInstance().showToast("再按一次退出应用");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }

    }


}
