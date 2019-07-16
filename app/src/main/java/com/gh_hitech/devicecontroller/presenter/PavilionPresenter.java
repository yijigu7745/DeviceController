package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.DeviceContract;
import com.gh_hitech.devicecontroller.contract.PavilionContract;
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
public class PavilionPresenter implements PavilionContract.Presenter {

    private IView iView;

    public PavilionPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public Observable getPavilionList() {
        Observable<ResultModel<List<DeviceBean.PavilionBean>>> observable = RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                PavilionContract.Model.class).getPavilionList();
        return observable;
    }

    @Override
    public Observable selectPavilionListByName(String name) {
        Observable<ResultModel<List<DeviceBean.PavilionBean>>> observable = RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class).selectPavilionListByName(name);
        return observable;
    }

    @Override
    public Observable selectPavilionListById(int id) {
        Observable<ResultModel<DeviceBean.PavilionBean>> observable = RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class).selectPavilionListById(id);
        return observable;
    }

    @Override
    public Observable<ResultModel<Void>> addPavilion(DeviceBean.PavilionBean pavilionBean) {
        Observable<ResultModel<Void>> observable = RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class).addPavilion(pavilionBean);
        return observable;
    }

    @Override
    public Observable deletePavilion(Long pavilionId) {
        Observable<ResultModel<Void>> observable = RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class).deletePavilion(pavilionId);
        return observable;
    }

}
