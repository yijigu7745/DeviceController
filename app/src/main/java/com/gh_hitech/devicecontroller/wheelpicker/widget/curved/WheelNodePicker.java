package com.gh_hitech.devicecontroller.wheelpicker.widget.curved;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;


import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.ui.NodeDialog;
import com.gh_hitech.devicecontroller.wheelpicker.core.AbstractWheelDecor;
import com.gh_hitech.devicecontroller.wheelpicker.core.AbstractWheelPicker;
import com.gh_hitech.devicecontroller.wheelpicker.core.IWheelPicker;
import com.gh_hitech.devicecontroller.wheelpicker.view.WheelCrossPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于WheelPicker的节点选择控件
 * NodePicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * Init初始化
 * @author AigeStudio 2015-12-12
 * 实现变更
 * @version 1.0.0 beta
 */
public class WheelNodePicker extends LinearLayout implements IWheelPicker {
    protected WheelNodeDataPicker pickerDataNode;

    protected AbstractWheelPicker.OnWheelChangeListener listener;

    protected String node;
    protected int labelColor = 0xFF000000;
    protected int stateNode;

    protected float labelTextSize;

    public WheelNodePicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);

        int padding = getResources().getDimensionPixelSize(R.dimen.WheelPadding);
        int padding2x = padding * 2;

        labelTextSize = padding;

        LayoutParams llParams = new LayoutParams(-2, -2);

        pickerDataNode = new WheelNodeDataPicker(getContext());
        pickerDataNode.setData(new ArrayList<String>());
        pickerDataNode.setPadding(0, padding, padding2x, padding);
        addLabel(pickerDataNode, "环节");

        addView(pickerDataNode, llParams);

        initListener(pickerDataNode);
    }

    private void addLabel(WheelCrossPicker picker, final String label) {
        picker.setWheelDecor(true, new AbstractWheelDecor() {
            @Override
            public void drawDecor(Canvas canvas, Rect rectLast, Rect rectNext, Paint paint) {
                paint.setColor(labelColor);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(labelTextSize * 1.5F);
                canvas.drawText(label, rectNext.centerX(),
                        rectNext.centerY() - (paint.ascent() + paint.descent()) / 2.0F, paint);
            }
        });
    }

    private void initListener(final WheelCrossPicker picker) {
        picker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
                if (null != listener) {
                    listener.onWheelScrolling(deltaX, deltaY);
                }
            }

            @Override
            public void onWheelSelected(int index, String data) {
                node = data;
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                stateNode = state;
            }
        });
    }

    public WheelNodePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setLabelColor(int labelColor) {
        this.labelColor = labelColor;
        invalidate();
    }

    public void setLabelTextSize(float labelTextSize) {
        this.labelTextSize = labelTextSize;
        invalidate();
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    @Override
    public void setOnWheelChangeListener(AbstractWheelPicker.OnWheelChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void setItemIndex(int index) {
        pickerDataNode.setItemIndex(index);
    }

    @Override
    public void setItemSpace(int space) {
        pickerDataNode.setItemSpace(space);
    }

    @Override
    public void setItemCount(int count) {
        pickerDataNode.setItemCount(count);
    }

    @Override
    public void setTextColor(int color) {
        pickerDataNode.setTextColor(color);
    }

    @Override
    public void setTextSize(int size) {
        pickerDataNode.setTextSize(size);
    }

    @Override
    public void clearCache() {
        pickerDataNode.clearCache();
    }

    @Override
    public void setCurrentTextColor(int color) {
        pickerDataNode.setCurrentTextColor(color);
    }

    @Override
    public void setWheelDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerDataNode.setWheelDecor(ignorePadding, decor);
    }

    public void setCurrentNode(String node) {
        pickerDataNode.setCurrentNode(node);
    }

    public void setVisibility(NodeDialog.WheelType wheelType, int visibility) {
        if (wheelType == NodeDialog.WheelType.Node) {
            pickerDataNode.setVisibility(visibility);
        }
    }
}