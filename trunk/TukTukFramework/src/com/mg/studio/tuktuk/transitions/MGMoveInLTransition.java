package com.mg.studio.tuktuk.transitions;

import com.mg.studio.tuktuk.actions.ActionCallback;
import com.mg.studio.tuktuk.actions.ease.MGEaseOut;
import com.mg.studio.tuktuk.actions.instant.MGCallback;
import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGMoveTo;
import com.mg.studio.tuktuk.actions.interval.MGSequence;
import com.mg.studio.tuktuk.director.CanvasGame;
import com.mg.studio.tuktuk.director.MGScreen;
import com.mg.studio.tuktuk.type.MGPointF;
import com.mg.studio.tuktuk.type.MGSize;

/**
 * @author Dk Thach
 * 
 *         MoveInL Transition. inScreen chay vao tu ben trai
 */

public class MGMoveInLTransition extends MGTransitionScreen implements
		MGTransitionEaseScreen {

	public static MGMoveInLTransition transition(float t, MGScreen s) {
		return new MGMoveInLTransition(t, s);
	}

	protected MGMoveInLTransition(float t, MGScreen s) {
		super(t, s);
	}

	@Override
	public void onEnter() {
		super.onEnter();

		initScreen();

		MGIntervalAction a = action();

		inScreen.runAction(MGSequence.actions(easeAction(a),
				MGCallback.action( new ActionCallback() {
					@Override
					public void execute(Object object) {
						finish();						
					}
				})));
	}

	/**
	 * returns action se thuc hien
	 */
	protected MGIntervalAction action() {
		return MGMoveTo.action(duration, new MGPointF());
	}

	@Override
	public MGIntervalAction easeAction(MGIntervalAction action) {
		return MGEaseOut.action(action, 2.0f);
	}

	/**
	 * init screen
	 */
	protected void initScreen() {
		MGSize s = CanvasGame.sizeDevices;
		inScreen.setPosition(-s.width, 0);
	}
}
