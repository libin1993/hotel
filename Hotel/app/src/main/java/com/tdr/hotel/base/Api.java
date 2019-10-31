package com.tdr.hotel.base;


import com.tdr.hotel.bean.AlarmRecordBean;
import com.tdr.hotel.bean.RoomBean;
import com.tdr.hotel.bean.RoomDetailBean;
import com.tdr.hotel.bean.UserBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author：Libin on 2019/05/31 09:34
 * Email：1993911441@qq.com
 * Describe：接口
 */
public interface Api {

    String HOST = "http://183.129.130.119:12041/";

    //登录
    @FormUrlEncoded
    @POST("api/Account/Login")
    Observable<UserBean> login(@Field("LoginName") String account, @Field("Password") String password);

    //全部房间
    @GET("api/RoomManagement/GetAllRoomList")
    Observable<RoomBean> getAllRoomList(@Query("CompanyId") String id);

    //安防预警
    @GET("api/RoomManagement/GetSafetyWarnRoomList")
    Observable<RoomBean> getSafetyWarnRoomList(@Query("CompanyId") String id);

    //烟雾预警
    @GET("api/RoomManagement/GetSmokeWarnRoomList")
    Observable<RoomBean> getSmokeWarnRoomList(@Query("CompanyId") String id);

    //房间详情
    @GET("api/RoomManagement/GetRoomDetail")
    Observable<RoomDetailBean> getRoomDetail(@Query("RoomId") String roomId);

    //搜索房间
    @GET("api/RoomManagement/GetAllRoomList")
    Observable<RoomBean> searchRoom(@Query("CompanyId") String id, @Query("RoomNo") String roomNo);

    //获取房间号列表
    @GET("api/RoomManagement/GetRoomNoList")
    Observable<RoomBean> getRoomList(@Query("CompanyId") String id, @Query("Tag") int tag);

    //入住
    @FormUrlEncoded
    @POST("api/RoomManagement/MoveIntoHourse")
    Observable<BaseBean> checkInRoom(@Field("RoomId") String roomId, @Field("RegisterCount") int registerCount);

    //退房
    @FormUrlEncoded
    @POST("api/RoomManagement/CheckOutHourse")
    Observable<BaseBean> checkOutRoom(@Field("RoomId") String roomId);

    //报警记录
    @GET("api/RoomManagement/GetAlarmRecord")
    Observable<AlarmRecordBean> alarmRecord(@Query("CompanyId") String id);

    //修改房间信息
    @FormUrlEncoded
    @POST("api/RoomManagement/UpdateRoom")
    Observable<BaseBean> updateRoom(@Field("RoomId") String roomId, @Field("RegisterCount") int registerCount);

}

