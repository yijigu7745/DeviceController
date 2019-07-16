package com.gh_hitech.devicecontroller.contract;

import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author yijigu
 */
public interface PavilionContract {
    interface Model {
        /**
         * 获取警银亭列表
         * @return
         */
        @GET("/pavilion/all")
        Observable<ResultModel<List<DeviceBean.PavilionBean>>> getPavilionList();

        /**
         * 添加警银亭
         * @param pavilionBean
         * @return
         */
        @POST("/pavilion")
        Observable<ResultModel<Void>> addPavilion(@Body DeviceBean.PavilionBean pavilionBean);

        /**
         * 删除警银亭
         * @param pavilionId
         * @return
         */
        @DELETE("/pavilion/{pavilionId}")
        Observable<ResultModel<Void>> deletePavilion(@Path("pavilionId") Long pavilionId);

        /**
         * 查找警银亭
         * @param id 警银亭
         * @return
         */
        @GET("/pavilion/id")
        Observable<ResultModel<DeviceBean.PavilionBean>> selectPavilionListById(@Query("id") int id);

        /**
         * 查找警银亭
         * @param name 警银亭
         * @return
         */
        @GET("/pavilion/name")
        Observable<ResultModel<List<DeviceBean.PavilionBean>>> selectPavilionListByName(@Query("name") String name);
    }

    interface View {
    }

    interface Presenter {
        /**
         * 获取警银亭列表
         * @return
         */
        Observable getPavilionList();

        /**
         * 查找警银亭
         * @param name
         * @return
         */
        Observable selectPavilionListByName(String name);

        /**
         * 查找警银亭
         * @param id
         * @return
         */
        Observable selectPavilionListById(int id);

        /**
         * 添加警银亭
         * @param pavilionBean
         * @return
         */
        Observable addPavilion(DeviceBean.PavilionBean pavilionBean);

        /**
         * 删除警银亭
         * @param pavilionId
         * @return
         */
        Observable deletePavilion(Long pavilionId);
    }
}
