package com.tdr.hotel.mvp.alarmrecord;

import com.tdr.hotel.base.BasePresenter;
import com.tdr.hotel.base.BasePresenterImpl;
import com.tdr.hotel.base.BaseView;
import com.tdr.hotel.bean.AlarmRecordBean;

/**
 * Author：Libin on 2019/6/11 16:15
 * Description：
 */
public class AlarmRecordContact {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        void initRecord(String id);
    }
}
