package com.tdr.hotel.mvp.main;

import com.tdr.hotel.base.BasePresenter;
import com.tdr.hotel.base.BaseView;
import com.tdr.hotel.bean.RoomBean;

/**
 * Author：Libin on 2019/6/6 11:21
 * Description：
 */
public class MainContact {
    interface View extends BaseView {

        void editSuccess(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void selectRoom(int tag);

        void checkIn(String roomId, int registerNum);

        void checkOut(String roomId);
    }
}
