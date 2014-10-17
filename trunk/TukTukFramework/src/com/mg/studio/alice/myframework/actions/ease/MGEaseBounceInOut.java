package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseBounceInOut extends MGEaseBounce {

    public static MGEaseBounceInOut action(MGIntervalAction action) {
        return new MGEaseBounceInOut(action);
    }

    protected MGEaseBounceInOut(MGIntervalAction action) {
        super(action);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseBounceInOut(other.copy());
    }

    @Override
    public void update(float t) {
        float newT = 0;
        if (t < 0.5) {
            t = t * 2;
            newT = (1 - bounceTime(1 - t)) * 0.5f;
        } else
            newT = bounceTime(t * 2 - 1) * 0.5f + 0.5f;

        other.update(newT);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseBounceInOut(other.reverse());
    }

}
