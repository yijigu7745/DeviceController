package com.gh_hitech.devicecontroller.wheelpicker.view;

import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.text.TextPaint;
import android.view.VelocityTracker;

import com.gh_hitech.devicecontroller.wheelpicker.core.WheelScroller;


interface ICrossOrientation {

    /**
     * @param scroller
     * @return
     */
    int getUnitDeltaTotal(WheelScroller scroller);

    /**
     * @param scroller
     * @param start
     * @param distance
     */
    void startScroll(WheelScroller scroller, int start, int distance);

    /**
     * @param count
     * @param space
     * @param width
     * @param height
     * @return
     */
    int computeRadius(int count, int space, int width, int height);

    /**
     * @param radius
     * @param width
     * @param height
     * @return
     */
    int getCurvedWidth(int radius, int width, int height);

    /**
     * @param radius
     * @param width
     * @param height
     * @return
     */
    int getCurvedHeight(int radius, int width, int height);

    /**
     * @param count
     * @param space
     * @param width
     * @param height
     * @return
     */
    int getStraightWidth(int count, int space, int width, int height);

    /**
     * @param count
     * @param space
     * @param width
     * @param height
     * @return
     */
    int getStraightHeight(int count, int space, int width, int height);

    /**
     * @param space
     * @param width
     * @param height
     * @return
     */
    int getStraightUnit(int space, int width, int height);

    /**
     * @param count
     * @param space
     * @param width
     * @param height
     * @return
     */
    int getDisplay(int count, int space, int width, int height);

    /**
     * @param camera
     * @param degree
     */
    void rotateCamera(Camera camera, int degree);

    /**
     * @param matrix
     * @param space
     * @param x
     * @param y
     */
    void matrixToCenter(Matrix matrix, int space, int x, int y);

    /**
     * @param canvas
     * @param paint
     * @param data
     * @param space
     * @param x
     * @param y
     */
    void draw(Canvas canvas, TextPaint paint, String data, int space, int x, int y);

    /**
     * @param moveX
     * @param moveY
     * @param radius
     * @return
     */
    int computeDegreeSingleDelta(int moveX, int moveY, int radius);

    /**
     * @param scroller
     * @param tracker
     * @param from
     * @param disMin
     * @param disMax
     * @param over
     */
    void fling(WheelScroller scroller, VelocityTracker tracker, int from, int disMin, int disMax, int over);

    /**
     * @param unit
     * @param index
     * @param totalMoveX
     * @param totalMoveY
     * @param singleMoveX
     * @param singleMoveY
     * @return
     */
    int computeStraightUnit(int unit, int index, int totalMoveX, int totalMoveY, int singleMoveX, int singleMoveY);

    /**
     * @param totalMoveX
     * @param totalMoveY
     * @return
     */
    int getUnitDeltaTotal(int totalMoveX, int totalMoveY);

    /**
     * @param rect
     * @param space
     * @param width
     * @param height
     * @param textWidth
     * @param textHeight
     * @param x
     * @param y
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void computeCurItemRect(Rect rect, int space, int width, int height, int textWidth, int textHeight, int x, int y, int left, int top, int right, int bottom);

    /**
     *
     */
    void clearCache();

    /**
     * @param rect
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void removePadding(Rect rect, int left, int top, int right, int bottom);

    /**
     * @param rectLast
     * @param rectNext
     * @param rect
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    void computeRectPadding(Rect rectLast, Rect rectNext, Rect rect, int left, int top, int right, int bottom);

    /**
     * @param moveX
     * @param moveY
     * @return
     */
    int obtainCurrentDis(int moveX, int moveY);
}