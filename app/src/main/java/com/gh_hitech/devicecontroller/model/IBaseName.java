package com.gh_hitech.devicecontroller.model;

/**
 * @author yijigu
 */
public abstract class IBaseName {
    public boolean isCheck = false;

    /**
     * 获取name
     *
     * @return
     */
    public abstract String getIName();

    /**
     * 获取id
     *
     * @return
     */
    public abstract Long getIid();
}