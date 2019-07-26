package com.gh_hitech.devicecontroller.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;

import com.gh_hitech.devicecontroller.wheelpicker.view.WheelCurvedPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 基于WheelPicker的月份选择控件
 * MonthPicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * @author AigeStudio 2015-12-12
 * @version 1.0.0 beta
 */
public class WheelMonthPicker extends WheelCurvedPicker {
    private static final List<String> MONTHS = new ArrayList<>();

    static {
        for (int i = 1; i <= 12; i++) {
            MONTHS.add(String.valueOf(i));
        }
    }

    private List<String> months = MONTHS;

    private int month;

    public WheelMonthPicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setData(months);
        setCurrentMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
    }

    public void setCurrentMonth(int month) {
        month = Math.max(month, 1);
        month = Math.min(month, 12);
        this.month = month;
        setItemIndex(month - 1);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }
}