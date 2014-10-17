package com.mg.studio.tuktuk.actions.ease;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;



/**
 * Base class for Easing actions with rate parameters
 */
public class MGEaseRateAction extends MGEaseAction {
	/** rate value for the actions */
	float rate;

	/** Creates the action with the inner action and the rate parameter */
	public static MGEaseRateAction action(MGIntervalAction action, float rate) {
		return new MGEaseRateAction(action, rate);
	}
	
	/** Initializes the action with the inner action and the rate parameter */
    protected MGEaseRateAction(MGIntervalAction action, float aRate) {
        super(action);
        rate = aRate;
    }

    @Override
    public MGEaseRateAction copy() {
        return new MGEaseRateAction(other.copy(), rate);
    }

    @Override
    public MGIntervalAction reverse() {
        return new MGEaseRateAction(other.reverse(), 1 / rate);
    }

}
