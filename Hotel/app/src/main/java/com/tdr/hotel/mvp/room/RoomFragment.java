package com.tdr.hotel.mvp.room;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tdr.hotel.R;
import com.tdr.hotel.base.BaseMvpFragment;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.bean.RoomDetailBean;
import com.tdr.hotel.bean.RoomListBean;
import com.tdr.hotel.mvp.main.MainActivity;
import com.tdr.hotel.utils.FormatUtils;
import com.tdr.hotel.utils.LoadingUtils;
import com.tdr.hotel.utils.LogUtils;
import com.tdr.hotel.utils.ObjectUtils;
import com.tdr.hotel.utils.StatusBarUtils;
import com.tdr.hotel.utils.ToastUtils;
import com.tdr.hotel.widget.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Author：Libin on 2019/6/6 13:34
 * Description：
 */
public class RoomFragment extends BaseMvpFragment<RoomContact.Presenter> implements RoomContact.View {

    @BindView(R.id.rv_room)
    RecyclerView rvRoom;
    Unbinder unbinder;

    List<RoomBean.DataBean.ListBean> roomList = new ArrayList<>();
    //fragment类型
    private int type;
    private BaseQuickAdapter<RoomBean.DataBean.ListBean, BaseViewHolder> adapter;
    public Disposable subscribe;
    private PopupWindow popupWindow;
    private List<RoomListBean> bedList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        initView();
        initData();
        return view;
    }

    private void getData() {
        type = getArguments() != null ? getArguments().getInt("type") : 1;
    }

    /**
     * 定时请求数据
     */
    public void initData() {
        if (subscribe == null || subscribe.isDisposed()) {
            subscribe = Observable.interval(0, 10, TimeUnit.SECONDS)
                    .doOnNext(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            mPresenter.initRoom(type);
                        }
                    }).subscribe();
        }
    }


    /**
     * 搜索房间
     */
    public void searchRoom(String roomNo) {
        if (type == 1) {
            if (subscribe != null && !subscribe.isDisposed()) {
                subscribe.dispose();
                subscribe = null;
            }

            mPresenter.searchRoom(roomNo);
        }
    }


    private void initView() {

        rvRoom.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        rvRoom.addItemDecoration(new GridItemDecoration(FormatUtils.getInstance().dp2px(14)));
        adapter = new BaseQuickAdapter<RoomBean.DataBean.ListBean, BaseViewHolder>(R.layout.rv_room_item, roomList) {
            @Override
            protected void convert(BaseViewHolder helper, RoomBean.DataBean.ListBean item) {
                CardView cardView = helper.getView(R.id.cv_room);
                ViewGroup.LayoutParams layoutParams = cardView.getLayoutParams();
                layoutParams.height = (StatusBarUtils.getInstance().getScreenWidth(getActivity())
                        - FormatUtils.getInstance().dp2px(14) * 8) * 14 / (7 * 13);
                cardView.setLayoutParams(layoutParams);
                View viewStatus = helper.getView(R.id.view_room_status);

                if (type == 3) {
                    FrameLayout flWarn = helper.getView(R.id.fl_warn);
                    flWarn.setVisibility(View.VISIBLE);

                    TextView tvPeopleCount = helper.getView(R.id.tv_people_count);

                    if (item.getRegisterCount() == 0) {
                        tvPeopleCount.setText("0");
                        viewStatus.setBackgroundResource(R.drawable.room_status_empty);
                    } else {
                        tvPeopleCount.setText(String.valueOf(item.getHeadCount()));
                        switch (item.getPersonNumStatus()) {
                            case 1:
                                viewStatus.setBackgroundResource(R.drawable.room_status_noraml);
                                break;
                            case 2:
                                viewStatus.setBackgroundResource(R.drawable.room_status_warn);
                                break;
                            case 3:
                                viewStatus.setBackgroundResource(R.drawable.room_status_danger);
                                break;
                        }
                    }


                    RelativeLayout rlStatus = helper.getView(R.id.rl_room_status);

                    switch (item.getSmokeStatus()) {
                        case 1:
                            rlStatus.setBackgroundResource(R.drawable.circle_normal);
                            break;
                        case 2:
                            rlStatus.setBackgroundResource(R.drawable.circle_warn);
                            break;
                        case 3:
                            rlStatus.setBackgroundResource(R.drawable.circle_danger);
                            break;
                    }

                    helper.setText(R.id.tv_room_number, item.getRoomNo() + "室");


                } else {
                    FrameLayout flNormal = helper.getView(R.id.fl_normal);
                    flNormal.setVisibility(View.VISIBLE);


                    TextView tvStatus = helper.getView(R.id.tv_people_num);
                    TextView tvTip = helper.getView(R.id.tv_people_tip);
                    switch (item.getSmokeStatus()) {
                        case 1:
                            viewStatus.setBackgroundResource(R.drawable.room_status_noraml);
                            break;
                        case 2:
                            viewStatus.setBackgroundResource(R.drawable.room_status_warn);
                            break;
                        case 3:
                            viewStatus.setBackgroundResource(R.drawable.room_status_danger);
                            break;
                    }
                    helper.setText(R.id.tv_room_no, item.getRoomNo() + "室");

                    if (item.getRegisterCount() == 0) {
                        tvStatus.setText("0");
                        tvStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray_cc));
                        tvTip.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray_cc));
                    } else {
                        tvStatus.setText(String.valueOf(item.getHeadCount()));
                        switch (item.getPersonNumStatus()) {
                            case 1:
                                tvStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.green_5ab));
                                tvTip.setTextColor(ContextCompat.getColor(getActivity(), R.color.green_5ab));
                                break;
                            case 2:
                                tvStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_fda));
                                tvTip.setTextColor(ContextCompat.getColor(getActivity(), R.color.orange_fda));
                                break;
                            case 3:
                                tvStatus.setTextColor(ContextCompat.getColor(getActivity(), R.color.red_e45));
                                tvTip.setTextColor(ContextCompat.getColor(getActivity(), R.color.red_e45));
                                break;
                        }
                    }
                }


            }

        };
        rvRoom.setAdapter(adapter);


        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPresenter.getRoomDetail(roomList.get(position).getRoomId());
            }
        });
    }

    @Override
    protected RoomContact.Presenter initPresenter() {
        return new RoomPresenter();
    }

    @Override
    public void onSuccess(Object object) {
        RoomBean.DataBean dataBean = (RoomBean.DataBean) object;
        roomList.clear();
        if (dataBean != null && ObjectUtils.getInstance().isNotNull(dataBean.getList())) {
            roomList.addAll(dataBean.getList());
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public static RoomFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        RoomFragment fragment = new RoomFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void roomDetail(RoomDetailBean.DataBean dataBean, String roomId) {
        if (dataBean != null) {
            showRoomDetail(dataBean, roomId);
        }
    }

    @Override
    public void editSuccess(String msg) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        ToastUtils.getInstance().showToast(msg);

        if (getActivity() != null) {
            ((MainActivity) getActivity()).refreshData();
        }

    }

    /**
     * 房间详情
     *
     * @param dataBean
     */
    private void showRoomDetail(final RoomDetailBean.DataBean dataBean, final String roomId) {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_room_detail, null);
        final PopupWindow popupWindow = new PopupWindow(contentView, FormatUtils.getInstance().dp2px(312),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAtLocation(getActivity().findViewById(R.id.ctl_main),
                Gravity.CENTER | Gravity.RIGHT, 0, 0);
        StatusBarUtils.getInstance().setBackgroundAlpha(getActivity(), true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                StatusBarUtils.getInstance().setBackgroundAlpha(getActivity(), false);
            }
        });

        ImageView ivBack = contentView.findViewById(R.id.iv_detail_back);
        TextView tvRoomNo = contentView.findViewById(R.id.tv_room_number);
        TextView tvBedNum = contentView.findViewById(R.id.tv_bed_num);
        TextView tvRegisterNum = contentView.findViewById(R.id.tv_register_num);
        TextView tvMonitorNum = contentView.findViewById(R.id.tv_monitor_num);
        TextView tvSecurityStatus = contentView.findViewById(R.id.tv_security_status);
        TextView tvSmokeStatus = contentView.findViewById(R.id.tv_smoke_status);

        Button btnCheckIn = contentView.findViewById(R.id.btn_check_in);
        Button btnCheckOut = contentView.findViewById(R.id.btn_check_out);
        Button btnEditRoom = contentView.findViewById(R.id.btn_edit_room);


        tvRoomNo.setText(dataBean.getRoomNo() + "号房");
        tvBedNum.setText("房间床铺数量：" + dataBean.getBedNum() + "张");
        tvMonitorNum.setText("监测实时人数：" + dataBean.getHeadCount() + "人");

        String smokeStatus = "";
        switch (dataBean.getSmokeStatus()) {
            case 1:
                smokeStatus = "正常";
                break;
            case 2:
                smokeStatus = "预警";
                break;
            case 3:
                smokeStatus = "报警";
                break;
        }
        tvSmokeStatus.setText("烟雾状态：" + smokeStatus);

        if (dataBean.getRegisterCount() > 0) {
            tvRegisterNum.setVisibility(View.VISIBLE);
            tvRegisterNum.setText("登记入住人数：" + dataBean.getRegisterCount() + "人");
            btnCheckIn.setVisibility(View.GONE);
            btnCheckOut.setVisibility(View.VISIBLE);
            btnEditRoom.setVisibility(View.VISIBLE);


            String securityStatus = "";
            switch (dataBean.getPersonNumStatus()) {
                case 1:
                    securityStatus = "正常";
                    break;
                case 2:
                    securityStatus = "预警";
                    break;
                case 3:
                    securityStatus = "报警";
                    break;
            }
            tvSecurityStatus.setText("安防状态：" + securityStatus);
        } else {
            tvRegisterNum.setVisibility(View.GONE);
            btnCheckIn.setVisibility(View.VISIBLE);
            btnCheckOut.setVisibility(View.GONE);
            btnEditRoom.setVisibility(View.GONE);
            tvSecurityStatus.setText("安防状态：---");
        }

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                selectRegisterNum(1, roomId, dataBean.getBedNum(), dataBean.getRoomNo());
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                LoadingUtils.getInstance().showLoading(getActivity(), "请求中");
                mPresenter.checkOut(roomId);
            }
        });

        btnEditRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                selectRegisterNum(2, roomId, dataBean.getBedNum(), dataBean.getRoomNo());
            }
        });
    }


    /**
     * 选择入住人数
     *
     * @param
     */
    private void selectRegisterNum(final int type, final String roomId, int bedNum, String roomNo) {
        if (bedNum == 0) {
            ToastUtils.getInstance().showToast("房间无床铺");
            return;
        }

        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_edit_room, null);
        popupWindow = new PopupWindow(contentView, FormatUtils.getInstance().dp2px(455),
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAtLocation(getActivity().findViewById(R.id.ctl_main),
                Gravity.CENTER, 0, 0);
        StatusBarUtils.getInstance().setBackgroundAlpha(getActivity(), true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                StatusBarUtils.getInstance().setBackgroundAlpha(getActivity(), false);
            }
        });


        ImageView ivBack = contentView.findViewById(R.id.iv_cancel_edit);
        TextView tvRoomNo = contentView.findViewById(R.id.tv_edit_room_no);
        TextView tvTitle = contentView.findViewById(R.id.tv_edit_room);

        RecyclerView rvSelect = contentView.findViewById(R.id.rv_room_select_num);
        Button btn = contentView.findViewById(R.id.btn_confirm_edit);


        tvRoomNo.setText(roomNo + "号房");
        if (type == 1) {
            tvTitle.setText("登记入住人数");
        } else {
            tvTitle.setText("修改入住人数");
        }


        bedList.clear();

        for (int i = 1; i <= bedNum; i++) {
            bedList.add(new RoomListBean(i, false));
        }

        rvSelect.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        BaseQuickAdapter<RoomListBean, BaseViewHolder> roomAdapter = new BaseQuickAdapter<RoomListBean,
                BaseViewHolder>(R.layout.rv_select_room_item, bedList) {
            @Override
            protected void convert(BaseViewHolder helper, RoomListBean item) {
                TextView tvNum = helper.getView(R.id.tv_check_in_num);
                tvNum.setText(item.getNum() + "人");
                if (item.isSelect()) {
                    tvNum.setTextColor(ContextCompat.getColor(getActivity(), R.color.blue_3d9));
                    tvNum.setBackgroundResource(R.drawable.bg_room_select);
                } else {
                    tvNum.setTextColor(ContextCompat.getColor(getActivity(), R.color.gray_55));
                    tvNum.setBackgroundResource(R.drawable.bg_room_normal);
                }
            }

        };

        rvSelect.setAdapter(roomAdapter);


        roomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                for (int i = 0; i < bedList.size(); i++) {
                    bedList.get(i).setSelect(i == position);
                }

                adapter.notifyDataSetChanged();
            }
        });



        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int num = 0;
                for (RoomListBean roomListBean : bedList) {
                    if (roomListBean.isSelect()) {
                        num = roomListBean.getNum();
                        break;
                    }
                }
                if (num == 0) {
                    ToastUtils.getInstance().showToast("请选择入住人数");
                    return;
                }

                LoadingUtils.getInstance().showLoading(getActivity(), "请求中");
                if (type == 1) {
                    mPresenter.checkIn(roomId, num);
                } else {
                    mPresenter.updateRoom(roomId, num);
                }
            }
        });

    }
}
