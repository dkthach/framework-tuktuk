package com.mg.studio.alice.myframework.actions.interval;

/** 
 * Delays the action a certain amount of seconds
*/
public class MGDelayTime extends MGIntervalAction {

    public static MGDelayTime action(float t) {
        return new MGDelayTime(t);
    }

    protected MGDelayTime(float t) {
        super(t);
    }

	@Override
	public MGDelayTime copy() {
		return new MGDelayTime(duration);
	}

    @Override
    public void update(float t) {
    }

    @Override
    public MGDelayTime reverse() {
        return new MGDelayTime(duration);
    }
}

