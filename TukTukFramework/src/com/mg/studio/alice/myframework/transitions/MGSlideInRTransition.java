package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.CanvasGame;
import com.mg.studio.alice.myframework.actions.interval.MGIntervalAction;
import com.mg.studio.alice.myframework.actions.interval.MGMoveBy;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.type.MGPointF;
import com.mg.studio.alice.myframework.type.MGSize;

/**
 * @author Dk Thach
 * 
 *         SlideInR Transition. Slide tu ben phai sang
 */

public class MGSlideInRTransition extends MGSlideInLTransition {

	public static MGSlideInRTransition transition(float t, MGScreen s) {
		return new MGSlideInRTransition(t, s);
	}

	public MGSlideInRTransition(float t, MGScreen s) {
		super(t, s);
	}

	@Override
	public void sceneOrder() {
		inScreenOnTop = true;
	}

	/**
	 * init screen
	 */
	@Override
	protected void initScenes() {
		MGSize s = CanvasGame.sizeDevices;
		inScreen.setPosition(s.width - ADJUST_FACTOR, 0);
	}

	@Override
	public MGIntervalAction action() {
		MGSize s = CanvasGame.sizeDevices;
		return MGMoveBy.action(duration,
				MGPointF.make(-(s.width - ADJUST_FACTOR), 0));
	}
}
