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
import com.gh_hitech.devicecontroller.utils.UiUtils;
import com.gh_hitech.devicecontroller.wheelpicker.core.AbstractWheelPicker;
import com.gh_hitech.devicecontroller.wheelpicker.widget.curved.WheelNodePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author yexin
 * @date 16/6/16
 */
public class NodeDialog extends Dialog {
    private Context context;
    private Button btnCanel;
    private Button btnOk;
    private WheelNodePicker wheelNodePicker;
    private String node;
    private TimeType timeType = TimeType.Both;
    private OnTimeSelectListener onTimeSelectListener;

    public NodeDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        this.context = context;
        setContentView(R.layout.dialog_node);
        initNode();
        initLayout();
        setCanceledOnTouchOutside(true);
    }

    private void initNode() {
        node = "";
    }

    private void initLayout() {
        btnCanel = findViewById(R.id.btn_canel);
        btnOk = findViewById(R.id.btn_ok);
        btnCanel = findViewById(R.id.btn_canel);
        btnOk = findViewById(R.id.btn_ok);
        wheelNodePicker = findViewById(R.id.wheelpicker_time);
        wheelNodePicker.setCurrentTextColor(UiUtils.getColor(R.color.color_3F51B5));
        wheelNodePicker.setTextSize(UiUtils.dip2px(20));
        wheelNodePicker.setItemSpace(UiUtils.dip2px(10));

        wheelNodePicker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
            }

            @Override
            public void onWheelSelected(int index, String data) {
                String newData = DateUtil.changeTimeFormat("y-M-d", data, "yyyy-MM-dd");
                if (TextUtils.isEmpty(newData)) {
                    node = data;
                } else {
                    node = newData;
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
            }
        });


        btnOk.setOnClickListener(v -> {
            dismiss();
            if (onTimeSelectListener != null) {
                onTimeSelectListener.onSelect(node + " ");
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
        params.height = UiUtils.dip2px(180);
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
            wheelNodePicker.setCurrentNode("");
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
            wheelNodePicker.setVisibility(View.GONE);
        } else if (timeType == TimeType.Time) {
            wheelNodePicker.setVisibility(View.GONE);
        } else {
            wheelNodePicker.setVisibility(View.VISIBLE);
        }
    }

    public void setVisibility(WheelType wheelType, int visibility) {
        if (wheelType == WheelType.Node) {
            wheelNodePicker.setVisibility(wheelType, visibility);
        }
    }

    public enum TimeType {
        Day,
        Time,
        Both
    }

    public enum WheelType {
        Node
    }

    public interface OnTimeSelectListener {
        void onSelect(String time);
    }

}
