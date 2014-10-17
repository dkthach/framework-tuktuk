package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseSineOut extends MGEaseAction {

    public static MGEaseSineOut action(MGIntervalAction action) {
        return new MGEaseSineOut(action);
    }

    protected MGEaseSineOut(MGIntervalAction action) {
        super(action);
    }

	@Override
	public MGEaseSineOut copy() {
		return new MGEaseSineOut(other.copy());
	}

    @Override
    public void update(float t) {
        other.update((float)Math.sin(t * (float) Math.PI / 2));
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseSineIn(other.reverse());
    }

}
