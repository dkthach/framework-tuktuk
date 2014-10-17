package com.mg.studio.alice.myframework.actions.ease;

import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;


public class MGEaseSineInOut extends MGEaseAction {

    public static MGEaseSineInOut action(MGIntervalAction action) {
        return new MGEaseSineInOut(action);
    }

    protected MGEaseSineInOut(MGIntervalAction action) {
        super(action);
    }

	@Override
	public MGEaseSineInOut copy() {
		return new MGEaseSineInOut(other.copy());
	}

    @Override
    public void update(float t) {
        other.update(-0.5f * ((float)Math.cos(Math.PI * t) - 1));
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseSineInOut(other.reverse());
    }

}
