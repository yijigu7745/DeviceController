package com.gh_hitech.devicecontroller.effects;


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
 * <p>
 * * @author yijigu
 */


public enum Effects {
    standard(Standard.class),
    slideOnTop(SlideOnTop.class),
    slideOnBottom(SlideOnBottom.class),
    flip(Flip.class),
    slideIn(SlideIn.class),
    jelly(Jelly.class),
    thumbSlider(ThumbSlider.class),
    scaleIn(ScaleIn.class),
    scale(Scale.class);


    private Class<? extends BaseEffect> effectsClazz;

    private Effects(Class<? extends BaseEffect> mclass) {
        effectsClazz = mclass;
    }

    public BaseEffect getAnimator() {
        BaseEffect bEffects = null;
        try {
            bEffects = effectsClazz.newInstance();
        } catch (ClassCastException e) {
            throw new Error("Can not initDataList animatorClazz instance");
        } catch (InstantiationException e) {
            throw new Error("Can not initDataList animatorClazz instance");
        } catch (IllegalAccessException e) {
            throw new Error("Can not initDataList animatorClazz instance");
        }
        return bEffects;
    }
}
