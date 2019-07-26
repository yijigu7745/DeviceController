package com.gh_hitech.devicecontroller.wheelpicker.widget.curved;

import android.content.Context;
import android.util.AttributeSet;


import com.gh_hitech.devicecontroller.wheelpicker.view.WheelCurvedPicker;
import com.gh_hitech.devicecontroller.wheelpicker.widget.IDigital;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author yijigu
 */
public class WheelMinutePicker extends WheelCurvedPicker implements IDigital {
    private static final List<String> MINUTES_DIGITAL_SINGLE = new ArrayList<>();
    private static final List<String> MINUTES_DIGITAL_DOUBLE = new ArrayList<>();

    static {
        for (int i = 0; i < 60; i++) {
            MINUTES_DIGITAL_SINGLE.add(String.valueOf(i));
        }
        for (int i = 0; i < 60; i++) {
            String num = String.valueOf(i);
            if (num.length() == 1) {
                num = "0" + num;
            }
            MINUTES_DIGITAL_DOUBLE.add(num);
        }
    }

    private List<String> minutes = MINUTES_DIGITAL_SINGLE;

    private int minute;

    public WheelMinutePicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setData(minutes);
        setCurrentMinute(Calendar.getInstance().get(Calendar.MINUTE));
    }

    public void setCurrentMinute(int minute) {
        minute = Math.max(minute, 0);
        minute = Math.min(minute, 59);
        this.minute = minute;
        setItemIndex(minute);
    }

    public WheelMinutePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    public void setData(List<String> data) {
        throw new RuntimeException("Set data will not allow here!");
    }

    @Override
    public void setDigitType(int type) {
        if (type == 1) {
            minutes = MINUTES_DIGITAL_SINGLE;
        } else {
            minutes = MINUTES_DIGITAL_DOUBLE;
        }
        super.setData(minutes);
    }
}