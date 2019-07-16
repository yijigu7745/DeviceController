package com.gh_hitech.devicecontroller.model;

/**
 * @author yijigu
 */

public enum Command {
    /**
     * 打开单路继电器,需添加路数
     */
    ON("on","打开单路继电器指令"),
    /**
     * 关闭单路继电器,需添加路数
     */
    OFF("off","关闭单路继电器指令"),
    /**
     * 读取继电器状态
     */
    READ("read","读取继电器状态指令"),
    /**
     * 控制所有继电器,需添加各路数开关状态
     */
    ALL("all","控制所有继电器指令"),
    /**
     * 延时控制继电器,需添加路数及时间
     */
    DELAY("on","延时控制指令"),
    /**
     * 读取设备时间
     */
    READTIME("rt:","读取设备时间指令"),
    /**
     * 设置设备时间
     */
    SETTIME("st:","设置设备时间指令");

    private String command;
    private String desc;

    Command(String command, String desc) {
        this.command = command;
        this.desc = desc;
    }

    public String getCommand() {
        return command;
    }

    public String getDesc() {
        return desc;
    }
}
