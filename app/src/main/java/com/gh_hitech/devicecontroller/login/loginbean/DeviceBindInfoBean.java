package com.gh_hitech.devicecontroller.login.loginbean;

/**
 * 设备登录绑定的实体类
 *
 * @author Administrator
 */
public class DeviceBindInfoBean {

    private String bind_user;
    private String bind_pass;
    private String device_code;
    private String device_alias;
    private String device_name;
    private String device_info;
    private String remark;
    private String version;
    private String battery_capacity;
    private String device_id;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public DeviceBindInfoBean setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDevice_id() {
        return device_id;
    }

    public DeviceBindInfoBean setDevice_id(String device_id) {
        this.device_id = device_id;
        return this;
    }

    public String getBind_user() {
        return bind_user;
    }

    public DeviceBindInfoBean setBind_user(String bind_user) {
        this.bind_user = bind_user;
        return this;
    }

    public String getBind_pass() {
        return bind_pass;
    }

    public DeviceBindInfoBean setBind_pass(String bind_pass) {
        this.bind_pass = bind_pass;
        return this;
    }

    public String getDevice_code() {
        return device_code;
    }

    public DeviceBindInfoBean setDevice_code(String device_code) {
        this.device_code = device_code;
        return this;
    }

    public String getDevice_alias() {
        return device_alias;
    }

    public DeviceBindInfoBean setDevice_alias(String device_alias) {
        this.device_alias = device_alias;
        return this;
    }

    public String getDevice_name() {
        return device_name;
    }

    public DeviceBindInfoBean setDevice_name(String device_name) {
        this.device_name = device_name;
        return this;
    }

    public String getDevice_info() {
        return device_info;
    }

    public DeviceBindInfoBean setDevice_info(String device_info) {
        this.device_info = device_info;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public DeviceBindInfoBean setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public DeviceBindInfoBean setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getBattery_capacity() {
        return battery_capacity;
    }

    public DeviceBindInfoBean setBattery_capacity(String battery_capacity) {
        this.battery_capacity = battery_capacity;
        return this;
    }
}
