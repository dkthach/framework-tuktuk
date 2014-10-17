package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseExponentialInOut extends MGEaseAction {

    public static MGEaseExponentialInOut action(MGIntervalAction action) {
        return new MGEaseExponentialInOut(action);
    }

    protected MGEaseExponentialInOut(MGIntervalAction action) {
        super(action);
    }

    @Override
    public void update(float t) {
    	t /= 0.5f;
        if (t < 1)
            t = 0.5f * (float) Math.pow(2, 10 * (t - 1));
        else
            t = 0.5f * (-(float) Math.pow(2, -10 * (t - 1) ) + 2);
        other.update(t);
    }

}
