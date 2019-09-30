package com.gh_hitech.devicecontroller.retrofit;

import android.content.Context;

import com.gh_hitech.devicecontroller.retrofit.factory.MyGsonConverterFactory;
import com.gh_hitech.devicecontroller.retrofit.network.HttpProvider;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Retrofit网络请求接口
 * @author yijigu
 */
public class RetrofitUtils {
    private static Retrofit mRetrofit;

    /**
     * @param url 请求路径
     * @param iContext 上下文对象
     * @param iService 请求接口的引用
     * @param <T>
     * @return
     */
    public static <T> T getInterface(String url, Context iContext, Class<T> iService){

        if(StringUtils.isEmpty(url)){
            throw new NullPointerException("url can't be null");
        }

        if(iContext == null){
            throw new NullPointerException("iContext can't be null");
        }

        mRetrofit = new Retrofit.Builder()
                .client(HttpProvider.Builder(iContext))
                .baseUrl(url)
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit.create(iService);
    }
}
