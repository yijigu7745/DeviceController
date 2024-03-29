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
public class WheelHourPicker extends WheelCurvedPicker implements IDigital {
    private static final List<String> HOURS_DIGITAL_SINGLE = new ArrayList<>();
    private static final List<String> HOURS_DIGITAL_DOUBLE = new ArrayList<>();

    static {
        for (int i = 0; i < 24; i++) {
            HOURS_DIGITAL_SINGLE.add(String.valueOf(i));
        }
        for (int i = 0; i < 24; i++) {
            String num = String.valueOf(i);
            if (num.length() == 1) {
                num = "0" + num;
            }
            HOURS_DIGITAL_DOUBLE.add(num);
        }
    }

    private List<String> hours = HOURS_DIGITAL_SINGLE;

    private int hour;

    public WheelHourPicker(Context context) {
        super(context);
        init();
    }

    private void init() {
        super.setData(hours);
        setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
    }

    public void setCurrentHour(int hour) {
        hour = Math.max(hour, 0);
        hour = Math.min(hour, 23);
        this.hour = hour;
        setItemIndex(hour);
    }

    public WheelHourPicker(Context context, AttributeSet attrs) {
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
            hours = HOURS_DIGITAL_SINGLE;
        } else {
            hours = HOURS_DIGITAL_DOUBLE;
        }
        super.setData(hours);
    }
}