package com.gh_hitech.devicecontroller.model;

import com.gh_hitech.devicecontroller.contract.CardListContract;

import io.reactivex.Observable;

public class CardListModel implements CardListContract.Model {
    @Override
    public Observable<CardListBean> getCardList() {
        return null;
    }
}
