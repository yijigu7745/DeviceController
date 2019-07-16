package com.gh_hitech.devicecontroller.model;

/**
 * @author yijigu
 */
public class CommandBean {
    private String command;
    private String deviceName;

    public CommandBean(String command, String deviceName) {
        this.command = command;
        this.deviceName = deviceName;
    }

    public CommandBean() {
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
