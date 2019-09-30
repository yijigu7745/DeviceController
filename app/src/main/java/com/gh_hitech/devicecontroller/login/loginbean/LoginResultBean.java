package com.gh_hitech.devicecontroller.login.loginbean;

/**
 * 登录成功后服务器返回的实体类
 * 获取token，用于下次与服务器访问使用
 *
 * @author Administrator
 */
public class LoginResultBean {
    /**
     * code : 0
     * msg : 操作成功
     * time : 1569297390
     * data : {"device_id":"2","token":"a6809c312ad2da7d34b32c375e12c1b2"}
     */

    private int code;
    private String msg;
    private int time;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * device_id : 2
         * token : a6809c312ad2da7d34b32c375e12c1b2
         */

        private String device_id;
        private String token;

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
