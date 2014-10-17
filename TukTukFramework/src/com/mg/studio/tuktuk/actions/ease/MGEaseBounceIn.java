package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseBounceIn extends MGEaseBounce {

    public static MGEaseBounceIn action(MGIntervalAction action) {
        return new MGEaseBounceIn(action);
    }

    protected MGEaseBounceIn(MGIntervalAction action) {
        super(action);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseBounceIn(other.copy());
    }

    @Override
    public void update(float t) {
        float newT = 1 - bounceTime(1 - t);
        other.update(newT);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseBounceOut(other.reverse());
    }

}
