package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseBackIn extends MGEaseAction {

    public static MGEaseBackIn action(MGIntervalAction action) {
        return new MGEaseBackIn(action);
    }

    protected MGEaseBackIn(MGIntervalAction action) {
        super(action);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseBackIn(other.copy());
    }

    @Override
    public void update(float t) {
        float overshoot = 1.70158f;
        other.update(t * t * ((overshoot + 1) * t - overshoot));
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseBackOut(other.reverse());
    }

}
