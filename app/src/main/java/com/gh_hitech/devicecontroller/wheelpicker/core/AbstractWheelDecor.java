package com.gh_hitech.devicecontroller.wheelpicker.core;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * @author yijigu
 */
public abstract class AbstractWheelDecor {
    /**
     * yijigu
     * @param canvas
     * @param rectLast
     * @param rectNext
     * @param paint
     */
    public abstract void drawDecor(Canvas canvas, Rect rectLast, Rect rectNext, Paint paint);
}