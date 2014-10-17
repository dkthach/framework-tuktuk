package com.mg.studio.alice.myframework.actions.interval;

import com.mg.studio.alice.myframework.director.MGNode;
import com.mg.studio.alice.myframework.type.MGPointF;


/**
 * Moves a MGNode object simulating a parabolic jump movement by modifying it's position attribute.
*/
public class MGJumpBy extends MGIntervalAction {
    protected MGPointF startPosition;
    protected MGPointF delta;
    protected float height;
    protected int jumps;

    /** creates the action */
    public static MGJumpBy action(float time, MGPointF pos, float height, int jumps) {
        return new MGJumpBy(time, pos, height, jumps);
    }

    /** initializes the action */
    protected MGJumpBy(float time, MGPointF pos, float h, int j) {
        super(time);
        startPosition = MGPointF.make(0, 0);
        delta = MGPointF.make(pos.x, pos.y);
        height = h;
        jumps = j;
    }

    @Override
    public MGJumpBy copy() {
        return new MGJumpBy(duration, delta, height, jumps);
    }

    @Override
    public void start(MGNode aTarget) {
        super.start(aTarget);
        MGPointF pnt = target.getPosition();
        startPosition =MGPointF.make(pnt.x, pnt.y);
    }

    @Override
    public void update(float t) {
        // parabolic jump 
        float frac = (t * jumps) % 1.0f;
        float y = height * 4 * frac * (1 - frac);
        y += delta.y * t;
        float x = delta.x * t;
        target.setPosition(MGPointF.ccp(startPosition.x + x, startPosition.y + y));
    }

    @Override
    public MGJumpBy reverse() {
        return new MGJumpBy(duration, MGPointF.ccpNeg(delta), height, jumps);
    }
}

