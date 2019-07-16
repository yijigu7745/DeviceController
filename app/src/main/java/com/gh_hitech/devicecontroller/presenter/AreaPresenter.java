package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.AreaContract;
import com.gh_hitech.devicecontroller.model.AreaBean;
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
public class AreaPresenter implements AreaContract.Presenter {

    private IView iView;

    public AreaPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public Observable getAreaList() {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, AreaContract.Model.class)
                .getAreaList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable getAreaListByParentCodeLike(String parentCode) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, AreaContract.Model.class)
                .getAreaListByParentCodeLike(parentCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable getAreaListByAreaCode(String areaCode) {
        return RetrofitUtils.getInterface(
                Constants.getServerUrl(),
                iView, AreaContract.Model.class)
                .getAreaListByAreaCode(areaCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
