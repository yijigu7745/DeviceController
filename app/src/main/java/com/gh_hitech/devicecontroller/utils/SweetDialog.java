package com.gh_hitech.devicecontroller.utils;

import android.content.Context;
import android.graphics.Color;

import cn.com.yijigu.rxnetwork.utils.StringUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * @author yijigu
 */
public class SweetDialog {
    Context context;
    private SweetAlertDialog mSweetAlertDialog;

    private SweetDialog(Context context) {
        this.context = context;
    }

    public static SweetDialog builder(Context context) {
        return new SweetDialog(context);
    }

    public SweetDialog progress(String title) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        mSweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setCancelable(false);
        return this;
    }

    public void close() {
        if (this.mSweetAlertDialog != null) {
            if (this.mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.cancel();
            }
            mSweetAlertDialog = null;
        }
    }

    public SweetDialog waring(String title, boolean cancelable) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        mSweetAlertDialog.setTitleText(title)
                .setConfirmText("确定")
//                .showCancelButton(false)
                .setCancelable(cancelable);
        return this;
    }

    /**
     * @param title              标题
     * @param content            内容
     * @param canCancelOutside   true 点击提示框外边能取消提示框
     * @param isNeedCancelButton true 需要“取消”按钮
     * @return
     */
    public SweetDialog waring(String title, String content, boolean canCancelOutside, boolean isNeedCancelButton) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        if (!StringUtils.isEmpty(title)) {
            mSweetAlertDialog.setTitleText(title);
        }
        if (!StringUtils.isEmpty(content)) {
            mSweetAlertDialog.setContentText(content);
        }
        mSweetAlertDialog.setTitleText(title)
                .setConfirmText("确定")
//                .showCancelButton(false)
                .setCancelable(canCancelOutside);
        if (isNeedCancelButton) {
            mSweetAlertDialog.setCancelText("取消");
        }
        return this;
    }

    public SweetDialog waring(String title, String content) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        mSweetAlertDialog.setTitleText(title)
                .setCancelText("取消")
                .setConfirmText("确定")
                .showCancelButton(true);
        if (!StringUtils.isEmpty(content)) {
            mSweetAlertDialog.setContentText(content);
        }
        return this;
    }

    public SweetDialog error(String title) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        mSweetAlertDialog.setTitleText(title)
                .setConfirmText("确定");
        return this;
    }

    public SweetDialog error(String title, String content) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        mSweetAlertDialog.setTitleText(title)
                .setCancelText("取消")
                .setConfirmText("确定");
        if (!StringUtils.isEmpty(content)) {
            mSweetAlertDialog.setContentText(content);
        }
        return this;
    }

    public SweetDialog success(String title) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        mSweetAlertDialog.setTitleText(title)
                .setConfirmText("确定");
        return this;
    }

    /**
     * @param title        标题
     * @param content      内容
     * @param cancelable   true 点击提示框外边能取消提示框
     * @param isCancelable true 需要“取消”按钮
     * @return
     */
    public SweetDialog success(String title, String content, boolean cancelable, boolean isCancelable) {
        close();
        mSweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        if (!StringUtils.isEmpty(title)) {
            mSweetAlertDialog.setTitleText(title);
        }
        if (!StringUtils.isEmpty(content)) {
            mSweetAlertDialog.setContentText(content);
        }
        mSweetAlertDialog.setTitleText(title)
                .setConfirmText("确定")
//                .showCancelButton(false)
                .setCancelable(cancelable);
        if (isCancelable) {
            mSweetAlertDialog.setCancelText("取消");
        }
        return this;
    }


    public SweetDialog switchError(String title) {
        //转化为错误框
        mSweetAlertDialog.setConfirmClickListener(sDialog -> sDialog
                .setTitleText(title)
                .setConfirmText("确定")
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE));
        return this;
    }

    public SweetDialog switchSuccess(String title) {
        //转化为成功框
        mSweetAlertDialog.setConfirmClickListener(sDialog -> sDialog
                .setTitleText(title)
                .setConfirmText("确定")
                .setConfirmClickListener(null)
                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE));
        return this;
    }

    public SweetDialog setConfirmClickListener(SweetAlertDialog.OnSweetClickListener confirmClickListener) {
        this.mSweetAlertDialog.setConfirmClickListener(confirmClickListener);
        return this;
    }

    public SweetDialog setCancelClickListener(SweetAlertDialog.OnSweetClickListener cancelListener) {
        this.mSweetAlertDialog.setCancelClickListener(cancelListener);
        return this;
    }

    public SweetDialog show() {
        if (this.mSweetAlertDialog != null) {
            if (!mSweetAlertDialog.isShowing()) {
                mSweetAlertDialog.show();
            }
        }
        return this;
    }

    public boolean isShowing() {
        if (this.mSweetAlertDialog != null) {
            return mSweetAlertDialog.isShowing();
        } else {
            return false;
        }
    }

}
