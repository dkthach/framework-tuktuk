package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.type.MGPointF;

//
// JumpTo
//

/** 
 * Moves a MGNode object to a parabolic position simulating a jump movement 
 *          by modifying it's position attribute.
*/ 
public class MGJumpTo extends MGJumpBy {

    public static MGJumpTo action(float time, MGPointF pos, float height, int jumps) {
        return new MGJumpTo(time, pos, height, jumps);
    }

    protected MGJumpTo(float time, MGPointF pos, float height, int jumps) {
        super(time, pos, height, jumps);
    }

    @Override
    public MGJumpTo copy() {
        return new MGJumpTo(duration, delta, height, jumps);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        delta.x -= startPosition.x;
        delta.y -= startPosition.y;
    }
}

