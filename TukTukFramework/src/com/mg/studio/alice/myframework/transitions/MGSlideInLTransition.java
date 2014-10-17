package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.myframework.actions.ActionCallback;
import com.mg.studio.alice.myframework.actions.ease.MGEaseOut;
import com.mg.studio.alice.myframework.actions.instant.MGCallback;
import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;
import com.mg.studio.alice.myframework.actions.interval.MGMoveBy;
import com.mg.studio.alice.myframework.actions.interval.MGSequence;
import com.mg.studio.alice.myframework.director.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGSize;

/**
 * @author Dk Thach
 * 
 *         SlideInL Transition. Slide tu ben trai qua
 */

public class MGSlideInLTransition extends MGTransitionScreen implements
		MGTransitionEaseScreen {

	protected static final float ADJUST_FACTOR = 0.5f;

	public static MGSlideInLTransition transition(float t, MGScreen s) {
		return new MGSlideInLTransition(t, s);
	}

	public MGSlideInLTransition(float t, MGScreen s) {
		super(t, s);
	}

	@Override
	public void onEnter() {
		super.onEnter();

		initScenes();

		MGIntervalAction in = action();
		MGIntervalAction out = action();

		inScreen.runAction(easeAction(in));
		outScreen.runAction(MGSequence.actions(easeAction(out),
				MGCallback.action( new ActionCallback() {

					@Override
					public void execute(Object object) {
						finish();
					}
				})));
	}

	public void sceneOrder() {
		inScreenOnTop = false;
	}

	/**
	 * init screen
	 */
	protected void initScenes() {
		MGSize s = CanvasGame.sizeDevices;
		inScreen.setPosition(-(s.width - ADJUST_FACTOR), 0);
	}

	/**
	 * returns action se chay
	 */
	protected MGIntervalAction action() {
		MGSize s = CanvasGame.sizeDevices;
		return MGMoveBy.action(duration,
				MGPointF.make(s.width - ADJUST_FACTOR, 0));
	}

	@Override
	public MGIntervalAction easeAction(MGIntervalAction action) {
		return MGEaseOut.action(action, 2.0f);
	}

}
