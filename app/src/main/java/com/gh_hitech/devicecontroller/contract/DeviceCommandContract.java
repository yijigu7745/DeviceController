package com.gh_hitech.devicecontroller.contract;

import com.gh_hitech.devicecontroller.model.CommandBean;
import com.gh_hitech.devicecontroller.model.ResultModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author yijigu
 */
public interface DeviceCommandContract {
    interface Model {
        /**
         * 获取设备时间
         * @param commandBean  指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> getDeviceTime(@Body CommandBean commandBean);

        /**
         * 获取继电器各路状态
         * @param commandBean 指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> getDeviceLineStatus(@Body CommandBean commandBean);

        /**
         * 设置设备时间
         * @param commandBean 指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> setDeviceTime(@Body CommandBean commandBean);

        /**
         * 打开单路继电器
         * @param commandBean 指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> turnOnLine(@Body CommandBean commandBean);

        /**
         * 关闭单路继电器
         * @param commandBean 指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> turnOffLine(@Body CommandBean commandBean);

        /**
         * 延时控制单路继电器
         * @param commandBean 指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> delayTurnLine(@Body CommandBean commandBean);

        /**
         * 控制所有继电器状态
         * @param commandBean 指令
         * @return
         */
        @POST("/device/control")
        Observable<ResultModel<String>> switchAllLineStatus(@Body CommandBean commandBean);

    }

    interface View {
    }

    interface Presenter {
        /**
         * 获取设备时间
         * @param commandBean  指令
         * @return
         */
        Observable getDeviceTime(CommandBean commandBean);

        /**
         * 获取继电器各路状态
         * @param commandBean 指令
         * @return
         */
        Observable getDeviceLineStatus(CommandBean commandBean);

        /**
         * 设置设备时间
         * @param commandBean 指令
         * @return
         */
        Observable setDeviceTime(CommandBean commandBean);

        /**
         * 打开单路继电器
         * @param commandBean 指令
         * @return
         */
        Observable turnOnLine(CommandBean commandBean);

        /**
         * 关闭单路继电器
         * @param commandBean 指令
         * @return
         */
        Observable turnOffLine(CommandBean commandBean);

        /**
         * 延时控制单路继电器
         * @param commandBean 指令
         * @return
         */
        Observable delayTurnLine(CommandBean commandBean);

        /**
         * 控制所有继电器状态
         * @param commandBean 指令
         * @return
         */
        Observable switchAllLineStatus(CommandBean commandBean);

    }
}
