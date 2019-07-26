package com.gh_hitech.devicecontroller.effects;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

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
public class ScaleIn extends BaseEffect {

    @Override
    protected void setInAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1, 1.1f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1, 1.1f, 1).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0.2f, 1.0f).setDuration(mDuration)
        );
    }

    @Override
    protected void setOutAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "alpha", 1, 0).setDuration(mDuration)
        );
    }

    @Override
    protected long getAnimDuration(long duration) {
        return duration;
    }
}
