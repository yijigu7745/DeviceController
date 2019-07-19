package com.gh_hitech.devicecontroller.ui;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.utils.PxUtils;

import java.util.List;

public class SheetPopUpWindow extends PopupWindow {
    Button btnCancel;
    LinearLayout llMain;
    LinearLayout popLayout;
    private View mMenuView;
    private OnItemSelectListener listener;
    List<String> data;
    private Context mCtx;

    public static SheetPopUpWindow  createSheetPopUpWindow(Context context,List<String> data, OnItemSelectListener itemsOnClick){
        SheetPopUpWindow popUpWindow=new SheetPopUpWindow(context,itemsOnClick);
        popUpWindow.setData(data);
        return  popUpWindow;
    }



    private SheetPopUpWindow(Context context, OnItemSelectListener itemsOnClick) {
        super(context);
        this.mCtx=context;
        this.listener=itemsOnClick;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_sheet_layout, null);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        btnCancel = mMenuView.findViewById(R.id.btn_cancel);
        llMain = mMenuView.findViewById(R.id.ll_main);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener((v, event) -> {

            int height = mMenuView.findViewById(R.id.pop_layout).getTop();
            int y = (int) event.getY();
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (y < height) {
                    dismiss();
                }
            }
            return true;
        });

        btnCancel.setOnClickListener(v -> dismiss());

    }

    public void setData(List<String> data){
        if (data != null && data.size() > 0) {
            this.data = data;
            layoutView();
        }
    }

    private void layoutView(){
        int marginsTop= PxUtils.dp2px(mCtx,15);
        if (data != null && data.size() > 0){
            for (int i = 0; i < data.size(); i++) {
                Button tv = new Button(mCtx);
                tv.setBackgroundDrawable(mCtx.getResources().getDrawable(R.drawable.btn_background));
                tv.setText(data.get(i));
                tv.setGravity(Gravity.CENTER);
                tv.setTextColor(Color.WHITE);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,marginsTop,0,0);
                tv.setLayoutParams(lp);
                tv.setTag(i);
                tv.setOnClickListener(v -> {
                    int tag = (int) v.getTag();
                    if (listener != null){
                        dismiss();
                        listener.itemSelect(tag, data.get(tag));
                    }

                });
                llMain.addView(tv);
            }
        }else{
            llMain.removeAllViews();
        }
    }


    public  interface OnItemSelectListener {
        /**
         *
         * @param pos
         * @param value
         */
        void itemSelect(int pos, String value);
    }
}

