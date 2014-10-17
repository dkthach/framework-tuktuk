package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.director.MGNode;

/**
 * @author Dk Thach
 */
public class MGRotateBy extends MGIntervalAction {
	private float angle;
	private float startAngle;

	public static MGRotateBy action(float t, float a) {
		return new MGRotateBy(t, a);
	}
	protected MGRotateBy(float t, float a) {
		super(t);
		angle = a;
	}

	@Override
	public MGRotateBy copy() {
		return new MGRotateBy(duration, angle);
	}

	@Override
	public void start(MGNode aTarget) {
		super.start(aTarget);
		startAngle = target.getRotation();
	}

	@Override
	public void update(float t) {
		// XXX: shall I add % 360
		target.setRotation(startAngle + angle * t);
	}

	@Override
	public MGRotateBy reverse() {
		return new MGRotateBy(duration, -angle);
	}

}
