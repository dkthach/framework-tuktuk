package com.mg.studio.alice.myframework.transitions;

import com.mg.studio.alice.myframework.director.CanvasGame;
import com.mg.studio.alice.myframework.director.MGScreen;
/**
 * @author Dk Thach
 
 * MoveInT Transition.
 * inScreen chay vao tu phia tren
 */

public class MGMoveInTTransition extends MGMoveInLTransition {

    public static MGMoveInTTransition transition(float t, MGScreen s) {
        return new MGMoveInTTransition(t, s);
    }

    public MGMoveInTTransition(float t, MGScreen s) {
        super(t, s);
    }

    /**
     * init screen
     */
    @Override
    protected void initScreen() {
        inScreen.setPosition(0, CanvasGame.sizeDevices.height);
    }
}
