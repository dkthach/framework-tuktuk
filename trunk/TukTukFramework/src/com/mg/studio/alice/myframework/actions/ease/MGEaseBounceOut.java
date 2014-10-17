package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseBounceOut extends MGEaseBounce {

    public static MGEaseBounceOut action(MGIntervalAction action) {
        return new MGEaseBounceOut(action);
    }

    protected MGEaseBounceOut(MGIntervalAction action) {
        super(action);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseBounceOut(other.copy());
    }

    @Override
    public void update(float t) {
        float newT = bounceTime(t);
        other.update(newT);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseBounceIn(other.reverse());
    }

}
