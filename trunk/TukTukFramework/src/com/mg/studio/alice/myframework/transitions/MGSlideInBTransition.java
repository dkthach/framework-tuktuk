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
 *         SlideInB Transition. slide tu tren xuong
 */

public class MGSlideInBTransition extends MGSlideInLTransition {

	public static MGSlideInBTransition transition(float t, MGScreen s) {
		return new MGSlideInBTransition(t, s);
	}

	public MGSlideInBTransition(float t, MGScreen s) {
		super(t, s);
	}

	public void sceneOrder() {
		inScreenOnTop = true;
	}

	/**
	 * init screen
	 */
	protected void initScenes() {
		MGSize s = CanvasGame.sizeDevices;
		inScreen.setPosition(0, -(s.height - ADJUST_FACTOR));
	}

	protected MGIntervalAction action() {
		MGSize s = CanvasGame.sizeDevices;
		return MGMoveBy.action(duration,
				MGPointF.make(0, s.height - ADJUST_FACTOR));
	}

}
