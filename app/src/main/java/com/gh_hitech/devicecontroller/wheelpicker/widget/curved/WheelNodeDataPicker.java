package com.gh_hitech.devicecontroller.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;

import com.gh_hitech.devicecontroller.wheelpicker.view.WheelCurvedPicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于WheelPicker的返回节点选择控件
 * NodePicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * @author AigeStudio 2015-12-12
 * @version 1.0.0 beta
 */
public class WheelNodeDataPicker extends WheelCurvedPicker {
    private static final List<String> NODE = new ArrayList<>();

    private List<String> node = NODE;

    public WheelNodeDataPicker(Context context) {
        super(context);
        init();
    }

    public WheelNodeDataPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.setData(node);
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }
    public void setCurrentNode(String node){

    }

}