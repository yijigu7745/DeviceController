package com.gh_hitech.devicecontroller.contract;

import com.gh_hitech.devicecontroller.model.DeviceBean;
import com.gh_hitech.devicecontroller.model.ResultModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * @author yijigu
 */
public interface DeviceContract {
    interface Model {
        /**
         * 获取设备列表
         * @return
         */
        @GET("/device/all")
        Observable<ResultModel<List<DeviceBean>>> getDeviceList();

        /**
         * 添加设备
         * @param deviceBean 设备
         * @return
         */
        @POST("/device")
        Observable<ResultModel<DeviceBean>> addDevice(@Body DeviceBean deviceBean);

        /**
         * 删除设备
         * @param id 设备ID
         * @return
         */
        @DELETE("/device/{id}")
        Observable<ResultModel<Void>> deleteDevice(@Path("id") Long id);

        /**
         * 更新设备
         * @param deviceBean
         * @return
         */
        @PUT("/device/{id}")
        Observable<ResultModel<Void>> updateDevice(@Body DeviceBean deviceBean);
    }

    interface View {
    }

    interface Presenter {
        /**
         * 获取设备列表
         * @return
         */
        Observable getDeviceList();

        /**
         * 添加设备
         * @param deviceBean
         * @return
         */
        Observable addDevice(DeviceBean deviceBean);

        /**
         * 删除设备
         * @param id
         * @return
         */
        Observable deleteDevice(Long id);

        /**
         * 更新设备
         * @param deviceBean
         * @return
         */
        Observable updateDevice(DeviceBean deviceBean);
    }
}
