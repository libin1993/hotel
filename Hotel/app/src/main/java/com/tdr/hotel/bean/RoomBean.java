package com.tdr.hotel.bean;

import com.google.gson.annotations.SerializedName;
import com.tdr.hotel.base.BaseBean;

import java.util.List;

/**
 * Author：Libin on 2019/6/10 16:52
 * Description：
 */
public class RoomBean extends BaseBean<RoomBean.DataBean> {

    public static class DataBean {
        private List<ListBean> List;

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public static class ListBean {
            /**
             * RoomId : 1
             * RoomNo : 101
             * HeadCount : 4
             * PersonNumStatus : 1
             * SmokeStatus : 1
             */

            private String RoomId;
            private String RoomNo;
            private int HeadCount;
            private int PersonNumStatus;
            private int SmokeStatus;
            private int RegisterCount;
            private int BedNum;

            public int getBedNum() {
                return BedNum;
            }

            public void setBedNum(int bedNum) {
                BedNum = bedNum;
            }

            public String getRoomId() {
                return RoomId;
            }

            public void setRoomId(String RoomId) {
                this.RoomId = RoomId;
            }

            public String getRoomNo() {
                return RoomNo;
            }

            public int getRegisterCount() {
                return RegisterCount;
            }

            public void setRegisterCount(int registerCount) {
                RegisterCount = registerCount;
            }

            public void setRoomNo(String RoomNo) {
                this.RoomNo = RoomNo;
            }

            public int getHeadCount() {
                return HeadCount;
            }

            public void setHeadCount(int HeadCount) {
                this.HeadCount = HeadCount;
            }

            public int getPersonNumStatus() {
                return PersonNumStatus;
            }

            public void setPersonNumStatus(int PersonNumStatus) {
                this.PersonNumStatus = PersonNumStatus;
            }

            public int getSmokeStatus() {
                return SmokeStatus;
            }

            public void setSmokeStatus(int SmokeStatus) {
                this.SmokeStatus = SmokeStatus;
            }
        }
    }
}
