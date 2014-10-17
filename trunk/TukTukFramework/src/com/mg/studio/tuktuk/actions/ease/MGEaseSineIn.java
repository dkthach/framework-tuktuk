package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;


public class MGEaseSineIn extends MGEaseAction {

    public static MGEaseSineIn action(MGIntervalAction action) {
        return new MGEaseSineIn(action);
    }

    protected MGEaseSineIn(MGIntervalAction action) {
        super(action);
    }

	@Override
	public MGEaseSineIn copy() {
		return new MGEaseSineIn(other.copy());
	}

    @Override
    public void update(float t) {
        other.update(-1 * (float)Math.cos(t * (float) Math.PI / 2) + 1);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseSineOut(other.reverse());
    }


}
