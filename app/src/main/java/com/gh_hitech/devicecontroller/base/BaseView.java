package com.gh_hitech.devicecontroller.base;

import android.content.Context;

/**
 * @author Administrator
 */
public interface BaseView<T> {

    /**
     * 设置Presenter
     *
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * 获取Context
     *
     * @return
     */
    Context getmContext();
}
