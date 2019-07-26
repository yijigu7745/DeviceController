package com.gh_hitech.devicecontroller.wheelpicker.widget.curved;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.gh_hitech.devicecontroller.R;
import com.gh_hitech.devicecontroller.ui.TimeDialog;
import com.gh_hitech.devicecontroller.wheelpicker.core.AbstractWheelDecor;
import com.gh_hitech.devicecontroller.wheelpicker.core.AbstractWheelPicker;
import com.gh_hitech.devicecontroller.wheelpicker.core.IWheelPicker;
import com.gh_hitech.devicecontroller.wheelpicker.view.WheelCrossPicker;

import java.util.Calendar;
import java.util.List;

/**
 * 基于WheelPicker的日期选择控件
 * DatePicker base on WheelPicker
 *
 * @author AigeStudio 2015-12-03
 * @author AigeStudio 2015-12-08
 * Init初始化
 * @author AigeStudio 2015-12-12
 * 实现变更
 * @version 1.0.0 beta
 */
public class WheelDatePicker extends LinearLayout implements IWheelPicker {
    protected WheelYearPicker pickerYear;
    protected WheelMonthPicker pickerMonth;
    protected WheelDayPicker pickerDay;

    protected AbstractWheelPicker.OnWheelChangeListener listener;

    protected String year, month, day;
    protected int labelColor = 0xFF000000;
    protected int stateYear, stateMonth, stateDay;

    protected float labelTextSize;

    public WheelDatePicker(Context context) {
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

        pickerYear = new WheelYearPicker(getContext());
        pickerMonth = new WheelMonthPicker(getContext());
        pickerDay = new WheelDayPicker(getContext());
        pickerYear.setPadding(0, padding, padding2x, padding);
        pickerMonth.setPadding(0, padding, padding2x, padding);
        pickerDay.setPadding(0, padding, padding2x, padding);
        addLabel(pickerYear, "年");
        addLabel(pickerMonth, "月");
        addLabel(pickerDay, "日");

        addView(pickerYear, llParams);
        addView(pickerMonth, llParams);
        addView(pickerDay, llParams);

        initListener(pickerYear, 0);
        initListener(pickerMonth, 1);
        initListener(pickerDay, 2);
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

    private void initListener(final WheelCrossPicker picker, final int type) {
        picker.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            @Override
            public void onWheelScrolling(float deltaX, float deltaY) {
                if (null != listener) listener.onWheelScrolling(deltaX, deltaY);
            }

            @Override
            public void onWheelSelected(int index, String data) {
                if (type == 0) {
                    year = data;
                }
                if (type == 1) {
                    month = data;
                }
                if (type == 2) {
                    day = data;
                }
                if (isValidDate()) {
                    if (TextUtils.isEmpty(year)) {
                        year = Calendar.getInstance().get(Calendar.YEAR) + "";
                    }
                    if (TextUtils.isEmpty(month)) {
                        month = (Calendar.getInstance().get(Calendar.MONTH) + 1) + "";
                    }
                    if (TextUtils.isEmpty(day)) {
                        day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "";
                    }
                    if (type == 0 || type == 1) {
                        pickerDay.setCurrentYearAndMonth(Integer.valueOf(year),
                                Integer.valueOf(month));
                    }
                    if (null != listener) {
                        listener.onWheelSelected(-1, year + "-" + month + "-" + day);
                    }
                }
            }

            @Override
            public void onWheelScrollStateChanged(int state) {
                if (type == 0) {
                    stateYear = state;
                }
                if (type == 1) {
                    stateMonth = state;
                }
                if (type == 2) {
                    stateDay = state;
                }
                if (null != listener) {
                    checkState(listener);
                }
            }
        });
    }

    private boolean isValidDate() {
        return pickerYear.getVisibility() == View.GONE ? true : !TextUtils.isEmpty(year)
                && pickerMonth.getVisibility() == View.GONE ? true : !TextUtils.isEmpty(month)
                && pickerDay.getVisibility() == View.GONE ? true : !TextUtils.isEmpty(day);
    }

    private void checkState(AbstractWheelPicker.OnWheelChangeListener listener) {
        if (stateYear == AbstractWheelPicker.SCROLL_STATE_IDLE &&
                stateMonth == AbstractWheelPicker.SCROLL_STATE_IDLE &&
                stateDay == AbstractWheelPicker.SCROLL_STATE_IDLE) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_IDLE);
        }
        if (stateYear == AbstractWheelPicker.SCROLL_STATE_SCROLLING ||
                stateMonth == AbstractWheelPicker.SCROLL_STATE_SCROLLING ||
                stateDay == AbstractWheelPicker.SCROLL_STATE_SCROLLING) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_SCROLLING);
        }
        if (stateYear + stateMonth + stateDay == 1) {
            listener.onWheelScrollStateChanged(AbstractWheelPicker.SCROLL_STATE_DRAGGING);
        }
    }

    public WheelDatePicker(Context context, AttributeSet attrs) {
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
        pickerYear.setItemIndex(index);
        pickerMonth.setItemIndex(index);
        pickerDay.setItemIndex(index);
    }

    @Override
    public void setItemSpace(int space) {
        pickerYear.setItemSpace(space);
        pickerMonth.setItemSpace(space);
        pickerDay.setItemSpace(space);
    }

    @Override
    public void setItemCount(int count) {
        pickerYear.setItemCount(count);
        pickerMonth.setItemCount(count);
        pickerDay.setItemCount(count);
    }

    @Override
    public void setTextColor(int color) {
        pickerYear.setTextColor(color);
        pickerMonth.setTextColor(color);
        pickerDay.setTextColor(color);
    }

    @Override
    public void setTextSize(int size) {
        pickerYear.setTextSize(size);
        pickerMonth.setTextSize(size);
        pickerDay.setTextSize(size);
    }

    @Override
    public void clearCache() {
        pickerYear.clearCache();
        pickerMonth.clearCache();
        pickerDay.clearCache();
    }

    @Override
    public void setCurrentTextColor(int color) {
        pickerYear.setCurrentTextColor(color);
        pickerMonth.setCurrentTextColor(color);
        pickerDay.setCurrentTextColor(color);
    }

    @Override
    public void setWheelDecor(boolean ignorePadding, AbstractWheelDecor decor) {
        pickerYear.setWheelDecor(ignorePadding, decor);
        pickerMonth.setWheelDecor(ignorePadding, decor);
        pickerDay.setWheelDecor(ignorePadding, decor);
    }

    public void setCurrentDate(int year, int month, int day) {
        pickerYear.setCurrentYear(year);
        pickerMonth.setCurrentMonth(month);
        pickerDay.setCurrentYearAndMonth(year, month);
        pickerDay.setCurrentDay(day);
    }

    public void setVisibility(TimeDialog.WheelType wheelType, int visibility) {
        if (wheelType == TimeDialog.WheelType.Year) {
            pickerYear.setVisibility(visibility);
        } else if (wheelType == TimeDialog.WheelType.Month) {
            pickerMonth.setVisibility(visibility);
        } else if (wheelType == TimeDialog.WheelType.Day) {
            pickerDay.setVisibility(visibility);
        }
    }
}