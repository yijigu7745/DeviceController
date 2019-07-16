package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.DeviceContract;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.utils.Constants;

import java.util.List;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author yijigu
 */
public class DevicePresenter implements DeviceContract.Presenter {

    private IView iView;

    public DevicePresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public Observable getDeviceList() {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceContract.Model.class)
                .getDeviceList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable addDevice(DeviceBean deviceBean) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceContract.Model.class)
                .addDevice(deviceBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable deleteDevice(Long id) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceContract.Model.class)
                .deleteDevice(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
