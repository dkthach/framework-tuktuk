package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseBackInOut extends MGEaseAction {

    public static MGEaseBackInOut action(MGIntervalAction action) {
        return new MGEaseBackInOut(action);
    }

    protected MGEaseBackInOut(MGIntervalAction action) {
        super(action);
    }

    @Override
    public MGEaseAction copy() {
        return new MGEaseBackInOut(other.copy());
    }

    @Override
    public void update(float t) {
        float overshoot = 1.70158f * 1.525f;

        t = t * 2;
        if (t < 1) {
            other.update((t * t * ((overshoot + 1) * t - overshoot)) / 2);
        } else {
            t = t - 2;
            other.update((t * t * ((overshoot + 1) * t + overshoot)) / 2 + 1);
        }
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseBackInOut(other.reverse());
    }

}
