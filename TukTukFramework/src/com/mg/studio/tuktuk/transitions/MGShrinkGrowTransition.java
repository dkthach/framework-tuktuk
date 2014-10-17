package com.mg.studio.tuktuk.transitions;

import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.ease.MGEaseIn;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGScaleTo;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.director.MGScreen;

/**
 * @author Dk Thach
 * 
 *         Thu nho outScreen zoomin inScreen
 */

public class MGShrinkGrowTransition extends MGTransitionScreen implements
		MGTransitionEaseScreen {

	public static MGShrinkGrowTransition transition(float t, MGScreen s) {
		return new MGShrinkGrowTransition(t, s);
	}

	public MGShrinkGrowTransition(float t, MGScreen s) {
		super(t, s);
	}

	@Override
	public void onEnter() {
		super.onEnter();

		inScreen.setScale(0.001f);
		outScreen.setScale(1.0f);

		 inScreen.setAnchorPoint(2/3.0f, 0.5f);
	 outScreen.setAnchorPoint(1/3.0f, 0.5f);
		
		MGIntervalAction scaleOut = MGScaleTo.action(duration, 0.01f);
		MGIntervalAction scaleIn = MGScaleTo.action(duration, 1.0f);

		inScreen.runAction(easeAction(scaleIn));
		outScreen.runAction(MGSequence.actions(easeAction(scaleOut),
				MGCallback.action( new ActionCallback() {

					@Override
					public void execute(Object object) {
						finish();
					}
				})));
	}

	@Override
	public MGIntervalAction easeAction(MGIntervalAction action) {
		return MGEaseIn.action(action, 2.0f);
	}

}
