package com.gh_hitech.devicecontroller.effects;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Copyright 2014 gitonway
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author yijigu
 */
public class SlideOnBottom extends BaseEffect {

    @Override
    protected void setInAnimation(View view) {

        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", view.getWidth(), 10, 20, 5, 10, 0).setDuration(mDuration)
        );
    }

    @Override
    protected void setOutAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationY", 0, 10, 5, view.getWidth()).setDuration(mDuration)
        );
    }

    @Override
    protected long getAnimDuration(long duration) {
        return duration;
    }
}
