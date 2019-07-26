package com.gh_hitech.devicecontroller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.adapter.CheckboxSelectAdapter;
import com.gh_hitech.devicecontroller.effects.BaseEffect;
import com.gh_hitech.devicecontroller.effects.Effects;
import com.gh_hitech.devicecontroller.model.IBaseName;
import com.gh_hitech.devicecontroller.utils.UiUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import java.util.List;

/**
 * @author yijigu
 */
public class CheckboxDialog extends Dialog {

    private Context context;
    private Button cancel;
    private Button confirmation;
    private ListView listView;
    private LinearLayout ll_main;
    private LinearLayout cbAllCheck;
    private LinearLayout cbInvertCheck;
    private LinearLayout llMutiselect;
    private CheckBox cbAll;
    private CheckBox cbInvert;
    private View line;
    private boolean isShowTitle = true;
    private OnButtonClickListener listener;
    private ConfirmationType type = ConfirmationType.TwoButton;
    private boolean isCancel;
    private Effects effects;
    private int mDuration = 400;
    private CheckboxSelectAdapter adapter;
    private List<IBaseName> iBaseNameList;

    public CheckboxDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        setContentView(R.layout.list_dialog);
        initLayout();
        setCanceledOnTouchOutside(true);
    }

    private void initLayout() {
        isCancel = true;
        ll_main = findViewById(R.id.ll_main);
        cancel = findViewById(R.id.cancel);
        confirmation = findViewById(R.id.confirmation);
        line = findViewById(R.id.boss_unipay_id_btnGap);
        listView = findViewById(R.id.tv_single_list);
        cbAllCheck = findViewById(R.id.cb_all_check);
        cbInvertCheck = findViewById(R.id.cb_invert_check);
        llMutiselect = findViewById(R.id.ll_mutiselect);
        cbAll = findViewById(R.id.cb_all);
        cbInvert = findViewById(R.id.cb_invert);
        cbAllCheck.setOnClickListener(v -> {
            cbAll.toggle();
            cbInvert.setChecked(false);
            adapter.putAllCheck(cbAll.isChecked(), listView);
            adapter.notifyDataSetChanged();
        });
        cbInvertCheck.setOnClickListener(v -> {
            cbAll.setChecked(false);
            cbInvert.toggle();
            adapter.putInvertCheck(listView);
            adapter.notifyDataSetChanged();
        });
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

    @Override
    public void dismiss() {
        if (effects == null) {
            effects = Effects.scaleIn;
        }
        startOutAnim(effects);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = (int) (UiUtils.getResolution()[1] * 0.6);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }

    private void startInAnim(Effects type) {
        BaseEffect animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.in(ll_main);
    }

    private void startOutAnim(Effects type) {
        BaseEffect animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.out(ll_main);
        animator.mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                CheckboxDialog.super.dismiss();
            }
        });
    }

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

    /**
     * 设置对话框类型
     */
    public void setType(ConfirmationType type) {
        this.type = type;
        changeType();
    }

    private void changeType() {
        if (type == ConfirmationType.Simple) {
            cancel.setVisibility(View.GONE);
            confirmation.setVisibility(View.VISIBLE);
            confirmation.setBackgroundResource(R.drawable.dialog_btn_bg_both);
            line.setVisibility(View.GONE);
        } else if (type == ConfirmationType.TwoButton) {
            cancel.setVisibility(View.VISIBLE);
            confirmation.setVisibility(View.VISIBLE);
            line.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置多选框
     */
    public void setMutiListBean(List<IBaseName> listName, Long checkId, boolean isNeedAllCheck) {
        if (isNeedAllCheck) {
            llMutiselect.setVisibility(View.VISIBLE);
        } else {
            llMutiselect.setVisibility(View.GONE);
        }
        setCanceledOnTouchOutside(false);
        this.iBaseNameList = listName;
        setIsCheck(checkId);
        adapter = new CheckboxSelectAdapter(iBaseNameList, context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> adapter.putIsCheck(position, view));
    }

    /**
     * 设置选中的IBaseName的position
     */
    public void setIsCheck(Long checkId) {
        if (checkId != -1) {
            for (int i = 0; i < iBaseNameList.size(); i++) {
                if (checkId == iBaseNameList.get(i).getIid().longValue()) {
                    iBaseNameList.get(i).isCheck = true;
                } else {
                    iBaseNameList.get(i).isCheck = false;
                }
            }
        } else {
            for (int i = 0; i < iBaseNameList.size(); i++) {
                iBaseNameList.get(i).isCheck = false;
            }
        }
    }

    public void setSingleListBean(List<IBaseName> listName, Long checkId) {
        llMutiselect.setVisibility(View.GONE);
        setCanceledOnTouchOutside(false);
        this.iBaseNameList = listName;
        setIsCheck(checkId);
        adapter = new CheckboxSelectAdapter(iBaseNameList, context);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < iBaseNameList.size(); i++) {
                CheckboxSelectAdapter.getIsSelected().put(i, false);
            }
            adapter.putSingleCheck(position, view);
            adapter.notifyDataSetChanged();
        });
    }

    public String getId() {
        return adapter.getCheckId(CheckboxSelectAdapter.getIsSelected());
    }

    public String getName() {
        return adapter.getCheckName(CheckboxSelectAdapter.getIsSelected());
    }

    public void setConfirmation() {
        if (iBaseNameList.size() <= 0) {
            return;
        }
        if (adapter == null) {
            return;
        }
        String[] isCheckRouteId = adapter.getCheckId(CheckboxSelectAdapter.getIsSelected()).split(",");
        for (int i = 0; i < iBaseNameList.size(); i++) {
            for (int j = 0; j < isCheckRouteId.length; j++) {
                if (isCheckRouteId[j].equals(iBaseNameList.get(i).getIid())) {
                    iBaseNameList.get(i).isCheck = true;
                    break;
                }
            }
        }
    }

    public int getSelectedSum() {
        return adapter.getIsSelectSum();
    }

    /**
     * 设置出现动画
     */
    public void setEffectstype(Effects effects) {
        this.effects = effects;
    }

    public enum ConfirmationType {
        Simple,
        TwoButton
    }


    public interface OnButtonClickListener {
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
