package com.gh_hitech.devicecontroller.wheelpicker.core;

/**
 * @author yijigu
 */
public interface WheelScroller {
    /**
     * .
     */
    void abortAnimation();

    /**
     * .
     *
     * @return
     */
    boolean computeScrollOffset();

    /**
     * .
     *
     * @param extend
     */
    void extendDuration(int extend);

    /**
     * .
     *
     * @param startX
     * @param startY
     * @param velocityX
     * @param velocityY
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY);

    /**
     * .
     *
     * @param startX
     * @param startY
     * @param velocityX
     * @param velocityY
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @param overX
     * @param overY
     */
    void fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY);

    /**
     * .
     *
     * @param finished
     */
    void forceFinished(boolean finished);

    /**
     * .
     *
     * @return
     */
    float getCurrVelocity();

    /**
     * .
     *
     * @return
     */
    int getCurrX();

    /**
     * .
     *
     * @return
     */
    int getCurrY();

    /**
     * .
     *
     * @return
     */
    int getDuration();

    /**
     * .
     *
     * @return
     */
    int getFinalX();

    /**
     * .
     *
     * @param newX
     */
    void setFinalX(int newX);

    /**
     * .
     *
     * @return
     */
    int getFinalY();

    /**
     * .
     *
     * @param newY
     */
    void setFinalY(int newY);

    /**
     * .
     *
     * @return
     */
    int getStartX();

    /**
     * .
     *
     * @return
     */
    int getStartY();

    /**
     * .
     *
     * @return
     */
    boolean isFinished();

    /**
     * .
     *
     * @return
     */
    boolean isOverScrolled();

    /**
     * .
     *
     * @param startX
     * @param finalX
     * @param overX
     */
    void notifyHorizontalEdgeReached(int startX, int finalX, int overX);

    /**
     * .
     *
     * @param startY
     * @param finalY
     * @param overY
     */
    void notifyVerticalEdgeReached(int startY, int finalY, int overY);

    /**
     * .
     *
     * @param friction
     */
    void setFriction(float friction);

    /**
     * ..
     *
     * @param startX
     * @param startY
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     * @return
     */
    boolean springBack(int startX, int startY, int minX, int maxX, int minY, int maxY);

    /**
     * .
     *
     * @param startX
     * @param startY
     * @param dx
     * @param dy
     */
    void startScroll(int startX, int startY, int dx, int dy);

    /**
     * .
     *
     * @param startX
     * @param startY
     * @param dx
     * @param dy
     * @param duration
     */
    void startScroll(int startX, int startY, int dx, int dy, int duration);

    /**
     * .
     *
     * @return
     */
    int timePassed();
}