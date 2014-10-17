package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseElasticInOut extends MGEaseElastic {

    public static MGEaseElasticInOut action(MGIntervalAction action) {
        return new MGEaseElasticInOut(action, 0.3f);
    }

    public static MGEaseElasticInOut action(MGIntervalAction action, float period) {
        return new MGEaseElasticInOut(action, period);
    }

    protected MGEaseElasticInOut(MGIntervalAction action, float period) {
        super(action, period);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseElasticInOut(other.copy(), period_);
    }

    public void update(float t) {
        float newT = 0;

        if (t == 0 || t == 1)
            newT = t;
        else {
            t = t * 2;
            if (period_ == 0)
                period_ = 0.3f * 1.5f;
            float s = period_ / 4;

            t = t - 1;
            if (t < 0) {
                newT = (float) (-0.5f * Math.pow(2, 10 * t) * Math.sin((t - s) * M_PI_X_2 / period_));
            } else {
                newT = (float) (Math.pow(2, -10 * t) * Math.sin((t - s) * M_PI_X_2 / period_) * 0.5f + 1);
            }
        }
        other.update(newT);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseElasticInOut(other.reverse(), period_);
    }

}
