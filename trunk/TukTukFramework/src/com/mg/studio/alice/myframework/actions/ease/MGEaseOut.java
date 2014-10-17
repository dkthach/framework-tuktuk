package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;



public class MGEaseOut extends MGEaseRateAction {

    public static MGEaseOut action(MGIntervalAction action, float rate) {
        return new MGEaseOut(action, rate);
    }

    protected MGEaseOut(MGIntervalAction action, float rate) {
        super(action, rate);
    }

    @Override
    public void update(float t) {
        other.update((float) Math.pow(t, 1 / rate));
    }

}
