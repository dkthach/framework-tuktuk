package com.mg.studio.tuktuk.actions.interval;

import com.mg.studio.tuktuk.director.MGNode;

/**
 * @author Dk Thach
 */
public class MGRotateTo extends MGIntervalAction {
	private float dstAngle;
	private float diffAngle;
	private float startAngle;

	public static MGRotateTo action(float duration, float ang) {
		return new MGRotateTo(duration, ang);
	}

	protected MGRotateTo(float duration, float ang) {
		super(duration);
		dstAngle = ang;
	}

	@Override
	public MGRotateTo copy() {
		return new MGRotateTo(duration, dstAngle);
	}

	@Override
	public void start(MGNode aTarget) {
		super.start(aTarget);
		startAngle = target.getRotation();
		if (startAngle > 0)
			startAngle = (float) (startAngle % 360.0f);
		else
			startAngle = (float) (startAngle % -360.0f);

		diffAngle = dstAngle - startAngle;
		if (diffAngle > 180)
			diffAngle -= 360;
		if (diffAngle < -180)
			diffAngle += 360;
	}

	@Override
	public void update(float t) {
		target.setRotation(startAngle + diffAngle * t);
	}

}
