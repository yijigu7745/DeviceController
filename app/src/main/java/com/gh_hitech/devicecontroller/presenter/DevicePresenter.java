package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.DeviceContract;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.utils.Constants;

import java.util.List;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;

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
        Observable<ResultModel<List<DeviceBean>>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceContract.Model.class)
                .getDeviceList();
        return observable;
    }

    @Override
    public Observable addDevice(DeviceBean deviceBean) {
        Observable<ResultModel<DeviceBean>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceContract.Model.class)
                .addDevice(deviceBean);
        return observable;
    }

    @Override
    public Observable deleteDevice(Long id) {
        Observable<ResultModel<Void>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, DeviceContract.Model.class)
                .deleteDevice(id);
        return observable;
    }
}
