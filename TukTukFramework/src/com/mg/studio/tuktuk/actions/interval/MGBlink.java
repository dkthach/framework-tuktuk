package com.mg.studio.tuktuk.actions.interval;


public class MGBlink extends MGIntervalAction {
    private int times;

    /** creates the action */
    public static MGBlink action(float t, int b) {
        return new MGBlink(t, b);
    }

    /** initilizes the action */
    protected MGBlink(float t, int b) {
        super(t);
        times = b;
    }

    @Override
    public MGBlink copy() {
        return new MGBlink(duration, times);
    }

    @Override
    public void update(float t) {
        float slice = 1.0f / times;
        float m = t % slice;
        target.setHide(m > slice / 2 ? true : false);
    }

    @Override
    public MGBlink reverse() {
        return new MGBlink(duration, times);
    }
}

