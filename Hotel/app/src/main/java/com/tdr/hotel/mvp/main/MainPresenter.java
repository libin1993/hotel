package com.tdr.hotel.mvp.main;

import com.tdr.hotel.base.BaseBean;
import com.tdr.hotel.base.BasePresenterImpl;
import com.tdr.hotel.base.RxObserver;
import com.tdr.hotel.base.Transformer;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.inter.Callback;
import com.tdr.hotel.utils.RetrofitUtils;
import com.tdr.hotel.utils.SPUtils;

/**
 * Author：Libin on 2019/6/6 11:21
 * Description：
 */
public class MainPresenter extends BasePresenterImpl<MainContact.View> implements MainContact.Presenter {

    @Override
    public void selectRoom(int tag) {
        if (!isViewAttached())
            return;

        RetrofitUtils.getInstance()
                .getService()
                .getRoomList(SPUtils.getInstance().getToken(), tag)
                .compose(Transformer.switchSchedulers())
                .subscribe(new RxObserver(new Callback<RoomBean>() {


                    @Override
                    public void onSuccess(RoomBean roomBean) {
                        getView().onSuccess(roomBean.getData());
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
}
