package com.gh_hitech.devicecontroller.login.model;

import com.gh_hitech.devicecontroller.constants.Constants;
import com.gh_hitech.devicecontroller.login.contract.LoginContract;
import com.gh_hitech.devicecontroller.login.loginbean.DeviceBindInfoBean;
import com.gh_hitech.devicecontroller.login.loginbean.LoginResultBean;
import com.gh_hitech.devicecontroller.retrofit.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginModel implements LoginContract.Model {

    private LoginContract.View mView;

    public LoginModel(LoginContract.View mView) {
        this.mView = mView;
    }

    @Override
    public Observable<LoginResultBean> deviceBind(DeviceBindInfoBean deviceBindInfoBean) {
        return RetrofitUtils.getInterface(Constants.URL,mView.getmContext(), LoginContract.Model.class)
                .deviceBind(deviceBindInfoBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
