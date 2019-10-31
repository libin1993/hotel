package com.tdr.hotel.bean;

import com.tdr.hotel.base.BaseBean;

/**
 * Author：Libin on 2019/6/11 12:40
 * Description：
 */
public class RoomDetailBean extends BaseBean<RoomDetailBean.DataBean> {

    public static class DataBean {
        /**
         * RoomNo : 101
         * BedNum : 2
         * RegisterCount : 2
         * HeadCount : 4
         * PersonNumStatus : 2
         * SmokeStatus : 2
         */

        private String RoomNo;
        private int BedNum;
        private int RegisterCount;
        private int HeadCount;
        private int PersonNumStatus;
        private int SmokeStatus;

        public String getRoomNo() {
            return RoomNo;
        }

        public void setRoomNo(String RoomNo) {
            this.RoomNo = RoomNo;
        }

        public int getBedNum() {
            return BedNum;
        }

        public void setBedNum(int BedNum) {
            this.BedNum = BedNum;
        }

        public int getRegisterCount() {
            return RegisterCount;
        }

        public void setRegisterCount(int RegisterCount) {
            this.RegisterCount = RegisterCount;
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
