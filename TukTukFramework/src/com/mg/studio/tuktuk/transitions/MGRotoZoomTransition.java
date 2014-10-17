package com.mg.studio.tuktuk.transitions;

import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGDelayTime;
import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGRotateBy;
import com.mg.studio.tuktuk.actions.interval.MGScaleBy;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.actions.interval.MGSpawn;
import com.mg.studio.tuktuk.director.MGScreen;

/**
 * @author Dk Thach
 * 
 *         RotoZoom Transition. Rotate va zoomout outScreen,rotate va zoomIn
 *         inScreen
 */

public class MGRotoZoomTransition extends MGTransitionScreen {

	public static MGRotoZoomTransition transition(float t, MGScreen s) {
		return new MGRotoZoomTransition(t, s);
	}

	public MGRotoZoomTransition(float t, MGScreen s) {
		super(t, s);
	}

	@Override
	public void onEnter() {
		super.onEnter();

		inScreen.setScale(0.001f);
		outScreen.setScale(1.0f);

		 inScreen.setAnchorPoint(0.5f, 0.5f);
		 outScreen.setAnchorPoint(0.5f, 0.5f);
		
		MGIntervalAction rotozoom = MGSequence.actions(MGSpawn.actions(
				MGScaleBy.action(duration / 2, 0.001f),
				MGRotateBy.action(duration / 2, 360 * 2)), MGDelayTime
				.action(duration / 2));

		outScreen.runAction(rotozoom);
		inScreen.runAction(MGSequence.actions(rotozoom.reverse(),

		MGCallback.action(new ActionCallback() {

			@Override
			public void execute(Object object) {
				finish();
			}
		})));
	}
}
