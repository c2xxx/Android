package com.chen.demo2019.bean;

/**
 * Created by ChenHui on 2019/3/31.
 */
public class UserInfo {

    /**
     * name : Ravi Tamada
     * email : ravi8x@gmail.com
     * phone : {"home":"08947 000000","mobile":"9999999999"}
     */

    private String name;
    private String email;
    private PhoneBean phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PhoneBean getPhone() {
        return phone;
    }

    public void setPhone(PhoneBean phone) {
        this.phone = phone;
    }

    public static class PhoneBean {
        /**
         * home : 08947 000000
         * mobile : 9999999999
         */

        private String home;
        private String mobile;

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
