package com.gh_hitech.devicecontroller.login.contract;

import com.gh_hitech.devicecontroller.base.BasePresenter;
import com.gh_hitech.devicecontroller.base.BaseView;
import com.gh_hitech.devicecontroller.login.loginbean.DeviceBindInfoBean;
import com.gh_hitech.devicecontroller.login.loginbean.LoginResultBean;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Administrator
 */
public interface LoginContract {
    interface Model {


        /**
         * 设备绑定
         *
         * @param deviceBindInfoBean
         * @return
         */
        @POST("/app/device/bind")
        Observable<LoginResultBean> deviceBind(@Body DeviceBindInfoBean deviceBindInfoBean);
    }

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

        /**
         * 登录
         *
         * @param deviceBindInfoBean
         */
        void login(DeviceBindInfoBean deviceBindInfoBean);
    }
}
