package com.gh_hitech.devicecontroller.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.yijigu.rxnetwork.utils.StringUtils;

/**
 * @author yijigu
 */
public class EditDialog extends Dialog {
    @BindView(R.id.dialog_title)
    TextView titleText;
    @BindView(R.id.dialog_edit)
    EditText editText;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    private String text, title, confirmText, cancelText;

    private OnButtonClickListener onButtonClickListener;

    public EditDialog(Context context) {
        super(context);
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setConfirmText(String confirmText) {
        this.confirmText = confirmText;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = 580;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.edit_dialog);
        //初始化界面控件
        ButterKnife.bind(this);
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initData() {
        if (StringUtils.isNotBlank(text)) {
            editText.setText(text);
        }
        if (StringUtils.isNotBlank(title)) {
            titleText.setText(title);
        }
        if (StringUtils.isNotBlank(confirmText)) {
            btnConfirm.setText(confirmText);
        }
        if (StringUtils.isNotBlank(cancelText)) {
            btnCancel.setText(cancelText);
        }
    }

    private void initEvent() {
        btnConfirm.setOnClickListener(v -> {
            if (onButtonClickListener != null) {
                onButtonClickListener.onConfirmClick();
            }
        });
        btnCancel.setOnClickListener(v -> {
            if (onButtonClickListener != null) {
                onButtonClickListener.onCancelClick();
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                text = s.toString();
            }
        });
    }

    public interface OnButtonClickListener {
        /**
         * “确认”按钮点击事件
         */
        void onConfirmClick();

        /**
         * “取消”按钮点击事件
         */
        void onCancelClick();
    }
}
