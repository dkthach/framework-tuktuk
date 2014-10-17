/**
 * 
 */
package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.actions.instant.MGCallbackN;
import com.mg.studio.alice.myframework.director.MGNode;

/**
 * @author Dk Thach
 * 
 */
public class MGScaleInAndOut extends MGIntervalAction {

	float sxMin, syMin, sxMax, syMax;
	MGSequence sequence;
	

	public static MGScaleInAndOut action(float t, float sxMin, float sxMax,
			float syMin, float syMax) {
		return new MGScaleInAndOut(t, sxMin, sxMax, syMin, syMax, null);
	}

	public static MGScaleInAndOut action(float t, float sxMin, float sxMax,
			float syMin, float syMax, MGCallbackN callbackN) {
		return new MGScaleInAndOut(t, sxMin, sxMax, syMin, syMax, callbackN);
	}

	public static MGScaleInAndOut action(float t, float sMin, float sMax,
			MGCallbackN callbackN) {
		return new MGScaleInAndOut(t, sMin, sMax, callbackN);
	}

	public static MGScaleInAndOut action(float t, float sMin, float sMax) {
		return new MGScaleInAndOut(t, sMin, sMax, null);
	}

	protected MGScaleInAndOut(float d, float sxMin, float sxMax, float syMin,
			float syMax, MGCallbackN callbackN) {
		super(d);
		init(d, sxMin, sxMax, syMin, syMax, callbackN);

	}

	protected MGScaleInAndOut(float d, float sMin, float sMax,
			MGCallbackN callbackN) {
		super(d);
		init(d, sMin, sMax, sMin, sMax, callbackN);

	}

	private void init(float d, float sxMin, float sxMax, float syMin,
			float syMax, MGCallbackN callbackN) {
		this.sxMin = sxMin;
		this.syMin = syMin;
		this.sxMax = sxMax;
		this.syMax = syMax;
		MGScaleTo scaleOut = MGScaleTo.action(d / 2, sxMin, syMin);
		MGScaleTo scaleIn = MGScaleTo.action(d / 2, sxMax, syMax);
		
		if (callbackN != null) {
			
			sequence = MGSequence.actions(scaleOut, callbackN, scaleIn);
		} else {
			sequence = MGSequence.actions(scaleOut,  scaleIn);
		}

	}

	

	@Override
	public MGIntervalAction copy() {
		//not support coppy
		return null;
	}

	@Override
	public void start(MGNode aTarget) {
		super.start(aTarget);
		sequence.start(aTarget);

	}

	@Override
	public void update(float t) {
		sequence.update(t);

	}

}
