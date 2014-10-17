package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;



public class MGEaseIn extends MGEaseRateAction {

    public static MGEaseIn action(MGIntervalAction action, float rate) {
        return new MGEaseIn(action, rate);
    }

    protected MGEaseIn(MGIntervalAction action, float rate) {
        super(action, rate);
    }

	@Override
	public MGEaseIn copy() {
		return new MGEaseIn(other.copy(), rate);
	}

    @Override
    public void update(float t) {
        other.update((float) Math.pow(t, rate));
    }

	@Override
	public MGIntervalAction reverse() {
		return new MGEaseOut(other.reverse(), rate);
	}

}
