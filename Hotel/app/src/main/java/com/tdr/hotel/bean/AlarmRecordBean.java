package com.tdr.hotel.bean;

import com.tdr.hotel.base.BaseBean;

import java.util.List;

/**
 * Author：Libin on 2019/6/11 14:14
 * Description：
 */
public class AlarmRecordBean extends BaseBean<AlarmRecordBean.DataBean> {

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
             * Id : 1
             * CreateTime : 2019-06-10T11:41:50
             * RoomId : 1
             * RoomNo : 101
             * AlarmType : 2
             */

            private int Id;
            private String CreateTime;
            private String RoomId;
            private String RoomNo;
            private int AlarmType;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
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

            public void setRoomNo(String RoomNo) {
                this.RoomNo = RoomNo;
            }

            public int getAlarmType() {
                return AlarmType;
            }

            public void setAlarmType(int AlarmType) {
                this.AlarmType = AlarmType;
            }
        }
    }
}
