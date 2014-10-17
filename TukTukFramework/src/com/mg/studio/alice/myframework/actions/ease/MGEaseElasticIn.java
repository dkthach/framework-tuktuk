package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseElasticIn extends MGEaseElastic {

    public static MGEaseElasticIn action(MGIntervalAction action) {
        return new MGEaseElasticIn(action, 0.3f);
    }

    public static MGEaseElasticIn action(MGIntervalAction action, float period) {
        return new MGEaseElasticIn(action, period);
    }

    protected MGEaseElasticIn(MGIntervalAction action, float period) {
        super(action, period);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseElasticIn(other.copy(), period_);
    }

    @Override
    public void update(float t) {
        float newT = 0;
        if (t == 0 || t == 1) {
            newT = t;

        } else {
            float s = period_ / 4;
            t = t - 1;
            newT = (float) (-Math.pow(2, 10 * t) * Math.sin((t - s) * M_PI_X_2 / period_));
        }
        other.update(newT);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseElasticOut(other.reverse(), period_);
    }

}
