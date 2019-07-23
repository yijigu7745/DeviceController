package com.gh_hitech.devicecontroller.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.effects.BaseEffect;
import com.gh_hitech.devicecontroller.effects.Effects;
import com.gh_hitech.devicecontroller.utils.UiUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

/**
 * @author yijigu
 */
public class ConfirmationDialog extends Dialog {

    private Context context;
    private TextView tv_title;
    private TextView tv_content;
    private Button cancel;
    private Button confirmation;
    private LinearLayout ll_main;
    private View line;
    private boolean isShowTitle = true;
    private OnButtonClickListener listener;
    private ConfirmationType type = ConfirmationType.TwoButton;
    private boolean isCancel;
    private Effects effects;
    private int mDuration = 400;


    public enum  ConfirmationType{
        Simple,
        TwoButton
    }


    public ConfirmationDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        setContentView(R.layout.confirmation_dialog);
        initLayout();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (UiUtils.getResolution()[1] * 0.8);
        params.width  = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes( params);
    }

    public ConfirmationDialog setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
        return this;
    }

    /** 设置对话框类型 */
    public ConfirmationDialog setType(ConfirmationType type){
        this.type = type;
        changeType();
        return this;
    }

    /**设置出现动画 */
    public ConfirmationDialog setEffectstype(Effects effects){
        this.effects = effects;
        return this;
    }

    /**是否显示title */
    public ConfirmationDialog isShowTitle(boolean isShowTitle){
        this.isShowTitle = isShowTitle;
        if(isShowTitle){
            tv_title.setVisibility(View.VISIBLE);
        }else{
            tv_title.setVisibility(View.GONE);
        }
        return this;
    }

    /**设置title内容 */
    public ConfirmationDialog setTitle(String title) {
        if (TextUtils.isEmpty(title) || tv_title == null){
            this.isShowTitle(false);
            return this;
        }
        tv_title.setText(title);
        return this;
    }

    /**设置内容 */
    public ConfirmationDialog setContentText(String text){
        if(TextUtils.isEmpty(text) || tv_content == null){
            tv_content.setText("");
            return this;
        }
        tv_content.setTextColor(Color.BLACK);
        tv_content.setText(text);
        return this;
    }

    public TextView getContentView(){
        return tv_content;
    }
    public TextView getTitleView(){
        return tv_title;
    }

    /**设置内容 颜色*/
    public ConfirmationDialog setContentText(int color,String text){
        if(TextUtils.isEmpty(text) || tv_content == null){
            tv_content.setText("");
            return this;
        }
        tv_content.setTextColor(color);
        tv_content.setText(text);
        return this;
    }

    private void initLayout(){
        isCancel = true;
        ll_main = findViewById(R.id.ll_main);
        cancel = findViewById(R.id.cancel);
        confirmation = findViewById(R.id.confirmation);
        tv_content = findViewById(R.id.tv_content);
        tv_title = findViewById(R.id.tv_title);
        line = findViewById(R.id.boss_unipay_id_btnGap);

        ll_main.setOnClickListener(v -> {
            isCancel = true;
            dismiss();
        });
        cancel.setOnClickListener(v -> {
            isCancel = true;
            dismiss();
        });

        confirmation.setOnClickListener(v -> {
            isCancel = false;
            dismiss();
        });

        setOnDismissListener(dialogInterface -> {
            if (isCancel) {
                if (listener != null) {
                    listener.onCancelClick();
                }
            } else {
                if (listener != null) {
                    listener.onConfirmationClick();
                }
            }
        });

        setOnShowListener(dialogInterface -> {
            if (effects == null) {
                effects = Effects.scaleIn;
            }
            startInAnim(effects);
        });
    }

    private void startInAnim(Effects type) {
        BaseEffect animator = type.getAnimator();
        if(mDuration != -1){
            animator.setDuration(Math.abs(mDuration));
        }
        animator.in(ll_main);
    }

    private void startOutAnim(Effects type){
        BaseEffect animator = type.getAnimator();
        if(mDuration != -1){
            animator.setDuration(Math.abs(mDuration));
        }
        animator.out(ll_main);
        animator.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ConfirmationDialog.super.dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        if (effects == null) {
            effects = Effects.scaleIn;
        }
        startOutAnim(effects);
    }

    private  void changeType(){
        if(type == ConfirmationType.Simple){
            cancel.setVisibility(View.GONE);
            confirmation.setVisibility(View.VISIBLE);
            confirmation.setBackgroundResource(R.drawable.dialog_btn_bg_both);
            line.setVisibility(View.GONE);
        }else if(type == ConfirmationType.TwoButton){
            cancel.setVisibility(View.VISIBLE);
            confirmation.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
    }


    public interface OnButtonClickListener{
        /**
         * 确定按钮
         */
        void onConfirmationClick();

        /**
         * 取消按钮
         */
        void onCancelClick();
    }

}
