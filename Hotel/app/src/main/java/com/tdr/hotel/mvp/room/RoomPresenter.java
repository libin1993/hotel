package com.tdr.hotel.mvp.room;

import android.text.TextUtils;

import com.tdr.hotel.base.Api;
import com.tdr.hotel.base.BaseBean;
import com.tdr.hotel.base.BasePresenterImpl;
import com.tdr.hotel.base.RxObserver;
import com.tdr.hotel.base.Transformer;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.bean.RoomDetailBean;
import com.tdr.hotel.inter.Callback;
import com.tdr.hotel.utils.RetrofitUtils;
import com.tdr.hotel.utils.SPUtils;

import io.reactivex.Observable;

/**
 * Author：Libin on 2019/6/6 13:35
 * Description：
 */
public class RoomPresenter extends BasePresenterImpl<RoomContact.View> implements RoomContact.Presenter {


    @Override
    public void initRoom(int type) {
        if (!isViewAttached())
            return;

        Api service = RetrofitUtils.getInstance().getService();
        Observable<RoomBean> observable;
        switch (type) {
            case 2:
                observable = service.getSafetyWarnRoomList(SPUtils.getInstance().getToken());
                break;
            case 3:
                observable = service.getSmokeWarnRoomList(SPUtils.getInstance().getToken());
                break;
            default:
                observable = service.getAllRoomList(SPUtils.getInstance().getToken());
                break;
        }


        observable.compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<RoomBean>() {
                    @Override
                    public void onSuccess(RoomBean roomBean) {
                        getView().hideLoading();
                        getView().onSuccess(roomBean.getData());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));
    }


    @Override
    public void searchRoom(String roomNo) {
        if (!isViewAttached())
            return;

        RetrofitUtils.getInstance()
                .getService()
                .searchRoom(SPUtils.getInstance().getToken(), roomNo)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<RoomBean>() {
                    @Override
                    public void onSuccess(RoomBean roomBean) {
                        getView().hideLoading();
                        getView().onSuccess(roomBean.getData());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));
    }

    @Override
    public void getRoomDetail(final String roomId) {
        if (!isViewAttached())
            return;

        RetrofitUtils.getInstance()
                .getService()
                .getRoomDetail(roomId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<RoomDetailBean>() {

                    @Override
                    public void onSuccess(RoomDetailBean roomDetailBean) {
                        getView().roomDetail(roomDetailBean.getData(), roomId);
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().onFail(msg);
                    }
                }));
    }

    @Override
    public void checkIn(String roomId, int registerNum) {
        if (!isViewAttached())
            return;

        RetrofitUtils.getInstance()
                .getService()
                .checkInRoom(roomId, registerNum)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<BaseBean>() {

                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        getView().hideLoading();
                        getView().editSuccess(baseBean.getMsg());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));

    }

    @Override
    public void checkOut(String roomId) {
        if (!isViewAttached())
            return;


        RetrofitUtils.getInstance()
                .getService()
                .checkOutRoom(roomId)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<BaseBean>() {

                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        getView().hideLoading();
                        getView().editSuccess(baseBean.getMsg());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));

    }

    @Override
    public void updateRoom(String roomId, int registerNum) {
        if (!isViewAttached())
            return;

        RetrofitUtils.getInstance()
                .getService()
                .updateRoom(roomId, registerNum)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<BaseBean>() {

                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        getView().hideLoading();
                        getView().editSuccess(baseBean.getMsg());
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().hideLoading();
                        getView().onFail(msg);
                    }
                }));

    }
}
