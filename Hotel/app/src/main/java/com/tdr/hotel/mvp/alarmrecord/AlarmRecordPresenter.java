package com.tdr.hotel.mvp.alarmrecord;

import com.tdr.hotel.base.BasePresenter;
import com.tdr.hotel.base.BasePresenterImpl;
import com.tdr.hotel.base.RxObserver;
import com.tdr.hotel.base.Transformer;
import com.tdr.hotel.bean.AlarmRecordBean;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.inter.Callback;
import com.tdr.hotel.utils.RetrofitUtils;
import com.tdr.hotel.utils.SPUtils;

/**
 * Author：Libin on 2019/6/11 16:18
 * Description：
 */
public class AlarmRecordPresenter extends BasePresenterImpl<AlarmRecordContact.View> implements AlarmRecordContact.Presenter {

    @Override
    public void initRecord(String id) {
        if (!isViewAttached())
            return;

        RetrofitUtils.getInstance()
                .getService()
                .alarmRecord(SPUtils.getInstance().getToken())
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<AlarmRecordBean>() {
                    @Override
                    public void onSuccess(AlarmRecordBean alarmRecordBean) {
                        getView().hideLoading();
                        getView().onSuccess(alarmRecordBean.getData());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));
    }
}
