package com.tdr.hotel.bean;

import com.google.gson.annotations.SerializedName;
import com.tdr.hotel.base.BaseBean;

/**
 * Author：Libin on 2019/6/10 16:11
 * Description：
 */
public class UserBean extends BaseBean<UserBean.DataBean> {


    public static class DataBean {
        /**
         * Id : 1
         * LoginName : admin
         * Password : e1adc3949ba59abbe56e057f2f883e
         * TraveIindustryName : 系统管理员
         * Contact : 18358581111
         * Status : 1
         * CreatorId : 1
         * CreateTime : 2019-06-10T10:33:08
         */

        private int Id;
        private String LoginName;
        private String Password;
        private String TraveIindustryName;
        private String Contact;
        private int Status;
        private int CreatorId;
        private String CreateTime;

        public String getCompanyId() {
            return CompanyId;
        }

        public void setCompanyId(String companyId) {
            CompanyId = companyId;
        }

        private String CompanyId;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String LoginName) {
            this.LoginName = LoginName;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String Password) {
            this.Password = Password;
        }

        public String getTraveIindustryName() {
            return TraveIindustryName;
        }

        public void setTraveIindustryName(String TraveIindustryName) {
            this.TraveIindustryName = TraveIindustryName;
        }

        public String getContact() {
            return Contact;
        }

        public void setContact(String Contact) {
            this.Contact = Contact;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public int getCreatorId() {
            return CreatorId;
        }

        public void setCreatorId(int CreatorId) {
            this.CreatorId = CreatorId;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }
    }
}
