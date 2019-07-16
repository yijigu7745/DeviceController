package com.gh_hitech.devicecontroller.contract;

import com.gh_hitech.devicecontroller.model.AreaBean;
import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author yijigu
 */
public interface AreaContract {
    interface Model {
        /**
         * 获取区域列表
         * @return
         */
        @GET("/area/all")
        Observable<ResultModel<List<AreaBean>>> getAreaList();

        /**
         * 查询区域
         * @param areaCode
         * @return
         */
        @GET("/area/{code}")
        Observable<ResultModel<List<AreaBean>>> getAreaListByAreaCode(@Path("code") String areaCode);

        /**
         * 模糊查询区域列表
         * @param parentCode
         * @return
         */
        @GET("/area/codeLike/{parentCode}")
        Observable<ResultModel<List<AreaBean>>> getAreaListByParentCodeLike(@Path("parentCode") String parentCode);
    }

    interface View {
    }

    interface Presenter {
        /**
         * 获取区域列表
         * @return
         */
        Observable getAreaList();

        /**
         * 查询区域
         * @param areaCode
         * @return
         */
        Observable getAreaListByAreaCode(String areaCode);

        /**
         * 模糊查询区域列表
         * @param parentCode
         * @return
         */
        Observable getAreaListByParentCodeLike(String parentCode );
    }
}
