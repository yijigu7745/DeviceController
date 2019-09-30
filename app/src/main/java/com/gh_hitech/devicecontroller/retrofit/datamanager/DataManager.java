package com.gh_hitech.devicecontroller.retrofit.datamanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.gh_hitech.devicecontroller.retrofit.application.RetrofitUtilsApplication;

/**
 * @author yijigu
 */
public class DataManager {
    private static final String NAME = "RetrofitUtilsApplication";
    private static final String COOKIE = "cookie";
    private static final String TOKEN = "token";

    public static String getToken() {
        SharedPreferences preferences = RetrofitUtilsApplication.getRetrofitUtilsContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(TOKEN, "");
    }

    public static void putToken(String value) {
        SharedPreferences preferences = RetrofitUtilsApplication.getRetrofitUtilsContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(TOKEN, value).commit();
    }

    public static String getCookie() {
        SharedPreferences preferences = RetrofitUtilsApplication.getRetrofitUtilsContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getString(COOKIE, "");
    }
    public static void putCookie( String value) {
        SharedPreferences preferences = RetrofitUtilsApplication.getRetrofitUtilsContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(COOKIE, value).commit();
    }

    public static boolean getCustomValue(String customName) {
        SharedPreferences preferences = RetrofitUtilsApplication.getRetrofitUtilsContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return preferences.getBoolean(customName,false);
    }
    public static void putCustomValue(String customName, boolean customValue) {
        SharedPreferences preferences = RetrofitUtilsApplication.getRetrofitUtilsContext().getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(customName, customValue).commit();
    }



}
