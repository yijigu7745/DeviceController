package com.gh_hitech.devicecontroller.retrofit.network;

import android.content.Context;

import okhttp3.Cache;

/**
 * @author yijigu
 */
public class CacheProvider {
    Context mContext;

    public CacheProvider(Context context){
        this.mContext = context;
    }

    public Cache provideCache(){
        return new Cache(this.mContext.getCacheDir(),52428800L);
    }
}
