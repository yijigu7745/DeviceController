package com.gh_hitech.devicecontroller.retrofit.network;

import android.content.Context;

import com.gh_hitech.devicecontroller.retrofit.SSLSocketClient;
import com.gh_hitech.devicecontroller.retrofit.interceptor.CacheInterceptor;
import com.gh_hitech.devicecontroller.retrofit.interceptor.CookieInterceptor;
import com.gh_hitech.devicecontroller.retrofit.interceptor.TokenHeadersInterceptor;

import java.util.concurrent.TimeUnit;

import cn.com.yijigu.rxnetwork.utils.Constants;
import okhttp3.OkHttpClient;

/**
 * @author yijigu
 */
public class HttpProvider {

    static OkHttpClient okHttpClient;

    public static OkHttpClient Builder(Context context){
        if (okHttpClient==null) {
            synchronized (HttpProvider.class) {
                if (okHttpClient==null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .sslSocketFactory(SSLSocketClient.getSSLSocketFactory(),SSLSocketClient.getX509TrustManager())
                            .addInterceptor(new CookieInterceptor())
                            .retryOnConnectionFailure(true)
                            .addNetworkInterceptor(new CacheInterceptor())
                            .cache(new CacheProvider(context).provideCache())
                            .connectTimeout(Constants.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(new TokenHeadersInterceptor(context))
                            .build();
                    okHttpClient = client;
                }
            }
        }
        return okHttpClient;
    }

    public static boolean checkNULL(String str)
    {
        return ((str == null) || ("null".equals(str)) || ("".equals(str)));
    }
}
