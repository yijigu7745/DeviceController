package com.gh_hitech.devicecontroller.login.presenter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gh_hitech.devicecontroller.login.contract.LoginContract;
import com.gh_hitech.devicecontroller.login.loginbean.DeviceBindInfoBean;
import com.gh_hitech.devicecontroller.login.model.LoginModel;
import com.gh_hitech.devicecontroller.retrofit.datamanager.DataManager;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * @author Administrator
 */
public class LoginPresenter implements LoginContract.Presenter {

    public static final String TAG = "LoginPresenter";

    @NonNull
    private final LoginContract.View mLoginView;

    @NonNull
    private LoginContract.Model mLoginModel;

    @SuppressLint("RestrictedApi")
    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = checkNotNull(loginView);
        mLoginModel = new LoginModel(mLoginView);
        mLoginView.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void login(DeviceBindInfoBean deviceBindInfoBean) {
        mLoginModel.deviceBind(deviceBindInfoBean)
                .subscribe(resultModel -> {
                    if (resultModel != null) {
                        Log.i(TAG, "getToken(): " + resultModel.getData().getToken());
                        DataManager.putToken(resultModel.getData().getToken());
                    }
                }, error -> error.printStackTrace());
    }

}
