package com.mg.studio.tuktuk.actions.bas;

/**
 * @author Dk Thach
 *
 */

/**
 * Base class actions that do have a finite time duration. Possible actions: -
 * An action with a duration of 0 seconds - An action with a duration of 35.5
 * seconds Infitite time actions are valid
 */

public class MGFiniteTimeAction extends MGAction {
	/*
	 * private static final String LOG_TAG = MGFiniteTimeAction.class
	 * .getSimpleName();
	 */

	// ! duration in seconds of the action
	protected float duration;

	public static MGFiniteTimeAction action(float d) {
		return new MGFiniteTimeAction(d);
	}

	protected MGFiniteTimeAction(float d) {
		duration = d;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@Override
	public MGFiniteTimeAction copy() {
		return new MGFiniteTimeAction(duration);
	}

	/** returns a reversed action */
	public MGFiniteTimeAction reverse() {
		return null;
	}

	@Override
	public void step(float dt) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(float time) {
		// TODO Auto-generated method stub
	}
}
