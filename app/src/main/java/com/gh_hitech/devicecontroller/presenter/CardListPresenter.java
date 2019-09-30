package com.gh_hitech.devicecontroller.presenter;

import com.gh_hitech.devicecontroller.constants.Constants;
import com.gh_hitech.devicecontroller.contract.CardListContract;

import cn.com.yijigu.rxnetwork.retrofit.RetrofitUtils;
import cn.com.yijigu.rxnetwork.view.IView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardListPresenter implements CardListContract.Presenter {

    private IView iView;

    public CardListPresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public Observable getCardList() {
        return RetrofitUtils.getInterface(Constants.URL,iView,CardListContract.Model.class)
                .getCardList()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
