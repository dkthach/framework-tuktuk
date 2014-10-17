package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseExponentialIn extends MGEaseAction {

    public static MGEaseExponentialIn action(MGIntervalAction action) {
        return new MGEaseExponentialIn(action);
    }

    protected MGEaseExponentialIn(MGIntervalAction action) {
        super(action);
    }

	@Override
	public MGEaseExponentialIn copy() {
		return new MGEaseExponentialIn(other.copy());
	}

    @Override
    public void update(float t) {
        other.update((t == 0) ? 0 : (float) Math.pow(2, 10 * (t / 1 - 1)) - 1 * 0.001f);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseExponentialOut(other.reverse());
    }

}
