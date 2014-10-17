/**
 * 
 */
package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.myframework.actions.ActionCallback;
import com.mg.studio.alice.myframework.actions.ease.MGEaseExponentialOut;
import com.mg.studio.alice.myframework.actions.instant.MGCallback;
import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;
import com.mg.studio.alice.myframework.actions.interval.MGScaleTo;
import com.mg.studio.alice.myframework.actions.interval.MGSequence;
import com.mg.studio.alice.myframework.director.MGScreen;

/**
 * @author Dk Thach
 * 
 */
public class MGZoomInExponentialOutTransiton extends MGTransitionScreen implements
		MGTransitionEaseScreen {

	public static MGZoomInExponentialOutTransiton transition(float t, MGScreen s) {
		return new MGZoomInExponentialOutTransiton(t, s);
	}

	/**
	 * @param t
	 * @param s
	 */
	public MGZoomInExponentialOutTransiton(float t, MGScreen s) {
		super(t, s);
		
	}

	@Override
	public void onEnter() {
		super.onEnter();
		inScreen.setScale(0.001f);
		outScreen.setScale(1.0f);
		inScreen.setAnchorPoint(0.5f, 0.5f);
		MGIntervalAction scaleIn = MGScaleTo.action(duration, 1.0f);
		inScreen.runAction(MGSequence.actions(easeAction(scaleIn),
				MGCallback.action( new ActionCallback() {

					@Override
					public void execute(Object object) {
						finish();
					}
				})));
	}

	@Override
	public MGIntervalAction easeAction(MGIntervalAction action) {
		return MGEaseExponentialOut.action(action);
	}

}
