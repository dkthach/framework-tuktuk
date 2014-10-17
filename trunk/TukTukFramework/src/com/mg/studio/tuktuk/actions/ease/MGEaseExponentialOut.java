package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseExponentialOut extends MGEaseAction {

    public static MGEaseExponentialOut action(MGIntervalAction action) {
        return new MGEaseExponentialOut(action);
    }

    protected MGEaseExponentialOut(MGIntervalAction action) {
        super(action);
    }

	@Override
	public MGEaseExponentialOut copy() {
		return new MGEaseExponentialOut(other.copy());
	}

    @Override
    public void update(float t) {
        other.update((t == 1) ? 1 : ((float) (-Math.pow(2, -10 * t / 1) + 1)));
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseExponentialIn(other.reverse());
    }

}
