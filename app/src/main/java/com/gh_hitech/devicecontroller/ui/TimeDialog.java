package com.gh_hitech.devicecontroller.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.utils.DateUtil;
import com.gh_hitech.devicecontroller.utils.PxUtils;
import com.gh_hitech.devicecontroller.utils.UiUtils;
import com.gh_hitech.devicecontroller.wheelpicker.core.AbstractWheelPicker;
import com.gh_hitech.devicecontroller.wheelpicker.widget.curved.WheelDatePicker;
import com.gh_hitech.devicecontroller.wheelpicker.widget.curved.WheelTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yijigu
 */
public class TimeDialog extends Dialog {
    private Context context;
    private Button btnCanel;
    private Button btnOk;
    private WheelDatePicker wheelpickerDay;
    private WheelTimePicker wheelpickerTime;
    private String day;
    private String time;
    private TimeType timeType = TimeType.Both;
    private OnTimeSelectListener onTimeSelectListener;

    public TimeDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        setContentView(R.layout.dialog_time);
        initTime();
        initLayout();
        setCanceledOnTouchOutside(true);
    }

    private void initTime() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        day = dayFormat.format(Calendar.getInstance().getTime());
        time = timeFormat.format(Calendar.getInstance().getTime());
    }

    private void initLayout() {
        btnCanel = findViewById(R.id.btn_canel);
        btnOk = findViewById(R.id.btn_ok);
        btnCanel = findViewById(R.id.btn_canel);
        btnOk = findViewById(R.id.btn_ok);
        wheelpickerDay = findViewById(R.id.wheelpicker_day);
        wheelpickerTime = findViewById(R.id.wheelpicker_time);
        wheelpickerDay.setCurrentTextColor(UiUtils.getColor(R.color.color_3F51B5));
        wheelpickerDay.setTextSize(PxUtils.dp2px(20));
        wheelpickerDay.setItemSpace(PxUtils.dp2px(10));
        wheelpickerTime.setCurrentTextColor(UiUtils.getColor(R.color.color_3F51B5));
        wheelpickerTime.setTextSize(PxUtils.dp2px(20));
        wheelpickerTime.setItemSpace(PxUtils.dp2px(10));

        wheelpickerDay.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                String newData = DateUtil.changeTimeFormat("y-M-d", data, "yyyy-MM-dd");
                if (TextUtils.isEmpty(newData)) {
                    day = data;
                } else {
                    day = newData;
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });

        wheelpickerTime.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                String newData = DateUtil.changeTimeFormat("H:m", data, "HH:mm");
                if (TextUtils.isEmpty(newData)) {
                    time = data;
                } else {
                    time = newData;
                }

            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });


        btnOk.setOnClickListener(v -> {
            dismiss();
            if (onTimeSelectListener != null) {
                onTimeSelectListener.onSelect(day + " " + time + ":00");
            }
        });
        btnCanel.setOnClickListener(v -> dismiss());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.dialogWindowAnim);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = PxUtils.dp2px(180);
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
    }

    /**
     * 设置当前时间
     *
     * @param data 日期格式必须为  yyyy-MM-dd HH:mm
     */
    public void setCurrentData(String data) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = format.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            wheelpickerDay.setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            wheelpickerTime.setCurrentTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentData(String data, String formart) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(formart);
            Date date = format.parse(data);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            wheelpickerDay.setCurrentDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
            wheelpickerTime.setCurrentTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setOnTimeSelectListener(OnTimeSelectListener onItemSelectLinstener) {
        this.onTimeSelectListener = onItemSelectLinstener;
    }

    public void setTimeType(TimeType timeType) {
        this.timeType = timeType;
        if (timeType == TimeType.Day) {
            wheelpickerTime.setVisibility(View.GONE);
            wheelpickerDay.setVisibility(View.VISIBLE);
        } else if (timeType == TimeType.Time) {
            wheelpickerDay.setVisibility(View.GONE);
            wheelpickerTime.setVisibility(View.VISIBLE);
        } else {
            wheelpickerDay.setVisibility(View.VISIBLE);
            wheelpickerTime.setVisibility(View.VISIBLE);
        }
    }

    public void setVisibility(WheelType wheelType, int visibility) {
        if (wheelType == WheelType.Year || wheelType == WheelType.Month || wheelType == WheelType.Day) {
            wheelpickerDay.setVisibility(wheelType, visibility);
        } else {
            wheelpickerTime.setVisibility(wheelType, visibility);
        }
    }

    public enum TimeType {
        Day,
        Time,
        Both
    }

    public enum WheelType {
        Year,
        Month,
        Day,
        Hour,
        Minute
    }

    public interface OnTimeSelectListener {
        void onSelect(String time);
    }
}

