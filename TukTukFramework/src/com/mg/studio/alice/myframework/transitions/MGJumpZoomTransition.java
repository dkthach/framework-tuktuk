package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.myframework.actions.ActionCallback;
import com.mg.studio.alice.myframework.actions.instant.MGCallback;
import com.mg.studio.alice.myframework.actions.interval.MGDelayTime;
import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;
import com.mg.studio.alice.myframework.actions.interval.MGJumpBy;
import com.mg.studio.alice.myframework.actions.interval.MGScaleTo;
import com.mg.studio.alice.myframework.actions.interval.MGSequence;
import com.mg.studio.alice.myframework.director.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGSize;

/**
 * @author Dk Thach
 * 
 *         JumpZoom Transition. Zoom out va jump outScreen , jump va zoomIn
 *         inScreen
 */

public class MGJumpZoomTransition extends MGTransitionScreen {

	public static MGJumpZoomTransition transition(float t, MGScreen s) {
		return new MGJumpZoomTransition(t, s);
	}

	public MGJumpZoomTransition(float t, MGScreen s) {
		super(t, s);
	}

	@Override
	public void onEnter() {
		super.onEnter();
		MGSize size = CanvasGame.sizeDevices;

		float width = size.width;
		// float height = size.getHeight();

		inScreen.setScale(0.5f);
		inScreen.setPosition(width, 0);

		 inScreen.setAnchorPoint(0.5f, 0.5f);
		 outScreen.setAnchorPoint(0.5f, 0.5f);
		

		MGIntervalAction jump = MGJumpBy.action(duration / 4,
				MGPointF.make(-width, 0), width / 4, 2);
		MGIntervalAction scaleIn = MGScaleTo.action(duration / 4, 1.0f);
		MGIntervalAction scaleOut = MGScaleTo.action(duration / 4, 0.5f);

		MGIntervalAction jumpZoomOut = MGSequence.actions(scaleOut, jump);
		MGIntervalAction jumpZoomIn = MGSequence
				.actions(jump.copy(), scaleIn);

		MGIntervalAction delay = MGDelayTime.action(duration / 2);

		outScreen.runAction(jumpZoomOut);
		inScreen.runAction(MGSequence.actions(delay, jumpZoomIn,

		MGCallback.action( new ActionCallback() {

			@Override
			public void execute(Object object) {
				finish();
			}
		})));
	}
}
