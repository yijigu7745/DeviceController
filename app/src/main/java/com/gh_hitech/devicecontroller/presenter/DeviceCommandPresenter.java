package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.DeviceCommandContract;
import com.gh_hitech.devicecontroller.model.Command;
import com.gh_hitech.devicecontroller.model.CommandBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.utils.Constants;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;

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
                iView,DeviceCommandContract.Model.class)
                .getDeviceTime(commandBean);
    }

    @Override
    public Observable getDeviceLineStatus(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView,DeviceCommandContract.Model.class)
                .getDeviceLineStatus(commandBean);
    }

    @Override
    public Observable setDeviceTime(CommandBean commandBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView,DeviceCommandContract.Model.class)
                .setDeviceTime(commandBean);
    }

    @Override
    public Observable turnOnLine(CommandBean commandBean) {

        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView,DeviceCommandContract.Model.class)
                .turnOnLine(commandBean);
    }

    @Override
    public Observable turnOffLine(CommandBean commandBean) {
        Observable<ResultModel<String>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView,DeviceCommandContract.Model.class)
                .turnOffLine(commandBean);
        return observable;
    }

    @Override
    public Observable delayTurnLine(CommandBean commandBean) {
        Observable<ResultModel<String>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView,DeviceCommandContract.Model.class)
                .delayTurnLine(commandBean);
        return observable;
    }

    @Override
    public Observable switchAllLineStatus(CommandBean commandBean) {
        Observable<ResultModel<String>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView,DeviceCommandContract.Model.class)
                .switchAllLineStatus(commandBean);
        return observable;
    }

}
