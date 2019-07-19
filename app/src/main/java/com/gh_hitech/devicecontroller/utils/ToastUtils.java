package com.gh_hitech.devicecontroller.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void longTimeText(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_LONG).show();

    }

    public static void shortTimeText(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
