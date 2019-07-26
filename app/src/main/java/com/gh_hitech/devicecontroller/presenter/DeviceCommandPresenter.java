package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.DeviceCommandContract;
import com.gh_hitech.devicecontroller.model.CommandBean;
import com.gh_hitech.devicecontroller.utils.Constants;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yijigu
 */
public class DeviceCommandPresenter implements DeviceCommandContract.Presenter {
    private IView iView;

    public DeviceCommandPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public Observable getDeviceTime(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .getDeviceTime(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable getDeviceLineStatus(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .getDeviceLineStatus(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable setDeviceTime(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .setDeviceTime(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable turnOnLine(CommandBean commandBean) {

        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .turnOnLine(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable turnOffLine(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .turnOffLine(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable delayTurnLine(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .delayTurnLine(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable switchAllLineStatus(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceCommandContract.Model.class)
                .switchAllLineStatus(commandBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
