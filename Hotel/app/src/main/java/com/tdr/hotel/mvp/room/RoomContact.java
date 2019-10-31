package com.tdr.hotel.mvp.room;

import com.tdr.hotel.base.BasePresenter;
import com.tdr.hotel.base.BaseView;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.bean.RoomDetailBean;
import com.tdr.hotel.bean.UserBean;

import java.util.List;

/**
 * Author：Libin on 2019/6/6 13:35
 * Description：
 */
public class RoomContact {
    interface View extends BaseView {

        void roomDetail(RoomDetailBean.DataBean dataBean, String roomId);

        void editSuccess(String msg);
    }

    interface Presenter extends BasePresenter<View> {

        void initRoom(int type);

        void searchRoom(String roomNo);

        void getRoomDetail(String roomId);

        void checkIn(String roomId, int registerNum);

        void checkOut(String roomId);

        void updateRoom(String roomId, int registerNum);
    }
}

