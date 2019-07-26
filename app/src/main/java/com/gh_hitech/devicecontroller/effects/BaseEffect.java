package com.gh_hitech.devicecontroller.effects;


import android.view.View;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.view.ViewHelper;

/**
 * Copyright 2014 gitonway
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author yijigu
 */
public abstract class BaseEffect {

    public static final int DURATION;

    static {
        DURATION = 700;
    }

    public long mDuration = DURATION;

    public AnimatorSet mAnimatorSet;

    {
        mAnimatorSet = new AnimatorSet();
    }

    public void in(View view) {
        reset(view);
        setInAnimation(view);
        mAnimatorSet.start();
    }

    public void reset(View view) {
        ViewHelper.setPivotX(view, view.getWidth() / 2.0f);
        ViewHelper.setPivotY(view, view.getHeight() / 2.0f);
    }

    protected abstract void setInAnimation(View view);

    public void out(View view) {
        reset(view);
        setOutAnimation(view);
        mAnimatorSet.start();
    }

    protected abstract void setOutAnimation(View view);

    public long getDuration() {
        return getAnimDuration(mDuration);
    }

    protected abstract long getAnimDuration(long duration);

    public BaseEffect setDuration(long duration) {
        this.mDuration = duration;
        return this;
    }

    public AnimatorSet getAnimatorSet() {
        return mAnimatorSet;
    }

}
