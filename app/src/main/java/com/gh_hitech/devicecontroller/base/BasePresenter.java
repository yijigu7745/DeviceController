package com.gh_hitech.devicecontroller.base;

/**
 * @author Administrator
 */
public interface BasePresenter {

    /**
     * 订阅事件
     */
    void subscribe();

    /**
     * 取消订阅
     */
    void unsubscribe();

}
