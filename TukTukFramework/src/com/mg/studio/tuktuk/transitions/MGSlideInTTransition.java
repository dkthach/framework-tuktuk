package com.mg.studio.tuktuk.transitions;

import com.mg.studio.tuktuk.actions.interval.MGIntervalAction;
import com.mg.studio.tuktuk.actions.interval.MGMoveBy;
import com.mg.studio.tuktuk.director.CanvasGame;
import com.mg.studio.tuktuk.director.MGScreen;
import com.mg.studio.tuktuk.type.MGPointF;
import com.mg.studio.tuktuk.type.MGSize;

/**
 * @author Dk Thach
 * 
 *         SlideInT Transition. Slide tu phia tren xuong
 */

public class MGSlideInTTransition extends MGSlideInLTransition {

	public static MGSlideInTTransition transition(float t, MGScreen s) {
		return new MGSlideInTTransition(t, s);
	}

	public MGSlideInTTransition(float t, MGScreen s) {
		super(t, s);
	}

	public void sceneOrder() {
		inScreenOnTop = false;
	}

	/**
	 * init screen
	 */
	protected void initScenes() {
		MGSize s = CanvasGame.sizeDevices;
		inScreen.setPosition(0, s.height - ADJUST_FACTOR);
	}

	@Override
	public MGIntervalAction action() {
		MGSize s = CanvasGame.sizeDevices;
		return MGMoveBy.action(duration,
				MGPointF.make(0, -(s.height - ADJUST_FACTOR)));
	}

}
