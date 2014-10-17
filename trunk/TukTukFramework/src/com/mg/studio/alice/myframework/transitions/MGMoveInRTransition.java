package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;

/**
 * @author Dk Thach
 * 
 *         MoveInR Transition.
 *         inScreen chay vao tu ben phai
 */

public class MGMoveInRTransition extends MGMoveInLTransition {

	public static MGMoveInRTransition transition(float t, MGScreen s) {
		return new MGMoveInRTransition(t, s);
	}

	public MGMoveInRTransition(float t, MGScreen s) {
		super(t, s);
	}

	/**
	 * init screen
	 */
	@Override
	protected void initScreen() {
		inScreen.setPosition(CanvasGame.sizeDevices.width, 0);
	}
}
