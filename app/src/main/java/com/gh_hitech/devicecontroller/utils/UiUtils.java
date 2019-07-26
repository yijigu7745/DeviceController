package com.gh_hitech.devicecontroller.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

/**
 * @author yijigu
 */
public class UiUtils {
    public static Thread getMainThread() {
        return ApplicationUtils.getMainThread();
    }

    /**
     * dip转换px
     */
    public static int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    public static Context getContext() {
        return ApplicationUtils.getApplication();
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(float px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return ApplicationUtils.getMainThreadHandler();
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 根据图片名获取图片的id
     */
    public static int getImageIdWithName(String imageName) {
        Context ctx = getContext();
        int resId = getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        return resId;
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    public static long getMainThreadId() {
        return ApplicationUtils.getMainThreadId();
    }

    /**
     * 修改tv部分字的颜色
     *
     * @param textView
     * @param color
     * @param start
     * @param end
     */
    public static void setTextColor(TextView textView, int color, int start, int end) {
        if (textView != null && !TextUtils.isEmpty(textView.getText().toString()) && end > start && start >= 0 && end > 0) {
            SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            builder.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);
        }
    }

    /**
     * 设置最后的几个字符的颜色
     *
     * @param textView
     * @param color
     * @param lastCount
     */
    public static void setTextColor(TextView textView, int color, int lastCount) {
        if (textView != null && !TextUtils.isEmpty(textView.getText().toString())) {
            SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
            ForegroundColorSpan span = new ForegroundColorSpan(color);
            int totleLenth = textView.getText().toString().length();
            if (totleLenth < lastCount) return;
            builder.setSpan(span, totleLenth - lastCount, totleLenth, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(builder);
        }
    }

    /**
     * 获取屏幕的分辨率
     */
    @SuppressWarnings("deprecation")
    public static int[] getResolution() {
        Context context = UiUtils.getContext();
        if (null == context) {
            return null;
        }
        WindowManager windowMgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int[] res = new int[2];
        res[0] = windowMgr.getDefaultDisplay().getWidth();
        res[1] = windowMgr.getDefaultDisplay().getHeight();
        return res;
    }

    /**
     * 把Bitmap转Byte
     */
    public static byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        return baos.toByteArray();
    }
}
