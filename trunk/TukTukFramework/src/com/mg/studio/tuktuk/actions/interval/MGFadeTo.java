package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.director.MGNode;
import com.mg.studio.tuktuk.events.MGRGBAProtocol;

/** Fades an object that implements the CCRGBAProtocol protocol.
 * It modifies the opacity from the current value to a custom one.
 * @warning This action doesn't support "reverse"
 */
public class MGFadeTo extends MGIntervalAction {
    float toOpacity;
    float fromOpacity;

    /** creates an action with duration and opactiy */
    public static MGFadeTo action(float t, float a) {
        return new MGFadeTo(t, a);
    }

    /** initializes the action with duration and opacity */
    protected MGFadeTo(float t, float a) {
        super(t);
        toOpacity = a;
    }

    @Override
    public MGFadeTo copy() {
        return new MGFadeTo(duration, toOpacity);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        fromOpacity = ((MGRGBAProtocol) target).getOpacity();
    }

    @Override
    public void update(float t) {
        ((MGRGBAProtocol) target).setOpacity((fromOpacity + (toOpacity - fromOpacity) * t));
    }
}

