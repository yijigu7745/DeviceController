package com.gh_hitech.devicecontroller.retrofit.interceptor;

import android.util.Log;

import com.gh_hitech.devicecontroller.retrofit.datamanager.DataManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * @author yijigu
 */
public class CookieInterceptor implements Interceptor{
    public  static List<String> cookieSet=new ArrayList<>();
    @Override
    public Response intercept(Chain chain) throws IOException {

        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("set-cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            Observable.fromIterable(originalResponse.headers("set-cookie"))
                    .map(s -> {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    })
                    .observeOn(Schedulers.trampoline())
                    .subscribe(cookie -> {
                        if (cookie.contains("SESSIONID")){
                            cookieBuffer.append(cookie).append(";");
                            Log.d("TAG", "cookieBuffer: " + cookieBuffer.toString());
                        }
                    });
            Log.d("TAG", "Put cookie: " + cookieBuffer.toString());
            DataManager.putCookie(cookieBuffer.toString());
        }
        return originalResponse;
    }
}
