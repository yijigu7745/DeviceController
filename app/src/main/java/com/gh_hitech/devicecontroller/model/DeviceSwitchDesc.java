package com.gh_hitech.devicecontroller.model;

/**
 * @author yijigu
 */
public class DeviceSwitchDesc {
    private int id;
    private int deviceId;
    private String switchDesc;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getSwitchDesc() {
        return switchDesc;
    }

    public void setSwitchDesc(String switchDesc) {
        this.switchDesc = switchDesc;
    }
}
