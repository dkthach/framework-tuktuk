package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseElasticOut extends MGEaseElastic {

    public static MGEaseElasticOut action(MGIntervalAction action) {
        return new MGEaseElasticOut(action, 0.3f);
    }

    public static MGEaseElasticOut action(MGIntervalAction action, float period) {
        return new MGEaseElasticOut(action, period);
    }

    protected MGEaseElasticOut(MGIntervalAction action, float period) {
        super(action, period);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseElasticOut(other.copy(), period_);
    }

    @Override
    public void update(float t) {
        float newT = 0;
        if (t == 0 || t == 1) {
            newT = t;
        } else {
            float s = period_ / 4;
            newT = (float) (Math.pow(2, -10 * t) * Math.sin((t - s) * M_PI_X_2  / period_) + 1);
        }
        other.update(newT);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseElasticIn(other.reverse(), period_);
    }

}
