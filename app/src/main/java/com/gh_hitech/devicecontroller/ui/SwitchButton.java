package com.gh_hitech.devicecontroller.ui;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 控制器开关按钮
 * @author yijigu
 */
public class SwitchButton extends FrameLayout implements Checkable {

    @BindView(R.id.linear_button)
    LinearLayout linearButton;
    @BindView(R.id.button_desc)
    TextView buttonDesc;

    private Drawable drawableLeft = null,drawableTop = null,drawableRight = null,drawableBottom = null;
    private Context mContext;

    private OnCheckedChangeListener mOnCheckedChangeListener;
    private boolean mChecked;
    private int mDrawableResource;

    public SwitchButton(Context context) {
        this(context,null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_button,null);
        addView(view);
        ButterKnife.bind(SwitchButton.this);
        initAndRegister();
    }

    private void initAndRegister() {
        linearButton.setOnClickListener(onClickListener -> mOnCheckedChangeListener.onCheckedChange(this,mChecked));
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onClickListener){
        mOnCheckedChangeListener = onClickListener;
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if(mDrawableResource != -1){
                setDrawableResource(checked == true ? R.drawable.kaiji_1_b:R.drawable.kaiji_1_r,Constants.TOP);
            }
            refreshDrawableState();
        }
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    public interface OnCheckedChangeListener{
        /**
         * 事件响应
         * @param switchButton
         * @param isChecked
         */
        void onCheckedChange(SwitchButton switchButton,boolean isChecked);
    }

    public void setTextColor(int color){
        buttonDesc.setTextColor(color);
        refreshDrawableState();
    }

    public void setButtonDesc(String mButtonDesc) {
        buttonDesc.setText(mButtonDesc);
        refreshDrawableState();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setDrawableResource(int drawableResource, int position) {
        if(drawableResource != -1){
            mDrawableResource = drawableResource;
        }
        switch (position){
            case Constants.LEFT:
                drawableRight = getResources().getDrawable(mDrawableResource,null);
                break;
            case Constants.TOP:
                drawableTop = getResources().getDrawable(mDrawableResource,null);
                break;
            case Constants.RIGHT:
                drawableLeft = getResources().getDrawable(mDrawableResource,null);
                break;
            case Constants.BOTTOM:
                drawableBottom = getResources().getDrawable(mDrawableResource,null);
                break;
                default:
        }
        if(null != drawableLeft){
            drawableLeft.setBounds(0,0, drawableLeft.getIntrinsicWidth(), drawableLeft.getIntrinsicHeight());
        }
        if(null != drawableRight){
            drawableRight.setBounds(0,0, drawableRight.getIntrinsicWidth(), drawableRight.getIntrinsicHeight());
        }
        if(null != drawableTop){
            drawableTop.setBounds(0,0, drawableTop.getIntrinsicWidth(), drawableTop.getIntrinsicHeight());
        }
        if(null != drawableBottom){
            drawableBottom.setBounds(0,0, drawableBottom.getIntrinsicWidth(), drawableBottom.getIntrinsicHeight());
        }
        buttonDesc.setCompoundDrawables(drawableLeft,drawableTop,drawableRight,drawableBottom);
        refreshDrawableState();
    }
}
