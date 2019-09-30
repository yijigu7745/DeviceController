package com.gh_hitech.devicecontroller.contract;


import com.gh_hitech.devicecontroller.model.CardListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface CardListContract {
    interface Model {
        @GET("cardlist?networktype=4g&sensors_device_id=none&orifid=231093_-_selffollowed&uicode=10000011&moduleID=708&wb_version=4033&c=android&s=5658f76c&ft=0&ua=LeMobile-Le%20x620__weibo__9.8.4__android__android6.0&wm=20006_0002&aid=01A8wo9U57BXIVRfsqI3et2M_RWoQz_IaraE6coci5WiI93oA.&ext=orifid%3A231093_-_selffollowed%7Coriuicode%3A10000011&fid=231051_-_fans_-_6297323754&uid=7300314852&v_f=2&v_p=76&from=1098495010&gsid=_2A25whoFqDeRxGeFN61IS8SrEzj6IHXVRFZOirDV6PUJbkdAKLWatkWpNQJu3Z4morExGG0IWI7N12yDDdRnK2zj2&imsi=460110418689403&lang=zh_CN&lfid=1076036297323754_-_WEIBO_SECOND_PROFILE_WEIBO&page=1&skin=default&count=20&oldwm=20006_0002&sflag=1&oriuicode=10000011&containerid=231051_-_fans_-_6297323754&ignore_inturrpted_error=true&luicode=10000198&sensors_mark=0&android_id=434d370adba92ce9&client_key=1799aaa58503537c953cbd2656cffbc6_1224&need_new_pop=1&sensors_is_first_day=none&need_head_cards=0&cum=84830BAD&since_id=2")
        Observable<CardListBean> getCardList();
    }

    interface View {
    }

    interface Presenter {

        Observable getCardList();
    }
}
