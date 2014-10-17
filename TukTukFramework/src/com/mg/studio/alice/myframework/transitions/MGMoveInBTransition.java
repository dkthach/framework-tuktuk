package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;
import com.mg.studio.alice.myframework.type.MGSize;

/**
 * @author Dk Thach
 * 
 *         MoveInB Transition. inScreen chay vao tu ben duoi
 */

public class MGMoveInBTransition extends MGMoveInLTransition {

	public static MGMoveInBTransition transition(float t, MGScreen s) {
		return new MGMoveInBTransition(t, s);
	}

	public MGMoveInBTransition(float t, MGScreen s) {
		super(t, s);
	}

	/**
	 * init screen
	 */
	protected void initScreen() {
		MGSize s = CanvasGame.sizeDevices;
		inScreen.setPosition(0, -s.height);
	}
}
