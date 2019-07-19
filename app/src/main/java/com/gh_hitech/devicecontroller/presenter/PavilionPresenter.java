package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.contract.PavilionContract;
import com.gh_hitech.devicecontroller.model.PavilionBean;
import com.gh_hitech.devicecontroller.model.ResultModel;
import com.gh_hitech.devicecontroller.utils.Constants;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
        return RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                PavilionContract.Model.class).getPavilionList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable selectPavilionListByName(String name) {
        return RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class)
                .selectPavilionListByName(name)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable selectPavilionListById(int id) {
        return RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class)
                .selectPavilionListById(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<ResultModel<Void>> addPavilion(PavilionBean pavilionBean) {
        return RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class)
                .addPavilion(pavilionBean)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable deletePavilion(Long pavilionId) {
        return RetrofitUtils
                .getInterface(Constants.getServerUrl(),iView,
                        PavilionContract.Model.class)
                .deletePavilion(pavilionId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
