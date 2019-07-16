package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.AreaContract;
import com.gh_hitech.devicecontroller.model.AreaBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.utils.Constants;

import java.util.List;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;

/**
 * @author yijigu
 */
public class AreaPresenter implements AreaContract.Presenter {

    private IView iView;

    public AreaPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public Observable getAreaList() {
        Observable<ResultModel<List<AreaBean>>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, AreaContract.Model.class)
                .getAreaList();
        return observable;
    }

    @Override
    public Observable getAreaListByParentCodeLike(String parentCode) {
        Observable<ResultModel<List<AreaBean>>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, AreaContract.Model.class)
                .getAreaListByParentCodeLike(parentCode);
        return observable;
    }

    @Override
    public Observable getAreaListByAreaCode(String areaCode) {
        Observable<ResultModel<List<AreaBean>>> observable = RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, AreaContract.Model.class)
                .getAreaListByAreaCode(areaCode);
        return observable;
    }
}
